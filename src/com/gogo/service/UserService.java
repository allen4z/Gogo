package com.gogo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.exception.BusinessException;
import com.gogo.helper.DomainStateHelper;
import com.gogo.helper.MD5Util;
import com.gogo.page.Page;

@Service
public class UserService{
	@Autowired
	private UserDao userDao;
	
	public void saveUser(User user){
		user.setUserState(DomainStateHelper.USER_NORMAL_STATE);
		user.setUserRegisterTime(new Date());
		user.setUpdate_time(new Date());
		//使用MD5加密用户密码
		String password = user.getPassword();
		user.setPassword(MD5Util.MD5(password));
		
		userDao.saveUser(user);
	}
	
	@Transactional
	public User loadUserById(int userId){
		return userDao.getUserById(userId);
	}
	
	public List<User> loadUserByName(String userName){
		List<User> user =  userDao.loadUserByName(userName);
		return user;
	}
	

	
	
	
	public List<User> loadUserByName(String userName,int curPage,int pagesize){
		
		List<User> user =  userDao.loadUserByName(userName,curPage,pagesize);
		return user;
	}
	
	public List<Activity> loadJoinActivitesByUser(int userId){
		return userDao.loadJoinActivitesByUser(userId);
	}

	public Page<Activity> loadOwnActivitesByUser(String userId) {
		return userDao.loadOwnActivitesByUser(userId);
	}
	
	
	/**
	 * 用户验证
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public User UserInfoCheck(User loginUser)  throws Exception{
		
		String dbPassword = MD5Util.MD5(loginUser.getPassword());
		List<User> users =  userDao.loadUserByNameAndPassword(loginUser.getUserName(),dbPassword);
		
		if(users != null && users.size()>0){
			if(users.size()>1){
				throw new BusinessException("用户名或密码错误！");
			}
			User user =users.get(0);
			return user;
		}
		
		return null;
	}
}
