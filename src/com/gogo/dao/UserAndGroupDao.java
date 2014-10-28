package com.gogo.dao;

import org.springframework.stereotype.Repository;

import com.gogo.domain.UserAndGroup;


@Repository
public class UserAndGroupDao extends BaseDao<UserAndGroup> {

	
	public UserAndGroup loadUAG4UserAndGroup(String userId,String groupId){
		String hql = "select * from UserAndGroup uag where uag.user.userId=? and uag.group.id=?";
		UserAndGroup uag = (UserAndGroup) findUnique(hql, userId,groupId);
		return uag;
	}
	
	public int loadCount(String groupId) {
		String hql = "select count(*) from  UserAndGroup uag where uag.group.id=?";
		return (Integer) findUnique(hql, groupId);
	}
	
}
