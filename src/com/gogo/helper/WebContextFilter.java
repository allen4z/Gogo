package com.gogo.helper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContextFilter implements Filter {  
	  
    public void init(FilterConfig filterConfig) throws ServletException {      
    }  
      
    public void doFilter(ServletRequest req, ServletResponse resp,  
            FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) resp;  
        ServletContext servletContext = request.getSession().getServletContext();  
        WebContext.create(request, response, servletContext);  
        chain.doFilter(request, response);  
        WebContext.clear();  
    }

	public void destroy() {
		// TODO Auto-generated method stub
		
	}  
}