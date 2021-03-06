package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Invite;
import com.gogo.domain.enums.InviteState;
import com.gogo.domain.enums.InviteType;

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
				+ " where beInvitedId.id=? and invite.type=? and invite.state=?";
		return hql;
	}
	
	public int loadAllInviteCount(String beInvitedId,InviteType type,InviteState state){
		String hql = getAllInviteHql(true);
		return getCount(hql, beInvitedId,type.ordinal(),state.ordinal());
	}
	
	public List<Invite> loadAllInvite(String beInvitedId,InviteType type,InviteState state){
		String hql = getAllInviteHql(false);
		return find(hql, beInvitedId,type.ordinal(),state.ordinal());
	}
	
	
}
