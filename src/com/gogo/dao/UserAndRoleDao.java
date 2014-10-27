package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;


@Repository
public class UserAndRoleDao extends BaseDao<UserAndRole> {


	/**
	 * 根据用户ID和角色ID查询用户与角色的关联关系
	 * @param user
	 * @param role
	 * @return
	 */
	public User loadGroupUserByRole(String userID, String roleId){
		StringBuffer hql = new StringBuffer();
		hql.append(" select userAndRole.user from UserAndRole userAndRole where userAndRole.role.roleId=:roleId and userAndRole.user.userId=:userId ");
		Query query = getSession().createQuery(hql.toString())
				.setString("userId", userID)
				.setString("roleId", roleId);
		
		return  (User)query.uniqueResult();
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

	public List<UserAndRole> loadUserAndRoleByGroup(String groupId) {
		String hql = "select ur from UserAndRole ur left join ur.role r left join r.belongGroup g where g.id=?";
		return find(hql, groupId);
	}
	
}
