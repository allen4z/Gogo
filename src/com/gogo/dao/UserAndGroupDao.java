package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.UserAndGroup;


@Repository
public class UserAndGroupDao extends BaseDao<UserAndGroup> {

	
	public UserAndGroup loadUAG4UserAndGroup(String userId,String groupId){
		String hql = "select uag from UserAndGroup uag "
				+ " left join uag.user uu "
				+ " left join uag.group ug  "
				+ " where uu.id=? and ug.id=?";
		UserAndGroup uag = (UserAndGroup) findUnique(hql, userId,groupId);
		return uag;
	}
	
	public int loadCount(String groupId) {
		String hql = "select count(uag) from  UserAndGroup uag where uag.group.id=?";
		return getCount(hql, groupId);
		
	}
	
	public List<UserAndGroup> loadAllUserAndGroup(String userId,Integer[] auth){
		String hql = "select uag from UserAndGroup uag left join uag.user u where u.id=:id and uag.authorityState in (:auth)";
		
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setParameterList("auth", auth);
		return query.list();
	}

}
