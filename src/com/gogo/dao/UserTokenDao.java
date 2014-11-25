package com.gogo.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.gogo.domain.User;
import com.gogo.domain.UserToken;

@Repository
public class UserTokenDao extends BaseDao<UserToken> {

	public UserToken findTokenByUser(User user){
		String hql = "select userToken from UserToken userToken "
				+ " left join userToken.user user "
				+ " where user.name=? and user.password=? "
				+ " and userToken.startDate<? and userToken.endDate>? ";
		Date currentTime = new Date();
		return findUnique(hql, user.getName(),user.getPassword(),currentTime,currentTime);
	}
	
	
	public UserToken getCurrentToken(String tokenId){
		String hql = " from UserToken userToken where id=? and userToken.startDate<? and userToken.endDate>?";
		Date currentTime = new Date();
		return findUnique(hql, tokenId,currentTime,currentTime);
	}
}
