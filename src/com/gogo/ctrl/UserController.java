package com.gogo.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.ctrl.model.UserMainModel;
import com.gogo.dao.UserDao;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.domain.filter.ActivityFilter;
import com.gogo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	

	@Autowired
	private UserService userService;
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public boolean registerUser(@RequestBody User user){	
		userService.saveUser(user);
		return true;
	}
	
	/**
	 * 根据用户ID更新用户
	 * @param UserId
	 * @return
	 */
	@RequestMapping("update/{userId}")
	public boolean UpdateUser(@RequestBody User user,@PathVariable String UserId){
		return false;
	}
	
	/**
	 * 根据用户ID获得用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("load/{userId}")
	@ResponseBody
	public User loadUserByUserId(@PathVariable int userId){
		return userService.loadUserById(userId);
	}
	
	/**
	 * 根据用户Name获得用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadByName/{userName}")
	@ResponseBody
	public List<User> loadUserByUserName(String userName){
		List<User> users =  userService.loadUserByName(userName);

		return users;
		
	}
	
	/**
	 * 根据用户ID获得用户参加的所有活动信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadJoinAct/{userId}")
	@ResponseBody
	public List<Activity> loadJoinActivitesByUser(int userId){
		return userService.loadJoinActivitesByUser(userId);
	}
	
	/**
	 * 根据用户ID获得用户拥有的所有活动信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadOwnAct/{userId}")
	@ResponseBody
	public List<Activity> loadOwnActivitesByUser(int userId){
		return userService.loadOwnActivitesByUser(userId);
	}
	
	
	/**
	 * 根据用户ID删除用户
	 * @param UserId
	 * @return
	 */
	@RequestMapping("del/{userId}")
	public boolean delUser(String UserId){
		return false;
	}
	
	/**
	 * 返回用户主页
	 * @return
	 */
	@RequestMapping(value="main")
	public ModelAndView backUserMain(HttpServletRequest req){
		ModelAndView mav = new ModelAndView();
		UserMainModel umm = new UserMainModel();
		umm.setUser(getSessionUser(req));
		
		int userId = getSessionUser(req).getUserId();
		
		List<Activity> ownAct = userService.loadOwnActivitesByUser(userId);
		
		List<Activity> joinAct = userService.loadJoinActivitesByUser(userId);
		
		umm.setOwnActivity(ownAct);
		umm.setJoinActivity(joinAct);
		
		mav.addObject("userMainModel",umm );
		mav.setViewName("main");
		return mav;
	}
	
}
