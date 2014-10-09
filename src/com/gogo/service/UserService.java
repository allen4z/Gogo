package com.gogo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.FriendListDao;
import com.gogo.dao.UserAndRoleDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.FriendList;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.domain.helper.RoleHelper;
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
	private UserAndRoleDao userAndRoleDao;
	
	@Autowired
	private FriendListDao friendGroupDao;
	
	public void saveUser(User user){
		user.setUserState(DomainStateHelper.USER_NORMAL_STATE);
		user.setUserRegisterTime(new Date());
		user.setUpdate_time(new Date());
		//使用MD5加密用户密码
		String password = user.getUserPassword();
		user.setUserPassword(MD5Util.MD5(password));
		
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
		
		List<UserAndRole> uars = userAndRoleDao.loadUserAndRoleByUser(userId);
		
		for (UserAndRole userAndRole : uars) {
			Role role = userAndRole.getRole();
			Activity act = role.getBelongAct();
			
			StringBuffer payInfo = new StringBuffer();
			payInfo.append("you must pay ");
			payInfo.append(userAndRole.getWaitCost());
			payInfo.append(" yuan for");
			if(role.getRoleCode().equals(RoleHelper.JOIN_CODE)){
				payInfo.append(" join ");
			}else if(role.getRoleCode().equals(RoleHelper.SIGNUP_CODE)){
				payInfo.append(" signup ");
			}
			payInfo.append("activity -- "+act.getActName());
		
		
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
		
		String dbPassword = MD5Util.MD5(loginUser.getUserPassword());
		User user =  userDao.loadUserByNameAndPassword(loginUser.getUserName(),dbPassword);
		
		if(user == null ){
			throw new Business4JsonException("user_username_or_password_error","username or password error");
		}
		
		return user;
	}
	
	public List<User> loadFriends(String userId) throws Exception{
		 List<User> friends = friendGroupDao.loadAllFriends(userId);
		 return friends;
	}
}
