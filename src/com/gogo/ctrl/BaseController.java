package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gogo.domain.User;
import com.gogo.exception.BusinessException;
import com.gogo.exception.ParameterException;
import com.gogo.helper.CommonConstant;

public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";
	
	Logger log = Logger.getLogger(BaseController.class);
	
	@ExceptionHandler
	public String exp(HttpServletRequest req,Exception ex){
		req.setAttribute("ex", ex);
		log.error(ex.toString());
		ex.printStackTrace();
		if(ex instanceof BusinessException){
			return "error/error-business";
		}else if(ex instanceof ParameterException) {
			return "error/error-parameter";
		} else {
			return "error/error";
		}
	}
	
	protected User getSessionUser(HttpSession session){
		return (User)session.getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	protected void setSessionUser(HttpSession session,User user){
		//req.getSession().setMaxInactiveInterval(5); 设置失效时间
		session.setAttribute(CommonConstant.USER_CONTEXT, user);
	}
	
	protected void removeSessionUser(HttpSession session){
		Object object = session.getAttribute(CommonConstant.USER_CONTEXT);
		if (object != null) {
			try {  
				System.out.println(session.getId());
				session.removeAttribute(CommonConstant.USER_CONTEXT); 
			} catch (Exception e) {    
				object = null;   
			}  
		}
		session.invalidate();
	}
	
	protected int getPageSize(Integer pagesize){
		return (pagesize == null || pagesize == 0) ? CommonConstant.PAGE_SIZE : pagesize;
	}
}
