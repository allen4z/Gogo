package com.gogo.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.gogo.domain.MatchList;
import com.gogo.domain.enums.GroupMatchState;

@Repository
public class MatchesDao extends BaseDao<MatchList> {


	/**
	 * 根据所属球队和状态查询所有比赛列表
	 * @param groupId
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MatchList> loadAllMatchByUser(String groupId, GroupMatchState state) {
		StringBuffer hql =new StringBuffer();
		hql.append("select matchList from MatchList matchList left join matchList.belongGroup belongGroup ");
		hql.append(" where belongGroup.id in( :ids ) ");
		
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("ids", groupId);
		
		if(state!=null){
			hql.append(" and matchList.state=:state ");
			query.setParameter("state", state);
		}
		
		return query.list();
	}

	/**
	 * 查询所有受邀比赛信息
	 * @param id
	 * @param state
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MatchList> loadAllInviteMatchByUser(String groupIds, GroupMatchState state) {
		StringBuffer hql =new StringBuffer();
		hql.append("select matchList from MatchList matchList left join matchList.otherGroup otherGroup ");
		hql.append(" where otherGroup.id in(:ids) ");
		
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("ids", groupIds);
		
		if(state!=null){
			hql.append(" and matchList.state=:state ");
			query.setParameter("state", state);
		}
		return query.list();
	}
	
	public MatchList loadMatchById(String matchListId, GroupMatchState done) {
		StringBuffer hql =new StringBuffer();
		hql.append("select matchList from MatchList matchList where matchList.id=? and matchList.state=? ");
		return findUnique(hql.toString(), matchListId,done.ordinal());
	}



}
