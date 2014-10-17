package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;
import com.gogo.domain.helper.RoleHelper;


@Repository
public class UserAndRoleDao extends BaseDao<UserAndRole> {

	private static final String HQL_AILS="uar";
	private static final String HQL_LIST="FROM UserAndRole"; 
	/**
	 * 查询当前用户当前活动的的角色信息
	 * @param user
	 * @param act
	 * @return
	 */
	public List<Role> loadRoleByUserAndAct(User user,Activity act){
	
		String hql = "select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		List<Role> roles = getSession().createQuery(hql)
		.setString("userId", user.getUserId())
		.setString("actId", act.getActId())
		.list();
		
		return roles;
	}
	
	/**
	 * 根据用户ID和角色ID查询用户与角色的关联关系
	 * @param user
	 * @param role
	 * @return
	 */
	public User loadActUserByRole(String userID, String roleId){
		StringBuffer hql = new StringBuffer();
		hql.append(" select userAndRole.user from UserAndRole userAndRole where userAndRole.role.roleId=:roleId and userAndRole.user.userId=:userId ");
		Query query = getSession().createQuery(hql.toString())
				.setString("userId", userID)
				.setString("roleId", roleId);
		
		return  (User)query.uniqueResult();
	}
	
	/**
	 * 根据用户和活动ID查询用户角色的关联关系
	 * @param userID
	 * @param actID
	 * @return
	 */
	public UserAndRole loadUserAndRoleByUserAndAct(String userID,String actID){
	
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		UserAndRole uar = (UserAndRole) getSession().createQuery(hql)
		.setString("userId", userID)
		.setString("actId", actID)
		.uniqueResult();
		
		return uar;
	}
	
	/**
	 * 根据活动ID查询该活动所有的用户角色关系
	 * @param actId
	 * @return
	 */
	public List<UserAndRole> loadUserAndRoleByAct(String actId){
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where a.actId=:actId";
		List<UserAndRole> uars = getSession().createQuery(hql)
		.setString("actId", actId)
		.list();
		return uars;
	}
	
	/**
	 * 根据活动ID和“角色用户状态”查询所有该状态用户角色关系
	 * @param actId
	 * @param uarState
	 * @param maxResult 最大返回条数
	 * @return
	 */
	public List<UserAndRole> loadUserAndRoleByActAndState(String actId,int uarState,int...maxResult){
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where a.actId=:actId and ur.uarState=:uarState ";
		
		Query query = getSession().createQuery(hql);
		
		List<UserAndRole> uars =query
		.setString("actId", actId).setInteger("uarState", uarState)
		.list();
		
		if(maxResult!= null ){
			query.setMaxResults(maxResult[0]);
		}
			
		return uars;
	}
	
	/**
	 * 根据用户ID查询所有该用户的用户角色关系
	 * @param userId
	 * @return
	 */
	public List<UserAndRole> loadUserAndRoleByUser(String userId){
		String hql = "select ur from UserAndRole ur where ur.user.userId = :userId";
		List<UserAndRole> uars = getSession().createQuery(hql)
		.setString("userId", userId)
		.list();
		return uars;
	}
	
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
		sqlBuffer.append(" "+HQL_LIST+" "+HQL_AILS+" join "+HQL_AILS+".role.belongAct a where a.actId=? ");
		
		if(paramlist!=null && paramlist.length>0){
			sqlBuffer.append(" and "+HQL_AILS+".uarState in (");
			int curState = (Integer) paramlist[0]; // 1
			int maxState = RoleHelper.MAX_ACTITVITY;
			for (int i = curState; i <=maxState; i++) {
				if(i==curState){
					sqlBuffer.append(i);
				}else if(RoleHelper.judgeState(i, curState)){
					sqlBuffer.append(","+i);
				}
			}
			sqlBuffer.append(" )");
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
	public List<User> loadActUserByAct(String actId,int currPage,int pageSize,Object...params){
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
	public int loadUserAndRoleByActCount (String actId,Object...params){
		String hql = getActUserByActSql(true,params);
		return  this.<Number>getCount(hql,actId).intValue();
	}
}
