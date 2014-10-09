package com.gogo.dao;

import java.util.ArrayList;
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
	public List<Role> loadRoleByUserAndAct(User user,Activity act){
	
		String hql = "select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		List<Role> roles = getSession().createQuery(hql)
		.setString("userId", user.getUserId())
		.setString("actId", act.getActId())
		.list();
		
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
	
	public UserAndRole loadUserAndRoleByUserAndAct(User user,Activity act){
	
		String hql = "select ur from UserAndRole ur join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		UserAndRole uar = (UserAndRole) getSession().createQuery(hql)
		.setString("userId", user.getUserId())
		.setString("actId", act.getActId())
		.uniqueResult();
		
		return uar;
	}
	
	
	public List<UserAndRole> loadUserAndRoleByUser(String userId){
		
		String hql = "select ur from UserAndRole ur where ur.user.userId = :userId";
		
		List<UserAndRole> uars = getSession().createQuery(hql)
		.setString("userId", userId)
		.list();
		
		return uars;
	}
}
