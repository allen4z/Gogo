package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gogo.domain.User;
import com.gogo.exception.BusinessException;
import com.gogo.exception.ParameterException;
import com.gogo.helper.CommonConstant;

public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";
	
	@ExceptionHandler
	public String exp(HttpServletRequest req,Exception ex){
		req.setAttribute("ex", ex);
		
		if(ex instanceof BusinessException){
			return "error/error-business";
		}else if(ex instanceof ParameterException) {
			return "error/error-parameter";
		} else {
			return "error/error";
		}
	}
	
	protected User getSessionUser(HttpServletRequest req){
		return (User) req.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	protected void setSessionUser(HttpServletRequest req,User user){
		//req.getSession().setMaxInactiveInterval(5); 设置失效时间
		req.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}
	
	protected int getPageSize(Integer pagesize){
		return (pagesize == null || pagesize == 0) ? CommonConstant.PAGE_SIZE : pagesize;
	}
}
