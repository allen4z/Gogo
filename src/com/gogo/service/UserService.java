package com.gogo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.CommonConstant;
import com.gogo.helper.DomainStateHelper;
import com.gogo.helper.MD5Util;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class UserService{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ActivityDao actDao;
	
	public void saveUser(User user){
		user.setUserState(DomainStateHelper.USER_NORMAL_STATE);
		user.setUserRegisterTime(new Date());
		user.setUpdate_time(new Date());
		//使用MD5加密用户密码
		String password = user.getUserPassword();
		user.setUserPassword(MD5Util.MD5(password));
		
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

	public Page<Activity> loadOwnActivitesByUser(String userId,int currPage,int pageSize) {
		
		return PageUtil.getPage(actDao.loadOwnActivitesByUserCount(userId), 0, actDao.loadOwnActivitesByUser(userId, currPage, pageSize), pageSize);
		
		//return actDao.loadOwnActivitesByUser(userId);
	}
	
	
	/**
	 * 用户验证
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public User UserInfoCheck(User loginUser)  throws Exception{
		
		String dbPassword = MD5Util.MD5(loginUser.getUserPassword());
		User user =  userDao.loadUserByNameAndPassword(loginUser.getUserName(),dbPassword);
		
		if(user == null ){
			throw new Business4JsonException("用户或密码错误");
		}
		
		return user;
	}
}
