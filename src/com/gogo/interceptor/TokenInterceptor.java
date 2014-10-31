package com.gogo.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gogo.annotation.Token;



public class TokenInterceptor extends HandlerInterceptorAdapter {
	
	private static final String Token = "token";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if(annotation != null){
				boolean needSaveSession = annotation.save();
				if(needSaveSession){
					request.getSession(false).setAttribute(Token,UUID.randomUUID().toString());
				}
				boolean needRemoveSession = annotation.remove();
				if(needRemoveSession){
					if(isRepeatSubmit(request)){
						return false;
					}
					request.getSession(false).removeAttribute(Token);
				}
			}
			return true;
			
		}else{
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(Token);
		if(serverToken == null){
			return true;
		}
		String clientToken = request.getParameter(Token);
		if(clientToken == null){
			return true;
		}
		if(!serverToken.equals(clientToken)){
			return true;
		}
		return false;
	}
	

}
