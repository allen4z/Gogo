package com.gogo.service;

import java.util.Date;
import java.util.Iterator;
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
	public synchronized void saveActivity4RoleState(String actId,User user,String RoleCode){
		
		Activity act = loadActbyActId(actId);
		Set<Role> roles= act.getRoles();
		Iterator<Role> it = roles.iterator();
		
		
		
		Role joinRole = null;
		UserAndRole uar = null;
		//1.如果用户以游客身份关联，则必须有其他的身份
		if(!RoleCode.equals(RoleHelper.VISITOR_CODE)){
			uar = userAndRoleDao.loadUserAndRoleByUserAndAct(user, act);
			if(uar == null){
				throw new Business4JsonException("用户不是当前小组成员，请先加入小组");
			}
		}else{
			uar = new UserAndRole();
			uar.setUser(user);
		}
		
		//2.查询当前活动是否已经有所需角色
		while(it.hasNext()){
			Role role = it.next();
			if(role.getRoleCode().equals(RoleCode)){
				joinRole = role;
				break;
			}
		}
		
		//3.如果没有则新建角色信息
		if(joinRole == null){
			joinRole = new Role();
			joinRole.setRoleCode(RoleCode);
			joinRole.setRoleName(RoleHelper.getRoleInfo().get(RoleCode));
			joinRole.setBelongAct(act);
		}else{
			User haveUser = userAndRoleDao.loadActUser4Role(user, joinRole);
			if(haveUser != null){
				throw new Business4JsonException("您已经报过名了！");
			}
		}
		
		
		//当前角色已经拥有的人数
		int hasUserCount = joinRole.getBelongUser() == null ? 0 :joinRole.getBelongUser().size();
		
		//5.判断报名人数 增加代付款金额
		if(RoleCode.equals(RoleHelper.JOIN_CODE)){
			if(hasUserCount>= act.getMaxJoin()){
				throw new Business4JsonException("参与活动报名人数已满！");
			}
			//更新用户参加活动待支付金额
			if(act.getJoinNeedPay() != 0){
				uar.setWaitCost(act.getJoinNeedPay());
			}
		}else if(RoleCode.equals(RoleHelper.SIGNUP_CODE)){
			if(hasUserCount>= act.getMaxSignUp()){
				throw new Business4JsonException("观看活动报名人数已满！");
			}
			//更新用户观看活动待支付金额
			if(act.getSignUpNeedPay() != 0){
				uar.setWaitCost(act.getSignUpNeedPay());
			}
		}
		
		
		//4.更新用户与角色的关联关系
		if(uar.getRole()== null || !uar.getRole().getRoleCode().equals(RoleHelper.MANAGER_CODE)){
			uar.setRole(joinRole);
		}
		
		
		if(joinRole.getBelongUser() !=null){
			if(joinRole.getBelongUser().contains(uar)){
				throw new Business4JsonException("you joined this activity!");
			}
		}
		
		notifyPayProcess(user);
		
		actDao.update(act);
		userAndRoleDao.saveOrUpdate(uar);
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
			queryList =PageUtil.getPage(actDao.loadActbyAddrCount(user,city), currPage, actDao.loadActbyAddr(user,city, currPage, pageSize), pageSize);
		}else{
			queryList = actDao.loadActByHotPoint();
		}
		return queryList;
	}
}
