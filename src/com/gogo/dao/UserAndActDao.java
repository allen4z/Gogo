package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.User;
import com.gogo.domain.UserAndAct;
import com.gogo.domain.UserAndGroup;

@Repository
public class UserAndActDao extends BaseDao<UserAndAct> {

	
	/**
	 * 根据活动ID查询该活动所有的用户角色关系
	 * @param actId
	 * @return
	 */
	public List<UserAndAct> loadByAct(String actId){
		String hql = "select ua from UserAndAct ua join ua.act a  where a.id=:actId";
		List<UserAndAct> uaas = getSession().createQuery(hql)
		.setString("actId", actId)
		.list();
		return uaas;
	}
	
	/**
	 * 根据用户和活动ID查询用户角色的关联关系
	 * @param userID
	 * @param actID
	 * @return
	 */
	public UserAndAct loadByUserAndAct(String userID,String actID){
		String hql = "select ua from UserAndAct ua join ua.act a where ua.user.id = :userId and a.id=:actId";
		UserAndAct uaa = (UserAndAct) getSession().createQuery(hql)
		.setString("userId", userID)
		.setString("actId", actID)
		.uniqueResult();
		return uaa;
	}
	
	/**
	 * 根据活动ID和“角色用户状态”查询所有该状态用户角色关系
	 * @param actId
	 * @param uarState
	 * @param maxResult 最大返回条数
	 * @return
	 */
	public List<UserAndAct> loadByActAndState(String actId,int uaaState,int...maxResult){
		String hql = "select ua from UserAndAct ua join ua.act a where a.id=:actId and ua.uaaState=:uaaState ";
		
		Query query = getSession().createQuery(hql);
		
		List<UserAndAct> uaas =query
		.setString("actId", actId).setInteger("uaaState", uaaState)
		.list();
		
		if(maxResult!= null ){
			query.setMaxResults(maxResult[0]);
		}
			
		return uaas;
	}
	
	
	

	/**
	 * 根据用户ID查询所有该用户的用户角色关系
	 * @param userId
	 * @return
	 */
	public List<UserAndAct> loadUserAndActByUser(String userId){
		String hql = "select ua from UserAndAct ua where ua.user.id = :userId";
		List<UserAndAct> uaas = getSession().createQuery(hql)
		.setString("userId", userId)
		.list();
		return uaas;
	}
}
