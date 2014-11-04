package com.gogo.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.GroupDao;
import com.gogo.dao.NotifyAndGroupDao;
import com.gogo.dao.NotifyDao;
import com.gogo.dao.UserAndActDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Group;
import com.gogo.domain.Notify;
import com.gogo.domain.NotifyAndGroup;
import com.gogo.domain.User;
import com.gogo.domain.UserAndAct;
import com.gogo.domain.enums.ACTState;
import com.gogo.domain.enums.NotifyType;
import com.gogo.domain.enums.UserAndActState;
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
	@Autowired
	private GroupDao  groupDao;
	@Autowired
	private NotifyDao notifyDao;
	@Autowired
	private NotifyAndGroupDao notifyAndGroupDao;
	
	public Activity loadActbyActId(String actId){
		return actDao.get(actId);
	}

	public void saveActivity(Activity act,User user) throws UnsupportedEncodingException {
		act.setActCreateTime(new Date());
		act.setOwnUser(user);
		//TODO 保存默认为发布状态
		act.setState(ACTState.RELEASE);
		actDao.save(act);
		//建立消息信息
		createActNotify(user,act);
	}
	
	/**
	 * 新建活动定义消息
	 * @param user
	 * @param act
	 * @throws UnsupportedEncodingException
	 */
	private void createActNotify(User user,Activity act) throws UnsupportedEncodingException {
		//新建消息
		Notify notify = new Notify();
		notify.setCreateUser(user);
		notify.setType(NotifyType.GROUP);
		
		ResourceBundle bundle = ResourceBundle.getBundle("notify");
		String title = new String(bundle.getString("newacttitle").getBytes("ISO-8859-1"), "UTF-8");
		notify.setTitle(title);

		String content = new String(bundle.getString("newactcontent").getBytes("ISO-8859-1"), "UTF-8");
		content = content.replace("{user}", user.getAliasName());
		content = content.replace("{act}", act.getName());
		notify.setContent(content);
		notifyDao.save(notify);
		//查看用户所在小组
		List<Group> groups= groupDao.loadGroup4User(user.getId());
		
		for (Group group : groups) {
			NotifyAndGroup nag = new NotifyAndGroup();
			nag.setNotify(notify);
			nag.setGroup(group);
			notifyAndGroupDao.save(nag);
		}
		//TODO ****推送消息  
	}

	/**
	 * 用户参加活动             
	 * @param actId  活动ID
	 * @param user  用户信息
	 */
	public synchronized UserAndActState saveUserJoinActivity(String userId, String actId) {
		
		UserAndActState result = UserAndActState.CANCEL;
		User user = userDao.get(userId);
		UserAndAct uaa = userAndActDao.loadByUserAndAct(userId, actId);
		
		Activity act = actDao.load(actId);
		
		//判断用户是否已参加此活动
		if(uaa == null){
			uaa = new UserAndAct();
			uaa.setUser(user);
			uaa.setAct(act);
		}else{
			if((uaa.getUaaState() == UserAndActState.JOIN||uaa.getUaaState() ==UserAndActState.QUEUE)){
				throw new Business4JsonException("you joined this activity!");
			}
		}
		
		//判断是否报名已满
		int maxJoin = act.getMaxJoin();
		int curJoint = act.getCutJoin();
		if(curJoint>=maxJoin){ //报名已满
			uaa.setUaaState(UserAndActState.QUEUE);
			result = UserAndActState.QUEUE;
		}else{
			uaa.setUaaState(UserAndActState.JOIN);
			result = UserAndActState.JOIN;
			uaa.setWaitCost(act.getJoinNeedPay());
			
			act.setCutJoin(act.getCutJoin()+1);
		}
		//保存报名信息
		uaa.setUpdate_time(new Date());
		userAndActDao.save(uaa);
		
		return result;
	}
	
	/**
	 * 取消报名或排队
	 * @param actId
	 * @param user
	 */
	public synchronized void updateUserJoinActivity(String actId, User user) {
		UserAndAct uaa = userAndActDao.loadByUserAndAct(user.getId(),actId);
		if(uaa == null){
			throw new Business4JsonException("您没有报名此活动");
		}
		
		//如果用户是参加状态，则查询排队人员，更新为参加
		if(uaa.getUaaState() == UserAndActState.JOIN){
			//获得最近一条排队人员
			List<UserAndAct> queueUsers = userAndActDao.loadByActAndState(actId,UserAndActState.QUEUE,new int[]{1});
			if(queueUsers != null && queueUsers.size()>0){
				UserAndAct queueUaa = queueUsers.get(0);
				queueUaa.setUaaState(UserAndActState.JOIN);
				queueUaa.setWaitCost(uaa.getAct().getJoinNeedPay());
				userAndActDao.update(queueUaa);
				
				//TODO 通知排队用户已经参加活动
				
			}else{
				Activity act = uaa.getAct();
				act.setCutJoin(act.getCutJoin()-1);
			}
		}
		uaa.setWaitCost(0);
		uaa.setUaaState(UserAndActState.CANCEL);
		userAndActDao.update(uaa);
	}
	
	/**
	 * 删除模块，首先检查模块是否属于当前登录用户
	 * 如不属于，则抛出异常
	 */
	public void deleteActivity(String actId,String userId) {
		Activity act = actDao.load(actId);
		User user = act.getOwnUser();
		if(user.getId().equals(userId)){
			act.setState(ACTState.DELETE);
			actDao.update(act);
		}else{
			throw new BusinessException("登录用户无权删除此活动");
		}
	}

	/**
	 * 更新活动信息，将前台传入的数据赋值到查询出的活动 并更新
	 */
	public void updateActivity(Activity act, String userId) throws Exception {
		String actId = act.getId();
		
		Activity act4db = actDao.load(actId);
		User user = act4db.getOwnUser();
		
		if(user.getId().equals(userId)){
			BeanUtils.copyProperties(act, act4db);
			DomainStateHelper.copyPriperties(act, act4db);
//			act4db.setUpdate_time(new Date());
			actDao.update(act4db);
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
	public Page<Activity> loadActByPlace(User user,GoMapHelper gmh,String ip,int currPage,int pageSize){
		//根据经纬度检索百度地图
		String[] mapIds = new String[]{};
		
		
		Page<Activity> queryList = null;
		if(gmh != null && gmh.getLongitude() != 0 && gmh.getLongitude() != 0){
			queryList = PageUtil.getPage(actDao.lodActByPlaceCount(user,mapIds),currPage , actDao.loadActByPlace(user,mapIds, currPage, pageSize), pageSize);
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
		return PageUtil.getPage(userDao.loadUserByActCount(actId), 0, userDao.loadUserByAct(actId, currPage, pageSize), pageSize);
	}
	/**
	 * 获得当前活动制定用户
	 * @param actId
	 * @param currPage
	 * @param pageSize
	 * @param uarState 权限状态标志
	 * @return
	 */
	public Page<User> loadSpecialUserFromAct(String actId, int currPage,int pageSize,UserAndActState uarState) {
		return PageUtil.getPage(userDao.loadUserByActCount(actId,uarState.ordinal()), 0, userDao.loadUserByAct(actId, currPage, pageSize,uarState.ordinal()), pageSize);
	}

	/**
	 * 获得用户在当前活动的状态信息
	 * @param userId
	 * @param actId
	 */
	public UserAndActState loadCurUserStateInAct(String userId, String actId) {
		UserAndAct uaa = userAndActDao.loadByUserAndAct(userId, actId);	
		if(uaa != null){
			return uaa.getUaaState();
		}else{
			return UserAndActState.CANCEL;
		}
	}

}
