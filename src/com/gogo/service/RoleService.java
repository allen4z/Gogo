package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.UserAndRoleDao;

@Service
public class RoleService {
	@Autowired
	private UserAndRoleDao uarDao;
	


}
