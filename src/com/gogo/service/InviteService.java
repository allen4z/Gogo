package com.gogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.dao.ActivityDao;
import com.gogo.dao.GroupDao;
import com.gogo.dao.InviteDao;
import com.gogo.dao.UserDao;
import com.gogo.domain.Invite;
import com.gogo.domain.User;
import com.gogo.domain.enums.InviteType;
import com.gogo.page.Page;
import com.gogo.page.PageUtil;

@Service
public class InviteService {
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private ActivityDao activityDao;
	
	public void saveInviteJoinGroup(User user,String friendId,String groupId){
		Invite invite = new Invite();
		invite.setUser(user);
		invite.setBeInvited(userDao.get(friendId));
		invite.setGroup(groupDao.get(groupId));
		inviteDao.save(invite);
		//TODO 推送邀请信息
	}

	public void saveInviteJoinAct(User user, String friendId, String actId) {
		Invite invite = new Invite();
		invite.setUser(user);
		invite.setBeInvited(userDao.get(friendId));
		invite.setActivity(activityDao.get(actId));
		inviteDao.save(invite);
		//TODO 推送邀请信息
	}

	public Page<Invite> loadAllInvite(User user,InviteType type,int pn,int ps) {
		return PageUtil.getPage(inviteDao.loadAllInviteCount(user.getId(),type), pn, inviteDao.loadAllInvite(user.getId(),type),ps);
	}

}
