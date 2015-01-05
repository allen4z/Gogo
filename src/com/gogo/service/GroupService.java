package com.gogo.service;

import java.util.Date;
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
import com.gogo.domain.enums.InviteState;
import com.gogo.domain.enums.InviteType;
import com.gogo.domain.enums.UserAndGroupState;
import com.gogo.domain.helper.DomainStateHelper;
import com.gogo.domain.helper.RoleHelper;
import com.gogo.exception.Business4JsonException;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class GroupService extends BaseService {

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
	public void saveGroup(Group group, String tokenId) {
		User user  =  getUserbyToken(tokenId);
		//查询是否已经加入小组
		List<UserAndGroup> uags = userAndGroupDao.loadByUser(user.getId());
		if(uags!= null && uags.size()>0){
			for (UserAndGroup userAndGroup : uags) {
				if(userAndGroup.getGroup().getCreateUser().getId().equals(user.getId())){
					throw new Business4JsonException("您已经创建了["+userAndGroup.getGroup().getName()+"]，不能新建球队");
				}
			}
		}
		
		group.setCreateUser(user);
		group.setMaxJoinUser(DomainStateHelper.GROUP_DEFAULT_USER_SIZE);
		group.setCurJoinUser(1);
		group.setCreateTime(new Date());
		
		int maxAuthority  = RoleHelper.ROLE_SUPERMANAGER;
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
	public void saveApplyJoinGroup(String tokenId, String groupId) {
		User user  =  getUserbyToken(tokenId);
		
		Group group = groupDao.load(groupId);
		//0.判断是否超过人员上线
		if(group.getCurJoinUser()>=group.getMaxJoinUser()){
			throw new Business4JsonException("小组人数已满");
		}
		//判断是否加入过小组
		UserAndGroup uag = userAndGroupDao.loadUAG4User(user.getId());
		
		if(uag != null && uag.getState() == UserAndGroupState.FORMAL){
			throw new Business4JsonException("您已经加入了球队:"+uag.getGroup().getName());
		}
		
		GroupApplyInfo gai = groupApplyInfoDao.loadApplyInfo(user.getId(), groupId);
		if(gai==null){
			gai = new GroupApplyInfo();
		}else{
			throw new Business4JsonException("您已经申请过加入球队["+group.getName()+"],请等待队长审核！");
		}
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
	public synchronized void savePassApply(String tokenId,String groupApplyId) {
		User user  =  getUserbyToken(tokenId);
		
		GroupApplyInfo gai = groupApplyInfoDao.load(groupApplyId);
		Group group = gai.getGroup();
		checkAuth(user, group.getId());
		//0.判断是否超过人员上线
		User applyUser = gai.getUser();
		userAndGroupHandler(applyUser, group);
		gai.setState(GroupApplyState.PASSED);
		groupApplyInfoDao.update(gai);
	}

	/**
	 * 用户通过邀请加入小组
	 * @param user
	 * @param inviteId
	 */
	public void savePassInviteGroup(String tokenId, String inviteId) {
		User user  =  getUserbyToken(tokenId);
		//查询是否已经加入小组
		List<UserAndGroup> uags = userAndGroupDao.loadByUser(user.getId());
		try {
			if(uags != null && uags.size()>DomainStateHelper.JOIN_GROUP_MAX){
				throw new Business4JsonException("您已经加入了"+DomainStateHelper.JOIN_GROUP_MAX+"支球队，不能加入其他球队");
			}

			Invite invite= inviteDao.load(inviteId);
			Group group = groupDao.load(invite.getEntityId());
			userAndGroupHandler(user, group);
			
		} catch (RuntimeException e) {
			throw e;
		}finally{
			//清除所有受邀信息
			List<Invite> invites = inviteDao.loadAllInvite(user.getId(), InviteType.GROUP,InviteState.WAITING);
			for (Invite invite : invites) {
				invite.setState(InviteState.DEL);
			}
		}
	}
	
	/**
	 * 查询当前用户所管理的小组的人员申请信息
	 * @author allen
	 */
	public List<GroupApplyInfo> loadAllApplyInfo(String tokenId,GroupApplyState state) {
		User user  =  getUserbyToken(tokenId);
		//获得所有拥有申请权限的小组
		UserAndGroup uag =userAndGroupDao.loadAllUserAndGroup(user.getId(),
				RoleHelper.getAuthInfo(RoleHelper.ROLE_MANAGER,RoleHelper.ROLE_SUPERMANAGER));
		if(uag!=null){
			Group group = uag.getGroup();
			return groupApplyInfoDao.loadAllApplyInfo(group.getId(),state);
		}else{
			return null;
		}
	}
	
	/**
	 * 更改用户权限
	 * @param user
	 * @param groupId
	 * @param userId
	 * @param authority
	 */
	public synchronized void updateUserAuthority(String tokenId, String groupId, String userId,int authority) {
		User user  =  getUserbyToken(tokenId);
		
		//判断是否允许修改
		UserAndGroup  uag = userAndGroupDao.loadByUserAndGroup(user.getId(), groupId,UserAndGroupState.FORMAL);
		
		int curAuth = uag.getAuthorityState();
		
		if(authority>curAuth){
			throw new Business4JsonException("被分配的权限比当前用户的权限大");
		}
		UserAndGroup  uagAuth = userAndGroupDao.loadByUserAndGroup(userId, groupId,UserAndGroupState.FORMAL);
		uagAuth.setAuthorityState(RoleHelper.mergeState(uagAuth.getAuthorityState(), authority));
		
	}

	//@TODO 按地区未实现
	public Page<Group> loadAllGroup(Place place, String remoteAddr,
			Integer pn, int pageSize){
		return PageUtil.getPage(groupDao.loadAllGroupCount(), pn, groupDao.loadAllGroup(pn, pageSize), pageSize);
	}
	
	public Page<Group> loadGroup4User(String tokenId,int currPage,int pageSize) {
		User user = getUserbyToken(tokenId);
		return PageUtil.getPage(groupDao.loadGroup4UserCount(user.getId()), 0, groupDao.loadGroup4User(user.getId(), currPage, pageSize), pageSize);
	}

	public Group loadGroupById(String groupId) {
		return groupDao.get(groupId);
	}
	
	private void userAndGroupHandler(User applyUser, Group group) {
		if(group.getCurJoinUser()>=group.getMaxJoinUser()){
			throw new Business4JsonException("小组人数已满");
		}
		UserAndGroup checkUag = userAndGroupDao.loadByUserAndGroup(applyUser.getId(), group.getId(),UserAndGroupState.FORMAL);
		if(checkUag != null){
			throw new Business4JsonException("您已经加入了小组");
		}
		UserAndGroup applyUag = new UserAndGroup();
		applyUag.setState(UserAndGroupState.FORMAL);
		applyUag.setGroup(group);
		applyUag.setUser(applyUser);
		applyUag.setAuthorityState(RoleHelper.mergeParamState(RoleHelper.ROLE_MEMBER));
		userAndGroupDao.saveOrUpdate(applyUag);
		group.setCurJoinUser(group.getCurJoinUser()+1);
		groupDao.update(group);
	}

	private void checkAuth(User user, String groupId) {
		UserAndGroup uag = userAndGroupDao.loadByUserAndGroup(user.getId(), groupId,UserAndGroupState.FORMAL);
		int authstate = uag.getAuthorityState();
		//如果用户不包含第三级别的权限，则无权查看申请列表
		if(!RoleHelper.judgeState(authstate, RoleHelper.FOUR_AUTHORITY_EXPEL)){
			throw new Business4JsonException("您无权查看改小组的申请列表！");
		}
	}

	public void updateQuitGroup(String tokenId, String groupId) {
		User user  =  getUserbyToken(tokenId);
		
		UserAndGroup uag = userAndGroupDao.loadByUserAndGroup(user.getId(), groupId,UserAndGroupState.FORMAL);
		if(uag == null){
			throw new Business4JsonException("您不在此小组");
		}
		//如果用户是最大权限的用户 -- 管理员
		if(RoleHelper.judgeState(uag.getAuthorityState(), RoleHelper.ROLE_SUPERMANAGER)){
			throw new Business4JsonException("队长退出球队需将球队转移给其他人");
		}
		
		uag.setAuthorityState(RoleHelper.ONE_AUTHORITY_NONE);
		uag.setState(UserAndGroupState.QUIT);
		userAndGroupDao.update(uag);
	}
}
