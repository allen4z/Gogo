package com.gogo.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.helper.DomainStateHelper;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void saveUser(User user){
		
		user.setUserState(DomainStateHelper.USER_NORMAL_STATE);
		user.setUserRegisterTime(new Date());
		userDao.saveUser(user);
	}
	
	public User loadUserById(int userId){
		return userDao.loadUserById(userId);
	}
	
	public List<User> loadUserByName(String userName){
		List<User> user =  userDao.loadUserByName(userName);
		return user;
	}
	
	public List<Activity> loadJoinActivitesByUser(int userId){
		return userDao.loadJoinActivitesByUser(userId);
	}

	public List<Activity> loadOwnActivitesByUser(int userId) {
		return userDao.loadOwnActivitesByUser(userId);
	}
	
}
