package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Group;
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
		return this.<Number>getCount(hql, groupId).intValue();
		
	}

}
