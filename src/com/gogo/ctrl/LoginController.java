package com.gogo.ctrl;

import java.io.File;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.User;
import com.gogo.domain.UserToken;
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
	
	@RequestMapping(value="doLogin4json")
	@ResponseBody
	public UserToken login4Json(HttpSession session,Model model, @RequestBody User loginUser) throws Exception{
		User user = userService.UserInfoCheck(loginUser);
			//setSessionUser(session, dbUser);	
		//生成token
		UserToken token = userService.saveToken(user);			
			//聊天服务器登陆
			//SmackTool smack = new SmackTool();
			//smack.getConnect(dbUser);
		return token;
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
}
