package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.enums.GroupApplyState;

@Repository
public class GroupApplyInfoDao extends BaseDao<GroupApplyInfo> {
	
	
	public List<GroupApplyInfo> loadAllApplyInfo(String groupId,GroupApplyState state) {
		String hql = "select gai from GroupApplyInfo gai "
				+ " left join gai.group g "
				+ " where g.id in(:groupIds) "
				+ " and gai.state="+state.ordinal()+" order by g.id ";
		Query query = getSession().createQuery(hql);
		query.setString("groupIds", groupId);
		return query.list();
		
	}
	
	public GroupApplyInfo loadApplyInfo(String userId,String groupId){
		String hql = "select gai from GroupApplyInfo gai "
				+ " left join gai.group g "
				+ " left join gai.user u"
				+ " where u.id =? "
				+ "	and g.id =?"
				+ " and gai.state="+GroupApplyState.APPLY.ordinal();
		return findUnique(hql, userId,groupId);
	}

}
