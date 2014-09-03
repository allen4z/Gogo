package com.gogo.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gogo.domain.User;

public interface LoginController {

	public ModelAndView login(HttpServletRequest req, String userName,
			String password) throws Exception;

	public boolean login4Json(HttpServletRequest req, User loginUser)
			throws Exception;

	public String goRegister() throws Exception;

	public String Logout(HttpServletRequest req) throws Exception;

	/* test */

	public User login4phone(HttpServletRequest req, String userName,
			String password) throws Exception;
}
