package com.gogo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityDao actDao;
	@Autowired
	private UserDao userDao;
	
	public Activity loadActbyActId(int actId){
		return actDao.loadActbyActId(actId);
	}

	public List<User> loadJoinUserByActId(String actId) {
		return actDao.loadJoinUserByActId(actId);
	}

	
	public void saveActivity(Activity act) {
		actDao.saveActivity(act);
	}
	
	public int saveActivity(Activity act, int userId) {
		User user = userDao.loadUserById(userId);
		act.setActCreateTime(new Date());
		act.setOwnUser(user);
		
		return (Integer) actDao.saveActivity(act);
	}

	public boolean delActivity(String actId) {
		return actDao.delActivity(actId);
	}


}
