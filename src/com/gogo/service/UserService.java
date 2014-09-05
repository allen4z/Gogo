package com.gogo.service;

import java.util.List;

import com.gogo.domain.Activity;
import com.gogo.domain.User;


public interface UserService {

	public void saveUser(User user);
	
	public User loadUserById(int userId);
	
	public List<User> loadUserByName(String userName);
	public List<User> loadUserByName(String userName,int curPage,int pagesize);
	
	public List<Activity> loadJoinActivitesByUser(int userId);

	public List<Activity> loadOwnActivitesByUser(int userId);
	
}
