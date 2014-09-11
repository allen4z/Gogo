package com.gogo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.RoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Place;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;
import com.gogo.exception.BusinessException;
import com.gogo.helper.DomainStateHelper;
import com.gogo.map.GoMapHelper;
import com.gogo.user.role.RoleHelper;

@Service
public class ActivityService {
	
	@Autowired
	private ActivityDao actDao;
	@Autowired
	private RoleDao roleDao;
	
	public Activity loadActbyActId(String actId){
		return actDao.getActbyActId(actId);
	}

	public void saveActivity(Activity act,User user) {
		act.setActCreateTime(new Date());
		act.setUpdate_time(new Date());
		act.setOwnUser(user);
		
		//设置为超级管理员
		Role role = new Role();
		role.setRoleCode(RoleHelper.ROLE_CODE);
		role.setRoleName(RoleHelper.ROLE_NAME);
		role.setBelongAct(act);
		
		UserAndRole uar = new UserAndRole();
		uar.setRole(role);
		uar.setUser(user);
		Set<UserAndRole> belongUser = new HashSet<UserAndRole>();
		belongUser.add(uar);
		role.setBelongUser(belongUser);
		
		roleDao.save(role);
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
			act.setUpdate_time(new Date());
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
			act4db.setUpdate_time(new Date());
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
	public List<Activity> loadActByPlace(Place place,String ip){
		List<Activity> queryList = null;
		if(place != null && place.getLongitude() != 0 && place.getLongitude() != 0){
			queryList = actDao.loadActByPlace(place);
		}else if(ip != null ){
			City city = GoMapHelper.getCityInfo(ip);
			queryList = actDao.loadActbyAddr(city);
		}else{
			queryList = actDao.loadActByHotPoint();
		}
		return queryList;
	}
}
