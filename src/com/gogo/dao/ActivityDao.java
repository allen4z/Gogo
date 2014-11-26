package com.gogo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.enums.ACTState;
import com.gogo.domain.enums.UserAndActState;

@Repository
public class ActivityDao extends BaseDao<Activity>{
	
	private static final String HQL_AILS=" activity";
	
	private static final String HQL_LIST="FROM Activity"; 

	public List<Activity> loadOwnActivitesByUser(String userId,int curPage,int pagesize) {
		String hql = "select act from Activity act left join act.ownUser ou where ou.id='"+userId+"'";
		List<Activity> list= findByPage(hql,curPage,pagesize);
		
		return list;
	}
	
	public int loadOwnActivitesByUserCount(String userId){
		String hql = "select count(act) from Activity act left join act.ownUser ou where ou.id='"+userId+"'";
		return  getCount(hql, null);
	}
	
	
	public List<Activity> loadJoinActivitesByUser(String userId,int curPage,int pagesize) {
		String hql = "select act from UserAndAct uaa left join uaa.act act "
				+ " where uaa.user.id='"+userId+"'"
						+ " and uaa.uaaState in("+UserAndActState.JOIN.ordinal()+","+UserAndActState.QUEUE.ordinal()+") ";
		List<Activity> list= findByPage(hql,curPage,pagesize);
		
		return list;
	}
	
	public int loadJoinActivitesByUserCount(String userId){
		String hql = "select count(act) from UserAndAct uaa left join uaa.act act "
				+ " where uaa.user.id=?"
				+ " and uaa.uaaState in("+UserAndActState.JOIN.ordinal()+","+UserAndActState.QUEUE.ordinal()+") ";
		return   getCount(hql, userId);
	}
	
	/**
	 * 根据地区信息查询活动信息
	 * @param place
	 * @return
	 */
	public List<Activity> loadActByPlace(String[] mapIds,int pn,int pageSize) {
		String hql = getHql4Place(mapIds,false);
		List<Activity> actList =findByPage(hql, pn, pageSize, mapIds);
		return actList;
	}

	
	
	public int lodActByPlaceCount(String[] mapIds){
		String hql = getHql4Place(mapIds, true);
		return  getCount(hql, mapIds);
	}
	
	
	private String getHql4Place(String[] mapIds,boolean isCount) {
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
		hql.append(" "+HQL_AILS+".bdplaceId in (:bdplaceId)");
		
		hql.append(" order by p.hotPoint");
		return hql.toString();
	}
	
	
	/**
	 * 根据当地热点地区信息查询活动信息
	 * @return
	 */
	public List<Activity> loadActbyAddr(String city,int pn,int pageSize) {
		String hql =getHql4City(false);		
		List<Activity> actList =findByPage(hql, pn, pageSize, null);
		return actList;
	}
	
	public int loadActbyAddrCount(String city) {
		String hql =getHql4City( true);
		return  getCount(hql, null);
	}
	

	public List<Activity> loadJoinActivitesByUser(int userId) {
		
		String hql = "from Activity act left join act.joinUser ju left join ju.user u where u.id='"+userId+"'";
		List<Activity> actList = find(hql);
		return actList;
	}
	/**
	 * 根据活动热点情况，查询活动信息
	 * @param ip
	 * @return
	 */
	public List<Activity> loadActByHotPoint(int pn,int pageSize) {
		
		String hql=getHql4City(false);
		List<Activity> actPage = findByPage(hql, pn, pageSize, null);
		return actPage;
	}
	
	public int loadActByHotPointCount(){
		String hql=getHql4City( true);
		return  getCount(hql, null);
	}
	
	private String getHql4City(boolean isCount){
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
		
		//非公开项目不能被查询到
		hql.append(" where "+HQL_AILS+".open=true ");
		//活动状态为发布则可以被查询
		hql.append(" and "+HQL_AILS+".state="+ACTState.RELEASE.ordinal());
		
		
		return hql.toString();
	}
	
}
