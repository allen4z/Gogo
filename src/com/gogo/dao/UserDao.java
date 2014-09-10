package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.page.Page;

@Repository
public class UserDao extends BaseDao<User>{
	
	public void saveUser(User user){
		save(user);
	}
	
	
	public User loadUserById(int userId){
		return load(userId);
	}
	
	public User getUserById(int userId){
		return get(userId);
	}
	
	public List<User> loadUserByName(String userName){
		String hql = "from User where userName='"+userName+"'";
		List<User> userList  = find(hql);
		return userList;
	}
	
	public List<User> loadUserByNameAndPassword(String userName,String password){
		String hql = "from User u where u.userName='"+userName+"' and u.password='"+password+"'";
		List<User> userList  = find(hql);
		return userList;
	}
	
	public List<User> loadUserByName(String userName,int curPage,int pagesize){
		String hql = "from User where userName='"+userName+"'";		
		List<User> userList  = findByPage(hql,curPage,pagesize);
		return userList;
	}


	public List<Activity> loadJoinActivitesByUser(int userId) {
		
		String hql = "from Activity act left join act.joinUser ju left join ju.user u where u.userId='"+userId+"'";
		List<Activity> actList = find(hql);
		return actList;
	}


	public Page<Activity> loadOwnActivitesByUser(String userId) {
		String hql = "select act from Activity act left join act.ownUser ou where ou.userId='"+userId+"'";
		Page<Activity> actPage = find(hql,1);
		return actPage;
	}


	public void updateUser(User user) {
		update(user);
	}
	
	public void deleteUser(User user){
		remove(user);
	}
}
