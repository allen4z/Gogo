package com.gogo.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.UserAndRoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Place;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.domain.helper.RoleHelper;
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
	private UserAndRoleDao userAndRoleDao;
	
	public Activity loadActbyActId(String actId){
		return actDao.getActbyActId(actId);
	}

	public void saveActivity(Activity act,User user) {
		act.setActCreateTime(new Date());
		act.setOwnUser(user);
		
		//设置为超级管理员
		Role mrole = new Role();
		mrole.setRoleCode(RoleHelper.MANAGER_CODE);
		mrole.setRoleName(RoleHelper.getRoleInfo().get(RoleHelper.MANAGER_CODE));
		mrole.setBelongAct(act);
		
		UserAndRole uar = new UserAndRole();
		uar.setRole(mrole);
		uar.setUser(user);
		uar.setUarState(RoleHelper.UAR_NONE_ACTIVITY);
		
		userAndRoleDao.save(uar);
	}

	
	/**
	 * 更新用户角色状态  ： 加入小组时 用户关联当前活动的游客角色
	 *              参加活动时 用户关联当前活动的参与者角色
	 *              观看活动时 用户关联当前活动的观众角色
	 * @param actId  活动ID
	 * @param user  用户信息
	 * @param RoleCode 角色编码
	 * 
	 * 
	 * 使用了同步方法！！！！
	 */
	public void saveActivity4RoleState(String actId,User user,String roleCode){
		
		Activity act = loadActbyActId(actId);
		Set<Role> roles= act.getRoles();
		Iterator<Role> it = roles.iterator();
		
		Role joinRole = null;
		UserAndRole uar = null;
		//1.用户第一次分配角色
		uar = new UserAndRole();
		uar.setUser(user);
	
		//2.查询当前活动是否已经有游客角色
		while(it.hasNext()){
			Role role = it.next();
			if(role.getRoleCode().equals(roleCode)){
				joinRole = role;
				break;
			}
		}
		
		//3.如果没有则新建角色信息
		if(joinRole == null){
			joinRole = new Role();
			joinRole.setRoleCode(roleCode);
			joinRole.setRoleName(RoleHelper.getRoleInfo().get(roleCode));
			joinRole.setBelongAct(act);
		}else{
			User haveUser = userAndRoleDao.loadActUser4Role(user, joinRole);
			if(haveUser != null){
				throw new Business4JsonException("act_you_have_registered","You have registered");
			}
		}
		uar.setRole(joinRole);
		
		//5、判断是否重复加入
		if(joinRole.getBelongUser() !=null){
			if(joinRole.getBelongUser().contains(uar)){
				throw new Business4JsonException("you joined this activity!");
			}
		}
		
		actDao.update(act);
		userAndRoleDao.saveOrUpdate(uar);
	}
	
	
	
	
	/**
	 *  更新用户关联关系的状态 
	 * @param actId
	 * @param user
	 * @param uarState
	 */
	public synchronized void updateActivity4UARState(String actId,User user,int uarState){
		Activity act = loadActbyActId(actId);
		//1、得到活动所有的角色用户关系
		List<UserAndRole> uars = userAndRoleDao.loadUserAndRoleByAct(act);
		//2.得到已经报名的人数
		int hasUserCount = 0;
		//3.得到当前用户与活动角色的关系
		UserAndRole uar = null;
		for (UserAndRole userAndRole : uars) {
			//当前用户的关联关系
			if(userAndRole.getUser().getUserId().equals(user.getUserId())){
				uar = userAndRoleDao.loadUserAndRoleByUserAndAct(user, act);
				//如果当前用户的权限包含了需要变更的权限，则为重复加入
				if(RoleHelper.judgeState(uar.getUarState(), uarState)){
					throw new Business4JsonException("you joined this activity!");
				}
			}
			int curState = uar.getUarState();
			if(RoleHelper.judgeState(curState, uarState)){
				hasUserCount += 1;
			}
		}	
		
		//如果用户没有关联人和该活动的角色，则提示用户先加入活动小组
		if(uar == null){
			throw new Business4JsonException("act_join_group_first","user not in current group,plase join first");
		}
		
		//4、分配权限，合并已有权限和变更权限
		int chagedState=RoleHelper.mergeState(uar.getUarState(), uarState);
		uar.setUarState(chagedState);
		//是否需要付款通知
		boolean payFlag = false;
		
		//5.判断报名人数 增加代付款金额
		if(uarState == RoleHelper.UAR_JOIN_ACTIVITY){
			if(hasUserCount>= act.getMaxJoin()){
				throw new Business4JsonException("act_join_full","Participate in the activity of the enrollment is full");
			}
			
			//更新用户参加活动待支付金额
			if(act.getJoinNeedPay() != 0){
				uar.setWaitCost(act.getJoinNeedPay());
				payFlag = true;
			}
		}else if(uarState == RoleHelper.UAR_SINGUP_ACTIVITY){
			if(hasUserCount>= act.getMaxSignUp()){
				throw new Business4JsonException("act_signup_full","Watching activity enrollment is full");
			}
			//更新用户观看活动待支付金额
			if(act.getSignUpNeedPay() != 0){
				uar.setWaitCost(act.getSignUpNeedPay());
				payFlag = true;
			}
		}
		if(payFlag){
			notifyPayProcess(user);
		}
		userAndRoleDao.update(uar);
	}
	
	
	/**
	 * 通知用户支付
	 * @param act
	 */
	private void notifyPayProcess(User user){
		//通过消息队列通知用户进行支付
		log.info("通知用户开始支付");
	}
	
	/**
	 * 删除模块，首先检查模块是否属于当前登录用户
	 * 如不属于，则抛出异常
	 */
	public void deleteActivity(String actId,String userId) {
		Activity act = actDao.loadActbyActId(actId);
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
		
		Activity act4db = actDao.loadActbyActId(actId);
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
}
