package com.gogo.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.exception.BusinessException;
import com.gogo.helper.DomainStateHelper;
import com.gogo.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityDao actDao;
	
	public Activity loadActbyActId(int actId){
		return actDao.loadActbyActId(actId);
	}

	public int saveActivity(Activity act,User user) {
		act.setActCreateTime(new Date());
		act.setOwnUser(user);
		
		return (Integer) actDao.saveActivity(act);
	}

	
	/**
	 * 删除模块，首先检查模块是否属于当前登录用户
	 * 如不属于，则抛出异常
	 */
	public void deleteActivity(int actId,int userId) {
		Activity act = actDao.loadActbyActId(actId);
		User user = act.getOwnUser();
		if(user.getUserId() == userId){
			act.setActState(DomainStateHelper.ACT_DEL);
			actDao.updateActivity(act);
		}else{
			throw new BusinessException("登录用户无权删除此活动");
		}
	}

	/**
	 * 更新活动信息，将前台传入的数据赋值到查询出的活动 并更新
	 */
	public void updateActivity(Activity act, int userId) throws Exception {
		int actId = act.getActId();
		
		Activity act4db = actDao.loadActbyActId(actId);
		
		User user = act4db.getOwnUser();
		
		if(user.getUserId() == userId){
			BeanUtils.copyProperties(act, act4db);
			DomainStateHelper.copyPriperties(act, act4db);
			actDao.updateActivity(act4db);
		}else{
			throw new BusinessException("登录用户无权更新此活动");
		}
	}
}
