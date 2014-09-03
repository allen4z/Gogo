package com.gogo.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.Activity;
import com.gogo.domain.User;

public interface UserController {

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */

	public boolean registerUser(User user);

	/**
	 * 根据用户ID更新用户
	 * 
	 * @param UserId
	 * @return
	 */

	public boolean UpdateUser(User user, String UserId);

	/**
	 * 根据用户ID获得用户信息
	 * 
	 * @param userId
	 * @return
	 */

	public User loadUserByUserId(int userId);

	/**
	 * 根据用户Name获得用户信息
	 * 
	 * @param userId
	 * @return
	 */

	public List<User> loadUserByUserName(String userName);

	/**
	 * 根据用户ID获得用户参加的所有活动信息
	 * 
	 * @param userId
	 * @return
	 */

	public List<Activity> loadJoinActivitesByUser(int userId);

	/**
	 * 根据用户ID获得用户拥有的所有活动信息
	 * 
	 * @param userId
	 * @return
	 */

	public List<Activity> loadOwnActivitesByUser(int userId);

	/**
	 * 根据用户ID删除用户
	 * 
	 * @param UserId
	 * @return
	 */

	public boolean delUser(String UserId);

	/**
	 * 返回用户主页
	 * 
	 * @return
	 */

	public ModelAndView backUserMain(HttpServletRequest req);

}
