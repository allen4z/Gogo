package com.gogo.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogo.domain.User;
import com.gogo.helper.CommonConstant;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	Logger log = Logger.getLogger(CommonInterceptor.class);	
	//不需要过滤的url
	private List<String> exceptFilter;
	
	//需要过滤的后缀
	private List<String> needSuffix;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		
		String uri = request.getRequestURI();
		//过滤
		if(uriFilter(uri)){
			return true;
		}
		
		Object obj = request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
		
		if(null == obj){
			 response.setCharacterEncoding("utf-8");  
			 response.setContentType("text/html; charset=utf-8"); 
			 PrintWriter out = response.getWriter();  
            StringBuilder builder = new StringBuilder();  
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");  
            builder.append("alert(\"页面过期，请重新登录\");");  
            builder.append("window.top.location.href=\"");  
            builder.append("http://127.0.0.1:8080/Gogo");  
            builder.append("/\";</script>");  
            out.print(builder.toString());  
            out.close();  
			return false;
		}else{
			//记录日志
			User user = (User) obj;
			log.debug(user.getUserId()+" : "+ uri);
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
