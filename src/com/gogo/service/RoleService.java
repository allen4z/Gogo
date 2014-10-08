package com.gogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.UserAndRoleDao;
import com.gogo.domain.Activity;
import com.gogo.domain.Role;
import com.gogo.domain.User;

@Service
public class RoleService {
	@Autowired
	private UserAndRoleDao uarDao;
	
	public List<Role> loadCurUserRole4Act(User user,Activity act){
		List<Role> roles = uarDao.loadRoleByUserAndAct(user, act);
		return roles;
	}

}
