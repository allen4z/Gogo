package com.gogo.helper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {  
	  
    private static ThreadLocal<WebContext> tlv = new ThreadLocal<WebContext>();  
    private HttpServletRequest request;  
    private HttpServletResponse response;  
    private ServletContext servletContext;  
  
    protected WebContext() {  
    }  
  
    public HttpServletRequest getRequest() {  
        return request;  
    }  
  
    public void setRequest(HttpServletRequest request) {  
        this.request = request;  
    }  
  
    public HttpServletResponse getResponse() { 
    	
    	response.setCharacterEncoding("UTF-8"); 
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
        return response;  
    }  
  
    public void setResponse(HttpServletResponse response) {  
        this.response = response;  
    }  
  
    public ServletContext getServletContext() {  
        return servletContext;  
    }  
  
    public void setServletContext(ServletContext servletContext) {  
        this.servletContext = servletContext;  
    }  
  
    private WebContext(HttpServletRequest request,  
            HttpServletResponse response, ServletContext servletContext) {  
        this.request = request;  
        this.response = response;  
        this.servletContext = servletContext;  
    }  
  
    public static WebContext getInstance() {  
        return tlv.get();  
    }  
  
    public static void create(HttpServletRequest request,  
            HttpServletResponse response, ServletContext servletContext) {  
        WebContext wc = new WebContext(request, response, servletContext);  
        tlv.set(wc);  
    }  
  
    public static void clear() {  
        tlv.set(null);  
    }  
}
