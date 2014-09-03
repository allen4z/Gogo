package com.gogo.ctrl.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.ctrl.LoginController;
import com.gogo.ctrl.impl.BaseController;
import com.gogo.domain.User;
import com.gogo.helper.CommonConstant;
import com.gogo.service.UserService;


@Controller
@RequestMapping("/login")
public class LoginControllerImpl extends BaseController implements LoginController {

	
	Logger log = Logger.getLogger(LoginControllerImpl.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="doLogin",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest req, 
				@RequestParam("userName") String userName,
				@RequestParam("password") String password) throws Exception{
		
		log.debug("userName:"+userName +"start login");
		
		List<User> users =  userService.loadUserByName(userName);
		ModelAndView mav = new ModelAndView();
		
		User loginUser = new User();
		loginUser.setUserName(userName);
		loginUser.setPassword(password);
		String toUrl;
		if(UserInfoCheck(req, loginUser, users)){
			toUrl = "redirect:/user/main";
		}else{
			String error = "password failed";
			mav.addObject(ERROR_MSG_KEY, error);
			toUrl = "redirect:/index.jsp";
		}
		mav.setViewName(toUrl);
		return mav;
	}
	
	@RequestMapping(value="doLogin4json")
	@ResponseBody
	public boolean login4Json(HttpServletRequest req, @RequestBody User loginUser) throws Exception{
		List<User> users =  userService.loadUserByName(loginUser.getUserName());
		return  UserInfoCheck(req, loginUser, users);
	}


	private boolean UserInfoCheck(HttpServletRequest req, User loginUser,
			List<User> users)  throws Exception{
		boolean passwordflag = false;
		for (User user : users) {
			if(user.getPassword().equals(loginUser.getPassword())){
				setSessionUser(req, user);
				passwordflag = true;
			}
		}
		return passwordflag;
	}
	
	@RequestMapping("doRegister")
	public String goRegister() throws Exception{
		return "user/register";
	}
	
	@RequestMapping("doLogout")
	public String Logout(HttpServletRequest req) throws Exception{
		req.getSession().removeAttribute(CommonConstant.USER_CONTEXT);
		return "redirect:/index.jsp";
	}
	
	
	
	/* test */
	@RequestMapping(value="doLogin4phone")
	@ResponseBody
	public User login4phone(HttpServletRequest req, 
				@RequestParam("userName") String userName,
				@RequestParam("password") String password) throws Exception{
		
		log.debug("userName:"+userName +"start login");
		
		User usr = new User();
		usr.setUserName(userName);
		usr.setPassword(password);
		return usr;
		
	}
}
