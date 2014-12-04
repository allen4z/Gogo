package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.InviteDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Invite;
import com.gogo.domain.User;
import com.gogo.domain.enums.InviteType;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class InviteService extends BaseService{
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private UserDao userDao;
	
	public void saveInviteJoinGroup(String tokenId,String friendId,String groupId){
		User user = getUserbyToken(tokenId);
		Invite invite = new Invite();
		invite.setUser(user);
		invite.setBeInvited(userDao.get(friendId));
//		invite.setGroup(groupDao.get(groupId));
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

	public Page<Invite> loadAllInvite(String tokenId,InviteType type,int pn,int ps) {
		User user = getUserbyToken(tokenId);
		return PageUtil.getPage(inviteDao.loadAllInviteCount(user.getId(),type), pn, inviteDao.loadAllInvite(user.getId(),type),ps);
	}

}
