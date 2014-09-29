package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;


@Repository
public class UserAndRoleDao extends BaseDao<UserAndRole> {

	
	/**
	 * 查询当前用户当前活动的的角色信息
	 * @param user
	 * @param act
	 * @return
	 */
	public List<Role> loadCurUserRole4Act(User user,Activity act){
	
		StringBuffer hql = new StringBuffer();
		
		hql.append("select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId ");
		
		Query query = getSession().createQuery(hql.toString())
				.setString("userId", user.getUserId())
				.setString("actId", act.getActId());
		
		List<Role> roles = query.list();
		
		return roles;
	}
	
	public User loadActUser4Role(User user, Role role){
		StringBuffer hql = new StringBuffer();
		hql.append(" select userAndRole.user from UserAndRole userAndRole where userAndRole.role.roleId=:roleId and userAndRole.user.userId=:userId ");
		Query query = getSession().createQuery(hql.toString())
				.setString("userId", user.getUserId())
				.setString("roleId", role.getRoleId());
		
		return  (User)query.uniqueResult();
	}
}
