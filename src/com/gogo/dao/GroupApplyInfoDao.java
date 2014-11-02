package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.helper.DomainStateHelper;

@Repository
public class GroupApplyInfoDao extends BaseDao<GroupApplyInfo> {

	public List<GroupApplyInfo> loadAllApplyInfo(String groupId) {
		String hql = "from GroupApplyInfo gai left join gai.group g where g.groupId = ? and gai.state="+DomainStateHelper.GROUP_APPLY_STATE_APPLY;
		return find(hql,groupId);
	}

}
