package com.gogo.ctrl;

import java.io.File;
import java.util.Random;
import java.util.ResourceBundle;

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
import com.gogo.exception.Business4JsonException;
import com.gogo.service.UserService;
import com.gogo.smack.SmackTool;


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
		loginUser.setName(userName);
		loginUser.setPassword(password);
		
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
			
			//聊天服务器登陆
			//SmackTool smack = new SmackTool();
			//smack.getConnect(dbUser);
		}
		
		return dbUser;
		
	}

	
	@RequestMapping("doRegister")
	public ModelAndView goRegister() throws Exception{
		ModelAndView mav = new ModelAndView();
		String serverPath = System.getProperty("myappfuse.root");
		//随机获得图片
		ResourceBundle rb = ResourceBundle.getBundle("imageConfig");
		String defaultUserHeadPath =rb.getString("image.default.userhead.path");
		String imageServer= rb.getString("image.upload.imageserver");
		
		File pathFile = new File(serverPath+defaultUserHeadPath);
		File[] imgs = pathFile.listFiles();
		int fileSize = imgs.length;
		
		Random r = new Random();
		File imageFile = imgs[r.nextInt(fileSize)];
		
		mav.addObject("userhead", imageServer+defaultUserHeadPath +imageFile.getName());
		mav.setViewName("user/registerUserPage");
		return mav;
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
		usr.setName(userName);
		usr.setPassword(password);
		return usr;
		
	}
}
