package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.UserTokenDao;
import com.gogo.domain.User;
import com.gogo.domain.UserToken;

@Service
public class BaseService {

	@Autowired
	UserTokenDao userTokenDao;
	
	public User getUserbyToken(String tokenId){
		UserToken userToken = userTokenDao.get(tokenId);
		return userToken.getUser();
	}

}
