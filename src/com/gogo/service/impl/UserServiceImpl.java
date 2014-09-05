package com.gogo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.helper.DomainStateHelper;
import com.gogo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public void saveUser(User user){
		user.setUserState(DomainStateHelper.USER_NORMAL_STATE);
		user.setUserRegisterTime(new Date());
		userDao.saveUser(user);
	}
	
	@Transactional
	public User loadUserById(int userId){
		return userDao.loadUserById(userId);
	}
	
	public List<User> loadUserByName(String userName){
		List<User> user =  userDao.loadUserByName(userName);
		return user;
	}
	
	public List<User> loadUserByName(String userName,int curPage,int pagesize){
		
		int psize = pagesize == 0 ?  10 : pagesize;
		
		List<User> user =  userDao.loadUserByName(userName,curPage,psize);
		return user;
	}
	
	public List<Activity> loadJoinActivitesByUser(int userId){
		return userDao.loadJoinActivitesByUser(userId);
	}

	public List<Activity> loadOwnActivitesByUser(int userId) {
		return userDao.loadOwnActivitesByUser(userId);
	}
	
}
