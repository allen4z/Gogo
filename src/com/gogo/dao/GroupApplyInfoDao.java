package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.GroupApplyInfo;
import com.gogo.domain.enums.GroupApplyState;

@Repository
public class GroupApplyInfoDao extends BaseDao<GroupApplyInfo> {

	public List<GroupApplyInfo> loadAllApplyInfo(String groupId) {
		String hql = "from GroupApplyInfo gai left join gai.group g where g.groupId = ? and gai.state="+GroupApplyState.APPLY.ordinal();
		return find(hql,groupId);
	}

}
