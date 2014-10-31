package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Invite;

@Repository
public class InviteDao extends BaseDao<Invite>{

	
	public List<Invite> loadAllInvite(String beInvitedId){
		String hql = "from Invite invite left join invite.beInvited beInvitedId "
				+ " where beInvitedId.id=?";
		
		return find(hql, beInvitedId);
	}
}
