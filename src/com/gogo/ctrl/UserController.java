package com.gogo.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.ctrl.UserController;
import com.gogo.ctrl.model.UserMainModel;
import com.gogo.domain.Activity;
import com.gogo.domain.User;
import com.gogo.helper.CommonConstant;
import com.gogo.page.Page;
import com.gogo.service.UserService;

@Controller
@RequestMapping("/user")
@SessionAttributes(CommonConstant.USER_CONTEXT)
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
	 * @param userName
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value="loadByName/{userName}",method=RequestMethod.GET)
	@ResponseBody
	public List<User> loadUserByUserName(@PathVariable String userName,
			@RequestParam(required=false) Integer curPage,
			@RequestParam(required=false) Integer pagesize){
		
		List<User> users = null;
		if(curPage == null || curPage == 0){
			users =  userService.loadUserByName(userName);
		}else{
			users =  userService.loadUserByName(userName,curPage,getPageSize(pagesize));
		}
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
	public Page<Activity> loadOwnActivitesByUser(String userId){
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
	public ModelAndView backUserMain(@ModelAttribute(CommonConstant.USER_CONTEXT) User user){
		ModelAndView mav = new ModelAndView();
		String userId = user.getUserId();
		Page<Activity> ownAct = userService.loadOwnActivitesByUser(userId);
		mav.addObject("page",ownAct );
		mav.setViewName("main");
		return mav;
	}
	
}
