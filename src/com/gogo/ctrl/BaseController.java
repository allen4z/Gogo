package com.gogo.ctrl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gogo.dao.UserTokenDao;
import com.gogo.domain.GoError;
import com.gogo.domain.User;
import com.gogo.exception.Business4JsonException;
import com.gogo.exception.BusinessException;
import com.gogo.exception.ParameterException;
import com.gogo.helper.CommonConstant;

/**
 * 控制父类
 * 提供获得session中用户的方法
 * 异常拦截方法
 * @author allen
 *
 */
public class BaseController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserTokenDao userTokenDao;
	
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
	
	
	@ExceptionHandler(value=Business4JsonException.class)
	@ResponseBody
	public GoError exp4Json(Exception ex,HttpServletRequest request){
		Locale locale = RequestContextUtils.getLocale(request);
		Business4JsonException e = (Business4JsonException) ex;
		String msg = null;
		String loaclMsg = messageSource.getMessage(e.getCode(), null, e.getMessage(), locale);
		if(loaclMsg == null || loaclMsg.equals("")){
			msg = e.getMessage();
		}else{
			msg = loaclMsg;
		}
		
		//错误信息按json返回
		GoError err= new GoError();
		err.setCategory(e.getCode());
		err.setMessage(msg);
		return err;
	}
	
	protected String getUserToken(HttpServletRequest request){
		return request.getParameter("access_token");
	}
	
	protected User getUser(String tokenId){
		return userTokenDao.get(tokenId).getUser();
	}
	protected String getServletPath(HttpServletRequest request) {
		return request.getServletPath();
	}
	protected int getPageSize(Integer pagesize){
		return (pagesize == null || pagesize == 0) ? CommonConstant.PAGE_SIZE : pagesize;
	}


	public MessageSource getMessageSource() {
		return messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
