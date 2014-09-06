package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.Role;
import com.gogo.domain.User;

@Repository
public class RoleDao extends BaseDao<Role>{
	
	public void saveRole(Role user){
		save(user);
	}
	
	public List<Role> loadCurUserRole4Act(User user,Activity act){
//		String hql = "from Activity act left join act.joinUser ju left join ju.user u where u.userId='"+userId+"'";
		String hql = "select ur.role from UserAndRole ur left join ur.role.belongAct a where ur.user.userId = '"+user.getUserId()+"' and a.actId='"+act.getActId()+"'";
		List<Role> roles = (List<Role>) find(hql);
		return roles;
	}
}
