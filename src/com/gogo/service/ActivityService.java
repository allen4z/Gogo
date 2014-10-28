package com.gogo.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.UserAndActDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.UserAndAct;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.exception.Business4JsonException;
import com.gogo.exception.BusinessException;
import com.gogo.map.GoMapHelper;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class ActivityService {
	
	Logger log = Logger.getLogger(ActivityService.class);
	
	@Autowired
	private ActivityDao actDao;
	@Autowired
	private UserAndActDao userAndActDao;
	@Autowired
	private UserDao userDao;
	
	public Activity loadActbyActId(String actId){
		return actDao.get(actId);
	}

	public void saveActivity(Activity act,User user) {
		act.setActCreateTime(new Date());
		act.setOwnUser(user);
		act.setActState(DomainStateHelper.ACT_NEW);
		actDao.save(act);
	}

	
	/**
	 * 用户参加活动             
	 * @param actId  活动ID
	 * @param user  用户信息
	 */
	public synchronized int saveUserJoinActivity(User user, String actId) {
		
		int result = -1;
		String userId = user.getUserId();
	
		UserAndAct uaa = userAndActDao.loadByUserAndAct(userId, actId);
		
		Activity act = actDao.load(actId);
		if(uaa == null){
			uaa = new UserAndAct();
			uaa.setUser(user);
			uaa.setAct(act);
		}else{
			if((uaa.getUaaState() == DomainStateHelper.USER_AND_ACT_JOIN||uaa.getUaaState() == DomainStateHelper.USER_AND_ACT_QUEUE)){
				throw new Business4JsonException("you joined this activity!");
			}
		}
		
		//判断是否报名已满
		int maxJoin = act.getMaxJoin();
		int curJoint = act.getCutJoin();
		if(curJoint>=maxJoin){ //报名已满
			uaa.setUaaState(DomainStateHelper.USER_AND_ACT_QUEUE);
			result = DomainStateHelper.USER_AND_ACT_QUEUE;
		}else{
			uaa.setUaaState(DomainStateHelper.USER_AND_ACT_JOIN);
			result = DomainStateHelper.USER_AND_ACT_JOIN;
			uaa.setWaitCost(act.getJoinNeedPay());
			
			act.setCutJoin(act.getCutJoin()+1);
		}
		//保存报名信息
		uaa.setUpdate_time(new Date());
		userAndActDao.saveOrUpdate(uaa);
		
		return result;
	}
	
	/**
	 * 取消报名或排队
	 * @param actId
	 * @param user
	 */
	public synchronized void updateUserJoinActivity(String actId, User user) {
		UserAndAct uaa = userAndActDao.loadByUserAndAct(user.getUserId(),actId);
		if(uaa == null){
			throw new Business4JsonException("您没有报名此活动");
		}
		
		//如果用户是参加状态，则查询排队人员，更新为参加
		if(uaa.getUaaState() == DomainStateHelper.USER_AND_ACT_JOIN){
			//获得最近一条排队人员
			List<UserAndAct> queueUsers = userAndActDao.loadByActAndState(actId,DomainStateHelper.USER_AND_ACT_QUEUE,new int[]{1});
			if(queueUsers != null && queueUsers.size()>0){
				UserAndAct queueUaa = queueUsers.get(0);
				queueUaa.setUaaState(DomainStateHelper.USER_AND_ACT_JOIN);
				queueUaa.setWaitCost(uaa.getAct().getJoinNeedPay());
				userAndActDao.update(queueUaa);
			}
		}
		uaa.setWaitCost(0);
		uaa.setUaaState(DomainStateHelper.USER_AND_ACT_CANCEL);
		Activity act = uaa.getAct();
		act.setCutJoin(act.getCutJoin()-1);
		userAndActDao.update(uaa);
	}
	
	/**
	 * 删除模块，首先检查模块是否属于当前登录用户
	 * 如不属于，则抛出异常
	 */
	public void deleteActivity(String actId,String userId) {
		Activity act = actDao.load(actId);
		User user = act.getOwnUser();
		if(user.getUserId().equals(userId)){
			act.setActState(DomainStateHelper.ACT_DEL);
//			act.setUpdate_time(new Date());
			actDao.updateActivity(act);
		}else{
			throw new BusinessException("登录用户无权删除此活动");
		}
	}

	/**
	 * 更新活动信息，将前台传入的数据赋值到查询出的活动 并更新
	 */
	public void updateActivity(Activity act, String userId) throws Exception {
		String actId = act.getActId();
		
		Activity act4db = actDao.load(actId);
		User user = act4db.getOwnUser();
		
		if(user.getUserId().equals(userId)){
			BeanUtils.copyProperties(act, act4db);
			DomainStateHelper.copyPriperties(act, act4db);
//			act4db.setUpdate_time(new Date());
			actDao.updateActivity(act4db);
		}else{
			throw new BusinessException("登录用户无权更新此活动");
		}
	}
	
	/**
	 * 查询附近活动
	 * 1、如果有地区信息，则按地图查询活动信息
	 * 2、如果没有地区信息，则按用户所在地查询活动信息
	 * 3、如果没有任何信息，则按热度展示信息
	 * @param place
	 * @return
	 */
	public Page<Activity> loadActByPlace(User user,Place place,String ip,int currPage,int pageSize){
		Page<Activity> queryList = null;
		if(place != null && place.getLongitude() != 0 && place.getLongitude() != 0){
			queryList = PageUtil.getPage(actDao.lodActByPlaceCount(user,place),currPage , actDao.loadActByPlace(user,place, currPage, pageSize), pageSize);
		}else if(ip != null ){
			City city = GoMapHelper.getCityInfo(ip);
			if(city != null){
				queryList =PageUtil.getPage(actDao.loadActbyAddrCount(user,city), currPage, actDao.loadActbyAddr(user,city, currPage, pageSize), pageSize);
			}else{
				queryList =PageUtil.getPage(actDao.loadActByHotPointCount(user), currPage, actDao.loadActByHotPoint(user, currPage, pageSize), pageSize);
			}
		}
		return queryList;
	}

	/**
	 * 获得当前活动的所有用户
	 * @param actId
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public Page<User> loadAllUserFromAct(String actId,int currPage,int pageSize) {
		return PageUtil.getPage(userAndActDao.loadUserByActCount(actId), 0, userAndActDao.loadUserByAct(actId, currPage, pageSize), pageSize);
	}
	/**
	 * 获得当前活动制定用户
	 * @param actId
	 * @param currPage
	 * @param pageSize
	 * @param uarState 权限状态标志
	 * @return
	 */
	public Page<User> loadSpecialUserFromAct(String actId, int currPage,int pageSize,int uarState) {
		return PageUtil.getPage(userAndActDao.loadUserByActCount(actId,uarState), 0, userAndActDao.loadUserByAct(actId, currPage, pageSize,uarState), pageSize);
	}

	/**
	 * 获得用户在当前活动的状态信息
	 * @param userId
	 * @param actId
	 */
	public int loadCurUserStateInAct(String userId, String actId) {
		UserAndAct uaa = userAndActDao.loadByUserAndAct(userId, actId);	
		if(uaa != null){
			return uaa.getUaaState();
		}else{
			return -1;
		}
	}

}
