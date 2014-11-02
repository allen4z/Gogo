package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Invite;

@Repository
public class InviteDao extends BaseDao<Invite>{

	private String getAllInviteHql(boolean isCount){
		String hql = "";
		if(isCount){
			hql +="select count(invite) ";
		}else{
			hql +="select invite ";
		}
		
		hql +=  "from Invite invite left join invite.beInvited beInvitedId "
				+ " where beInvitedId.id=? and invite.type=?";
		return hql;
	}
	
	public int loadAllInviteCount(String beInvitedId,int type){
		String hql = getAllInviteHql(true);
		return getCount(hql, beInvitedId,type);
	}
	
	public List<Invite> loadAllInvite(String beInvitedId,int type){
		String hql = getAllInviteHql(false);
		return find(hql, beInvitedId,type);
	}
	
	
}
