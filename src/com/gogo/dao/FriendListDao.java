package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.FriendList;
import com.gogo.domain.User;

@Repository
public class FriendListDao extends BaseDao<FriendList> {

	/**
	 * 查询所有好友
	 * @param userId
	 * @return
	 */
	public List<User> loadAllFriends(String userId){
		
		String hql = " select friendList.friendUser from FriendList friendList where friendList.belongUser.userId=:userId and friendList.passed=:passed "
			;
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setBoolean("passed", true)
				.list();
		return firends;
	}
	
}
