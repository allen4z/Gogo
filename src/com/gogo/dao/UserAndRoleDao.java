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
	
	public User loadActUserByRole(User user, Role role){
		StringBuffer hql = new StringBuffer();
		hql.append(" select userAndRole.user from UserAndRole userAndRole where userAndRole.role.roleId=:roleId and userAndRole.user.userId=:userId ");
		Query query = getSession().createQuery(hql.toString())
				.setString("userId", user.getUserId())
				.setString("roleId", role.getRoleId());
		
		return  (User)query.uniqueResult();
	}
	
	public UserAndRole loadUserAndRoleByUserAndAct(User user,Activity act){
	
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		UserAndRole uar = (UserAndRole) getSession().createQuery(hql)
		.setString("userId", user.getUserId())
		.setString("actId", act.getActId())
		.uniqueResult();
		
		return uar;
	}
	
	public List<UserAndRole> loadUserAndRoleByAct(String actId){
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where a.actId=:actId";
		List<UserAndRole> uars = getSession().createQuery(hql)
		.setString("actId", actId)
		.list();
		return uars;
	}
	
	public List<UserAndRole> loadUserAndRoleByUser(String userId){
		String hql = "select ur from UserAndRole ur where ur.user.userId = :userId";
		List<UserAndRole> uars = getSession().createQuery(hql)
		.setString("userId", userId)
		.list();
		return uars;
	}
	
	/**
	 * 根据活动ID查询活动用户Sql
	 * @param isCount
	 * @param paramlist 权限状态（只能传入一个值）
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
	
	public List<User> loadActUserByAct(String actId,int currPage,int pageSize,Object...params){
		String hql = getActUserByActSql(false,params);
		List<User> userList = findByPage(hql,currPage,pageSize,actId);
		return userList;
	}
	
	public int loadUserAndRoleByActCount (String actId,Object...params){
		String hql = getActUserByActSql(true,params);
		return  this.<Number>getCount(hql,actId).intValue();
	}
}
