package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.FriendGroup;
import com.gogo.domain.User;

@Repository
public class FriendGroupDao extends BaseDao<FriendGroup> {

	/**
	 * 查询所有好友
	 * @param userId
	 * @return
	 */
	public List<User> loadAllFriends(String userId){
		
		String hql = " select friendGroup.friendUser from FriendGroup friendGroup where friendGroup.belongUser.userId:=userId and friendGroup.passed:=passed "
				+ " order by friendGroup.friendUser.alisName ";
		
		List firends = getSession().createQuery(hql)
				.setString("userId", userId)
				.setBoolean("passed", true)
				.list();
		return firends;
	}
	
}
