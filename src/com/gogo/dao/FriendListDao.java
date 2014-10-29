package com.gogo.dao;

import org.springframework.stereotype.Repository;

import com.gogo.domain.FriendList;

@Repository
public class FriendListDao extends BaseDao<FriendList> {
	

	public FriendList loadFriendListByUserId(String requestUserId, String agreeUserId) {
		String hql = "select friendList from FriendList friendList "
				+ " where friendList.belongUser.userId=:requestUserId "
				+ " and friendList.friendUser.userId=:agreeUserId ";
	
		FriendList fl = (FriendList) getSession()
				.createQuery(hql)
				.setString("requestUserId", requestUserId)
				.setString("agreeUserId", agreeUserId)
				.uniqueResult();
		
		return fl;
	}
}
