package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.GroupDao;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Group;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.UserAndGroup;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class GroupService {

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private UserAndGroupDao userAndGroupDao;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 保存Group信息
	 * @param group
	 * @param user
	 */
	public void saveGroup(Group group, User user) {
		group.setCreateUser(user);
		group.setMaxJoinUser(DomainStateHelper.GROUP_DEFAULT_USER_SIZE);
		group.setCurJoinUser(1);
		
		int maxAuthority  = RoleHelper.FOUR_AUTHORITY_EXPEL;
		UserAndGroup uag =new UserAndGroup();
		uag.setGroup(group);
		uag.setUser(user);
		uag.setAuthorityState(maxAuthority);
		userAndGroupDao.save(uag);
		groupDao.save(group);
	}
	
	/**
	 * 用户加入活动小组
	 * @param user
	 * @param groupId
	 */
	public synchronized void saveUserJoinGroup(User user, String groupId) {

		Group group = groupDao.load(groupId);
		//0.判断是否超过人员上线
		if(group.getCurJoinUser()>=group.getMaxJoinUser()){
			throw new Business4JsonException("小组人数已满");
		}
		
		UserAndGroup uag = userAndGroupDao.loadUAG4UserAndGroup(user.getId(), groupId);
		if(uag != null){
			throw new Business4JsonException("您已经加入了小组");
		}
		uag = new UserAndGroup();
		uag.setGroup(group);
		uag.setUser(user);
		uag.setAuthorityState(RoleHelper.TOW_AUTHORITY_TEXT);
		userAndGroupDao.saveOrUpdate(uag);
		groupDao.update(group);
	}
	
	
	/**
	 * 更改用户权限
	 * @param user
	 * @param groupId
	 * @param userId
	 * @param authority
	 */
	public synchronized void updateUserAuthority(User user, String groupId, String userId,int authority) {
		//判断是否允许修改
		UserAndGroup  uag = userAndGroupDao.loadUAG4UserAndGroup(user.getId(), groupId);
		
		int curAuth = uag.getAuthorityState();
		
		if(authority>curAuth){
			throw new Business4JsonException("被分配的权限比当前用户的权限大");
		}
		UserAndGroup  uagAuth = userAndGroupDao.loadUAG4UserAndGroup(userId, groupId);
		uagAuth.setAuthorityState(RoleHelper.mergeState(uagAuth.getAuthorityState(), authority));
		
	}

	//@TODO 按地区未实现
	public Page<Group> loadAllGroup(User user, Place place, String remoteAddr,
			Integer pn, int pageSize){
		return PageUtil.getPage(groupDao.loadAllGroupCount(), pn, groupDao.loadAllGroup(pn, pageSize), pageSize);
	}
	
	public Page<Group> loadGroup4User(User user,int currPage,int pageSize) {
		return PageUtil.getPage(groupDao.loadGroup4UserCount(user.getId()), 0, groupDao.loadGroup4User(user.getId(), currPage, pageSize), pageSize);
	}

	public Group loadGroupById(String groupId) {
		return groupDao.get(groupId);
	}
	
}
