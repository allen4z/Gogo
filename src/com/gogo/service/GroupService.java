package com.gogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.GroupApplyInfoDao;
import com.gogo.dao.GroupDao;
import com.gogo.dao.InviteDao;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Group;
import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.Invite;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.domain.UserAndGroup;
import com.gogo.domain.enums.GroupApplyState;
import com.gogo.domain.enums.UserAndGroupState;
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
	@Autowired
	private GroupApplyInfoDao groupApplyInfoDao;
	@Autowired
	private InviteDao inviteDao;
	
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
		uag.setState(UserAndGroupState.FORMAL);
		uag.setGroup(group);
		uag.setUser(user);
		uag.setAuthorityState(RoleHelper.mergeParamState(maxAuthority));
		userAndGroupDao.save(uag);
		groupDao.save(group);
	}
	
	/**
	 * 保存申请信息
	 * @param user
	 * @param groupId
	 */
	public void saveApplyJoinGroup(User user, String groupId) {
		Group group = groupDao.load(groupId);
		//0.判断是否超过人员上线
		if(group.getCurJoinUser()>=group.getMaxJoinUser()){
			throw new Business4JsonException("小组人数已满");
		}
		
		UserAndGroup uag = userAndGroupDao.loadUAG4UserAndGroup(user.getId(), groupId);
		if(uag != null){
			throw new Business4JsonException("您已经加入了小组");
		}
		
		GroupApplyInfo gai = new GroupApplyInfo();
		gai.setUser(user);
		gai.setGroup(group);
		gai.setState(GroupApplyState.APPLY);
		
		groupApplyInfoDao.save(gai);
	}
	
	/**
	 * 管理员通过用户申请
	 * @param user
	 * @param groupId
	 */
	public synchronized void savePassApply(User user,String groupId,String userId) {
		checkAuth(user, groupId);
		User applyUser = userDao.load(userId);
		Group group = groupDao.load(groupId);
		
		//0.判断是否超过人员上线
		userAndGroupHandler(applyUser, group);
	}

	/**
	 * 用户通过邀请加入小组
	 * @param user
	 * @param inviteId
	 */
	public void savePassInviteGroup(User user, String inviteId) {
		Invite invite= inviteDao.load(inviteId);
//		Group group = invite.getGroup();
		Group group = groupDao.load(invite.getEntityId());
		userAndGroupHandler(user, group);
		
	}
	
	/**
	 * 查询改小组所有的申请信息
	 * @author allen
	 */
	public List<GroupApplyInfo> loadAllApplyInfo(User user,String groupId) {
		checkAuth(user, groupId);
		return groupApplyInfoDao.loadAllApplyInfo(groupId);
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
	
	private void userAndGroupHandler(User applyUser, Group group) {
		if(group.getCurJoinUser()>=group.getMaxJoinUser()){
			throw new Business4JsonException("小组人数已满");
		}
		
		UserAndGroup checkUag = userAndGroupDao.loadUAG4UserAndGroup(applyUser.getId(), group.getId());
		if(checkUag != null){
			throw new Business4JsonException("您已经加入了小组");
		}
		UserAndGroup applyUag = new UserAndGroup();
		applyUag.setState(UserAndGroupState.FORMAL);
		applyUag.setGroup(group);
		applyUag.setUser(applyUser);
		applyUag.setAuthorityState(RoleHelper.mergeParamState(RoleHelper.TOW_AUTHORITY_TEXT));
		userAndGroupDao.saveOrUpdate(applyUag);
		
		group.setCurJoinUser(group.getCurJoinUser()+1);
		groupDao.update(group);
	}

	private void checkAuth(User user, String groupId) {
		UserAndGroup uag = userAndGroupDao.loadUAG4UserAndGroup(user.getId(), groupId);
		int authstate = uag.getAuthorityState();
		//如果用户不包含第三级别的权限，则无权查看申请列表
		if(!RoleHelper.judgeState(authstate, RoleHelper.THREE_AUTHORITY_INVITE)){
			throw new Business4JsonException("您无权查看改小组的申请列表！");
		}
	}

	public void quitGroup(User user, String groupId) {
		UserAndGroup uag = userAndGroupDao.loadUAG4UserAndGroup(user.getId(), groupId);
		if(uag == null){
			throw new Business4JsonException("您不在此小组");
		}
		uag.setAuthorityState(RoleHelper.ONE_AUTHORITY_NONE);
		
		uag.setState(UserAndGroupState.QUIT);
		userAndGroupDao.update(uag);
	}
}
