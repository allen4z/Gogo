package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.UserAndGroup;
import com.gogo.domain.enums.UserAndGroupState;


@Repository
public class UserAndGroupDao extends BaseDao<UserAndGroup> {
	
	public UserAndGroup loadByUserAndGroup(String userId,String groupId,UserAndGroupState state){
		String hql = "select uag from UserAndGroup uag left join uag.user u "
				+ " left join uag.group g "
				+ " where u.id=:id "
				+ " and uag.state:=state "
				+ " and uag.g.id=:groupId";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setInteger("state", state.ordinal());
		query.setString("groupId", groupId);
		return (UserAndGroup) query.uniqueResult();
	}
	
	
	public UserAndGroup loadUAG4User(String userId){
		String hql = "select uag from UserAndGroup uag left join uag.user u where u.id=:id";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		return (UserAndGroup) query.uniqueResult();
	}
	
	public int loadCount(String groupId) {
		String hql = "select count(uag) from  UserAndGroup uag where uag.group.id=?";
		return getCount(hql, groupId);
		
	}
	/**
	 * 球员只能加入或创建一支球队
	 * @param userId
	 * @return
	 */
	public UserAndGroup loadByUser(String userId){
		String hql = " select uag from UserAndGroup uag left join uag.user u where u.id=:id and uag.state=:state ";
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setInteger("state", UserAndGroupState.FORMAL.ordinal());
		return (UserAndGroup) query.uniqueResult();
	}
	
	/**
	 * 查询当前用户加入的所有球队关联关系信息
	 * 球员可以加入多只球队
	 * @param userId
	 * @return
	 */
//	public List<UserAndGroup> loadByUser(String userId){
//		String hql = " select uag from UserAndGroup uag left join uag.user u where u.id=? and uag.state=? ";
//		return find(hql, userId,UserAndGroupState.FORMAL.ordinal());
//	}
	

	
	public UserAndGroup loadAllUserAndGroup(String userId,Integer[] auth){
		String hql = "select uag from UserAndGroup uag left join uag.user u "
				+ " where u.id=:id "
				+ " and uag.authorityState in (:auth) "
				+ " and uag.state=:state ";
		
		Query query =  getSession().createQuery(hql);
		query.setString("id", userId);
		query.setParameterList("auth", auth);
		query.setInteger("state", UserAndGroupState.FORMAL.ordinal());
		return (UserAndGroup) query.uniqueResult();
	}

}
