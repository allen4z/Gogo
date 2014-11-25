package com.gogo.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogo.dao.UserTokenDao;
import com.gogo.domain.UserToken;
import com.gogo.exception.Business4JsonException;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = Logger.getLogger(CommonInterceptor.class);	
	//不需要过滤的url
	private List<String> exceptFilter;
	//需要过滤的后缀
	private List<String> needSuffix;
	
	@Autowired
	private UserTokenDao userTokenDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		
		String uri = request.getRequestURI();
		//过滤
		if(uriFilter(uri)){
			return true;
		}
		String token = request.getParameter("access_token");	
		if(token != null && !token.equals("")){
			UserToken  userToken = userTokenDao.getCurrentToken(token);
			if(userToken==null){
				throw new Business4JsonException("账号已过期，请重启登陆");
			}
			//记录日志
			log.debug("ACCESS_TOKEN: "+ token);
			
		}else{
			throw new Business4JsonException("用户未登陆");
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * uri过滤方法
	 * @param uri
	 * @return
	 */
	private boolean uriFilter(String uri) {
		
		log.debug("开始过滤URI");
		
		log.debug("uri："+uri);
		//不拦截的过滤
		for (String  noFilterInfo : exceptFilter) {
			if(uri.indexOf(noFilterInfo) != -1){
				return true;
			}
		}
		
		String[] uris = uri.split("/");
		//获得请求的最后一部分
		String end = uris[uris.length-1];
		log.debug("end info ："+end);
		//正则表达式判断是否有后缀名
		if(end.indexOf(".") != -1){
			for (String suffix : needSuffix) {
				if(end.endsWith(suffix)){
					return false;
				}
			}
			return true;
		}
		
		return false;
	}

	public List<String> getExceptFilter() {
		return exceptFilter;
	}

	public void setExceptFilter(List<String> exceptFilter) {
		this.exceptFilter = exceptFilter;
	}

	public List<String> getNeedSuffix() {
		return needSuffix;
	}

	public void setNeedSuffix(List<String> needSuffix) {
		this.needSuffix = needSuffix;
	}
	
}
