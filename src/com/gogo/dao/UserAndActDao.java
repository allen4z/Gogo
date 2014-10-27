package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.User;
import com.gogo.domain.UserAndAct;
import com.gogo.domain.UserAndRole;

@Repository
public class UserAndActDao extends BaseDao<UserAndAct> {

	
	/**
	 * 根据活动ID查询该活动所有的用户角色关系
	 * @param actId
	 * @return
	 */
	public List<UserAndAct> loadByAct(String actId){
		String hql = "select ua from UserAndAct ua join ua.act a  where a.actId=:actId";
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
		String hql = "select ua from UserAndAct ua join ua.act a where ua.user.userId = :userId and a.actId=:actId";
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
		String hql = "select ua from UserAndAct ua join ua.act a where a.actId=:actId and ua.uaaState=:uaaState ";
		
		Query query = getSession().createQuery(hql);
		
		List<UserAndAct> uaas =query
		.setString("actId", actId).setInteger("uaaState", uaaState)
		.list();
		
		if(maxResult!= null ){
			query.setMaxResults(maxResult[0]);
		}
			
		return uaas;
	}
	
	
	private static final String HQL_AILS="uaa";
	private static final String HQL_LIST="FROM UserAndAct"; 
	
	/**
	 * 根据活动ID查询活动用户Sql  查询参与用户，观众用户...
	 * @param isCount
	 * @param paramlist 权限状态（最多传入一个值，为空则查询所有用户）
	 * @return
	 */
	private String getActUserByActSql(boolean isCount,Object... paramlist){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select ");
		if(isCount){
			sqlBuffer.append(" count("+HQL_AILS+".user) ");
		}else{
			sqlBuffer.append(" "+HQL_AILS+".user ");
		}
		sqlBuffer.append(" "+HQL_LIST+" "+HQL_AILS+" join "+HQL_AILS+".act a where a.actId=? ");
		
		if(paramlist!=null && paramlist.length>0){
			sqlBuffer.append(" and "+HQL_AILS+".uaaState in (");
			for (int i = 0; i < paramlist.length; i++) {
				if(i==0){
					sqlBuffer.append(paramlist[i]);
				}else{
					sqlBuffer.append(","+paramlist[i]);
				}
			}
			sqlBuffer.append(")");
		}
		return sqlBuffer.toString();
	}
	
	/**
	 * 根据活动ID查询跟该活动有关的所有用户
	 * @param actId
	 * @param currPage
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<User> loadUserByAct(String actId,int currPage,int pageSize,Object...params){
		String hql = getActUserByActSql(false,params);
		List<User> userList = findByPage(hql,currPage,pageSize,actId);
		return userList;
	}
	
	/**
	 * 根据活动ID查询跟该活动有关的所有用户的数量
	 * @param actId
	 * @param params
	 * @return
	 */
	public int loadUserByActCount (String actId,Object...params){
		String hql = getActUserByActSql(true,params);
		return  this.<Number>getCount(hql,actId).intValue();
	}

	/**
	 * 根据用户ID查询所有该用户的用户角色关系
	 * @param userId
	 * @return
	 */
	public List<UserAndAct> loadUserAndActByUser(String userId){
		String hql = "select ua from UserAndAct ua where ua.user.userId = :userId";
		List<UserAndAct> uaas = getSession().createQuery(hql)
		.setString("userId", userId)
		.list();
		return uaas;
	}
}
