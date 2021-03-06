package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Group;
import com.gogo.domain.enums.UserAndGroupState;

@Repository
public class GroupDao extends BaseDao<Group> {
	
	private static final String HQL_AILS=" group";
	
	private static final String HQL_LIST="FROM Group"; 
	
	//获得所有小组
	private String getAllGroupHql(boolean isCount){
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count(*) ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
		
		return hql.toString();
	}
	
	public int loadAllGroupCount(){
		String hql=getAllGroupHql(true);
		return getCount(hql, null);
	}
	
	public List<Group> loadAllGroup(int pn,int pageSize) {
		String hql=getAllGroupHql(false);
		List<Group> groupPage = findByPage(hql, pn, pageSize, null);
		return groupPage;
	}
	
	public String getGroup4UserHql(boolean isCount){
		String hql = "select ";
		if(isCount){
			hql+=" count(uag.group) ";
		}else{
			hql+= " uag.group";
		}
		hql +=" from UserAndGroup uag left join uag.user user where user.id=? and uag.state="+UserAndGroupState.FORMAL.ordinal();
		
		return hql;
	}
	
	public int loadGroup4UserCount(String userId) {
		String hql = getGroup4UserHql(true);
		return getCount(hql, userId);
	}

	public List<Group> loadGroup4User(String userId, int pn, int pageSize) {
		String hql = getGroup4UserHql(false);
		List<Group> groupPage = findByPage(hql, pn, pageSize, userId);
		return groupPage;
	}
	
	
	public List<Group> loadGroup4User(String userId){
		String hql = "select uag.group from UserAndGroup uag left join uag.user user where user.id=? and uag.state="+UserAndGroupState.FORMAL.ordinal();
		List<Group> groups = find(hql,userId);
		return groups;
	}
	
	

}
