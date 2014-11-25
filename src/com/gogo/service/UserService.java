package com.gogo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.UserAndActDao;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.dao.UserDao;
import com.gogo.dao.UserTokenDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.domain.UserAndAct;
import com.gogo.domain.UserToken;
import com.gogo.domain.enums.UserState;
import com.gogo.exception.Business4JsonException;
import com.gogo.helper.MD5Util;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class UserService{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ActivityDao actDao;
	
	@Autowired
	private UserAndGroupDao userAndRoleDao;
	@Autowired
	private UserAndActDao userAndActDao;
	@Autowired
	private UserTokenDao userTokenDao;
	
	public void saveUser(User user){
		user.setState(UserState.FORMAL);
		user.setRegisterTime(new Date());
		user.setUpdate_time(new Date());
		//使用MD5加密用户密码
		String password = user.getPassword();
		user.setPassword(MD5Util.MD5(password));
		
		/*FriendList fg = new FriendList();
		fg.setBelongUser(user);*/

		userDao.save(user);
//		friendGroupDao.save(fg);
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
	
	public Page<Activity> loadJoinActivitesByUser(String userId,int currPage,int pageSize){
		return PageUtil.getPage(actDao.loadJoinActivitesByUserCount(userId), 0, actDao.loadJoinActivitesByUser(userId, currPage, pageSize), pageSize);
	}

	public Page<Activity> loadOwnActivitesByUser(String userId,int currPage,int pageSize) {
		
		return PageUtil.getPage(actDao.loadOwnActivitesByUserCount(userId), 0, actDao.loadOwnActivitesByUser(userId, currPage, pageSize), pageSize);
		
	}
	
	
	public List<String> loadPayInfo(String userId){
		
		List<String> payInfos = new ArrayList<String>();
		
		//查询所有用户拥有角色信息
		List<UserAndAct> uaas = userAndActDao.loadUserAndActByUser(userId);
		
		for (UserAndAct uaa : uaas) {
			if(uaa.getWaitCost() == 0){
				continue;
			}
			Activity act = uaa.getAct();
			StringBuffer payInfo = new StringBuffer();
			payInfo.append("you must pay ");
			payInfo.append(uaa.getWaitCost());
			payInfo.append(" yuan for");
			payInfo.append(" join ");
			payInfo.append("activity -- "+act.getName());
			payInfos.add(payInfo.toString());
		}
		
		return payInfos;
		
	}
	
	/**
	 * 用户验证
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public User UserInfoCheck(User loginUser)  throws Exception{
		
		String dbPassword = MD5Util.MD5(loginUser.getPassword());
		User user =  userDao.loadUserByNameAndPassword(loginUser.getName(),dbPassword);
		
		if(user == null ){
			throw new Business4JsonException("user_username_or_password_error","username or password error");
		}
//		user.setPassword(loginUser.getPassword());
		return user;
	}
	
	
	public UserToken saveToken(User user){
		UserToken token = userTokenDao.findTokenByUser(user);
		if(token == null){
			token = new UserToken();
			token.setUser(user);
			Calendar c = Calendar.getInstance();
			token.setStartDate(c.getTime());
			c.add(Calendar.MONTH, 6);
			token.setEndDate(c.getTime());
		}
		userTokenDao.save(token);
		
		return token;
	}
	
}
