package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.UserAndGroup;
import com.gogo.domain.enums.UserAndGroupState;


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
	
	
	public UserAndGroup loadUAG4User(String userId){
		String hql = "select uag from UserAndGroup uag left join uag.user u where u.id:=id";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		return (UserAndGroup) query.uniqueResult();
	}
	
	public int loadCount(String groupId) {
		String hql = "select count(uag) from  UserAndGroup uag where uag.group.id=?";
		return getCount(hql, groupId);
		
	}
	
	public UserAndGroup loadByUser(String userId){
		String hql = " select uag from UserAndGroup uag left join uag.user u where u.id=:id and uag.state=:state ";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setInteger("state", UserAndGroupState.FORMAL.ordinal());
		return (UserAndGroup) query.uniqueResult();
	}
	
	public UserAndGroup loadByUserAndGroup(String userId,String groupId){
		String hql = "select uag from UserAndGroup uag left join uag.user u "
				+ " left join uag.group g "
				+ " where u.id=:id "
				+ " and uag.state:=state "
				+ " and uag.g.id=:groupId";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setInteger("state", UserAndGroupState.FORMAL.ordinal());
		query.setString("groupId", groupId);
		return (UserAndGroup) query.uniqueResult();
	}
	
	public List<UserAndGroup> loadAllUserAndGroup(String userId,Integer[] auth){
		String hql = "select uag from UserAndGroup uag left join uag.user u where u.id=:id and uag.authorityState in (:auth)";
		
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setParameterList("auth", auth);
		return query.list();
	}

}
