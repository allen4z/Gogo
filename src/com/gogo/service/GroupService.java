package com.gogo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.GroupDao;
import com.gogo.dao.UserAndRoleDao;
import com.gogo.domain.Group;
import com.gogo.domain.Role;
import com.gogo.domain.User;
import com.gogo.domain.UserAndRole;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;

@Service
public class GroupService {

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private UserAndRoleDao userAndRoleDao;
	
	
	public void saveGroup(Group group, User user) {
		group.setCreateUser(user);
		groupDao.save(group);
	}

	/**
	 * 用户加入小组
	 * @param groupId
	 * @param user
	 * @param visitorCode
	 * @return
	 */
	private UserAndRole saveUserJoinGroup(String groupId, User user,
			String roleCode) {
		Group group = groupDao.load(groupId);
		Set<Role> roles= group.getRoles();
		Iterator<Role> it = roles.iterator();
		
		Role joinRole = null;
		UserAndRole uar = null;
		//1.用户第一次分配角色
		uar = new UserAndRole();
		uar.setUser(user);
	
		//2.查询当前活动是否已经有游客角色
		while(it.hasNext()){
			Role role = it.next();
			if(role.getRoleCode().equals(roleCode)){
				joinRole = role;
				break;
			}
		}
		
		//3.如果没有则新建角色信息
		if(joinRole == null){
			joinRole = new Role();
			joinRole.setRoleCode(roleCode);
			joinRole.setRoleName(RoleHelper.getRoleInfo().get(roleCode));
			joinRole.setBelongGroup(group);
		}else{
			User haveUser = userAndRoleDao.loadGroupUserByRole(user.getUserId(), joinRole.getRoleId());
			if(haveUser != null){
				throw new Business4JsonException("act_you_have_registered","You have registered");
			}
		}
		uar.setRole(joinRole);
		
		//5、判断是否重复加入
		if(joinRole.getBelongUser() !=null){
			if(joinRole.getBelongUser().contains(uar)){
				throw new Business4JsonException("you joined this activity!");
			}
		}
		
		groupDao.update(group);
		userAndRoleDao.saveOrUpdate(uar);
		return uar;
	}
	
	
	/**
	 * 分配权限
	 * @param groupId
	 * @param user
	 * @param uarJoinActivity
	 * @return
	 */
	public int updateAddGroup4UARState(String groupId, User user,
			int uarJoinActivity) {
		int result = RoleHelper.JOIN_SUCCESS;
		Group group = groupDao.load(groupId);
		
		//1、得到活动所有的角色用户关系
		List<UserAndRole> uars = userAndRoleDao.loadUserAndRoleByGroup(groupId);
		//2.得到已经报名的人数
		int hasUserCount = 0;
		//3.得到当前用户与活动角色的关系
		UserAndRole  uar = null;
		for (UserAndRole userAndRole : uars) {
			//当前用户的关联关系
			if(userAndRole.getUser().getUserId().equals(user.getUserId())){
				uar = userAndRole;
				//uar = userAndRoleDao.loadUserAndRoleByUserAndAct(user.getUserId(), actId);
				//如果当前用户的权限包含了需要变更的权限，则为重复加入
				if(RoleHelper.judgeState(userAndRole.getUarState(), uarJoinActivity)){
					throw new Business4JsonException("act_you_joined_this_activity","you joined this activity!");
				}
			}
			int curState = userAndRole.getUarState();
			if(RoleHelper.judgeState(curState, uarJoinActivity)){
				hasUserCount += 1;
			}
		}	
		
		//如果用户没有关联人和该活动的角色，则提示用户先加入活动小组
		if(uar == null){
			//如果当前用户没有加入小组，则自动加入小组
			uar = saveUserJoinGroup(groupId, user, RoleHelper.VISITOR_CODE);
		}
		//4、分配权限，合并已有权限和变更权限 （例如：参加活动，观看活动，投资活动等不同权限时）
		int chagedState=RoleHelper.mergeState(uar.getUarState(), uarJoinActivity);
		uar.setUarState(chagedState);
				
		userAndRoleDao.update(uar);
		return result;
	}


 
}
