package com.gogo.dao;

import java.util.List;

import com.gogo.domain.Activity;
import com.gogo.domain.User;


public interface UserDao{
	
	public void saveUser(User user);
	
	public User loadUserById(int userId);
	
	public List<User> loadUserByName(String userName);

	public List<Activity> loadJoinActivitesByUser(int userId);

	public List<Activity> loadOwnActivitesByUser(int userId);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);
}
