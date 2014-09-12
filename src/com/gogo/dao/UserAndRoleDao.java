package com.gogo.dao;

import java.util.List;

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
		//String hql = "select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = '"+user.getUserId()+"' and a.actId='"+act.getActId()+"'";
	
		String hql = "select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = :userId and a.actId=:actId";
		
		List<Role> roles = getSession().createQuery(hql)
		.setString("userId", user.getUserId())
		.setString("actId", act.getActId())
		.list();
		
		return roles;
	}
}
