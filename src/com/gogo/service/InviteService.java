package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.InviteDao;
import com.gogo.dao.UserAndGroupDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Invite;
import com.gogo.domain.User;
import com.gogo.domain.UserAndGroup;
import com.gogo.domain.enums.InviteState;
import com.gogo.domain.enums.InviteType;
import com.gogo.exception.Business4JsonException;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class InviteService extends BaseService{
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAndGroupDao userAndGroupDao;
	
	public void saveInviteJoinGroup(String tokenId,String friendId,String groupId){
		//查询受邀用户是否已经加入其他球队
		UserAndGroup uags = userAndGroupDao.loadByUser(friendId);
		if(uags != null){
			throw new Business4JsonException("用户已经加入了["+uags.getGroup().getName()	+"]，不能接受邀请");
		}
		User user = getUserbyToken(tokenId);
		Invite invite = new Invite();
		invite.setUser(user);
		invite.setBeInvited(userDao.get(friendId));
		invite.setEntityId(groupId);
		inviteDao.save(invite);
		//TODO 推送邀请信息
	}

	public void saveInviteJoinAct(String tokenId, String friendId, String actId) {
		User user = getUserbyToken(tokenId);
		Invite invite = new Invite();
		invite.setUser(user);
		invite.setBeInvited(userDao.get(friendId));
//		invite.setActivity(activityDao.get(actId));
		invite.setEntityId(actId);
		inviteDao.save(invite);
		//TODO 推送邀请信息
	}

	public Page<Invite> loadAllInvite(String tokenId,InviteType type,InviteState state,int pn,int ps) {
		User user = getUserbyToken(tokenId);
		return PageUtil.getPage(inviteDao.loadAllInviteCount(user.getId(),type,state), pn, inviteDao.loadAllInvite(user.getId(),type,state),ps);
	}

}
