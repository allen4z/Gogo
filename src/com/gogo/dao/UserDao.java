package com.gogo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.User;

@Repository
public class UserDao extends BaseDao<User> {
	
	
	public void saveUser(User user){
		save(user);
	}
	
	
	public User loadUserById(int userId){
		return get(userId);
	}
	
	public List<User> loadUserByName(String userName){
		String hql = "from User where userName='"+userName+"'";
		List<User> userList  = find(hql);
		return userList;
	}


	public List<Activity> loadJoinActivitesByUser(int userId) {
		
		String hql = "from Activity act left join act.joinUser ju left join ju.user u where u.userId='"+userId+"'";
		List<Activity> actList = find(hql);
		return actList;
	}


	public List<Activity> loadOwnActivitesByUser(int userId) {
		String hql = "select act from Activity act left join act.ownUser ou where ou.userId='"+userId+"'";
		List<Activity> actList = find(hql);
		return actList;
	}
}
