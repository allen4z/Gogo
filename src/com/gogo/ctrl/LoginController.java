package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.User;
import com.gogo.service.UserService;


/**
 * 登录控制器
 * @author allen
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{

	
	Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="doLogin",method=RequestMethod.POST)
	public ModelAndView login(HttpSession session, 
				@RequestParam("userName") String userName,
				@RequestParam("userPassword") String password) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		User loginUser = new User();
		loginUser.setUserName(userName);
		loginUser.setUserPassword(password);
		
		String toUrl;
		User dbUser = userService.UserInfoCheck(loginUser);
		if(dbUser!=null){
			setSessionUser(session, dbUser);
			toUrl = "redirect:/user/main";
		}else{
			String error = "用户名或密码错误";
			mav.addObject(ERROR_MSG_KEY, error);
			toUrl = "redirect:/index.jsp";
		}
		mav.setViewName(toUrl);
		return mav;
	}
	
	@RequestMapping(value="doLogin4json")
	@ResponseBody
	public User login4Json(HttpSession session,Model model, @RequestBody User loginUser) throws Exception{
		User dbUser = userService.UserInfoCheck(loginUser);
		if(dbUser!=null){
			setSessionUser(session, dbUser);
		}
		return dbUser;
	}

	
	@RequestMapping("doRegister")
	public String goRegister() throws Exception{
		return "user/registerUserPage";
	}
	
	@RequestMapping("doLogout")
	public String Logout(HttpSession session) throws Exception{
		removeSessionUser(session);
		return "redirect:/index.jsp";
	}
	
	
	
	/* test */
	@RequestMapping(value="doLogin4phone")
	@ResponseBody
	public User login4phone(HttpServletRequest req, 
				@RequestParam("userName") String userName,
				@RequestParam("userPassword") String password) throws Exception{
		
		log.debug("userName:"+userName +"start login");
		
		User usr = new User();
		usr.setUserName(userName);
		usr.setUserPassword(password);
		return usr;
		
	}
}
