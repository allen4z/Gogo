package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.enums.GroupApplyState;

@Repository
public class GroupApplyInfoDao extends BaseDao<GroupApplyInfo> {

	public List<GroupApplyInfo> loadGroupApplyInfo(String groupId) {
		String hql = "from GroupApplyInfo gai left join gai.group g where g.groupId = ? and gai.state="+GroupApplyState.APPLY.ordinal()+" order g.id ";
		return find(hql,groupId);
	}
	
	
	public List<GroupApplyInfo> loadAllApplyInfo(String[] groupIds) {
		String hql = "select gai from GroupApplyInfo gai left join gai.group g where g.id in(:groupIds) and gai.state="+GroupApplyState.APPLY.ordinal()+" order by g.id ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("groupIds", groupIds);
		return query.list();
		
	}

}
