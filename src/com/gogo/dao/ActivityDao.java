package com.gogo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Place;
import com.gogo.domain.User;
import com.gogo.map.GoMapHelper;
import com.gogo.page.Page;

@Repository
public class ActivityDao extends BaseDao<Activity>{
	
	
	private static final String HQL_AILS=" activity ";
	
	private static final String HQL_LIST="FROM Activity"; 
	
	private static final String HQL_COUNT="SELECT COUNT(*) FROM Activity"; 

	public Activity loadActbyActId(String actId){
		return load(actId);
	}
	
	public Activity getActbyActId(String actId){
		return get(actId);
	}

	public Serializable saveActivity(Activity act) {
		return save(act);
	}
	
	public void updateActivity(Activity act) {
		update(act);
	}

	public void deleteActivity(Activity act) {
		remove(act);
	}
	
	
	

	public List<Activity> loadOwnActivitesByUser(String userId,int curPage,int pagesize) {
		String hql = "select act from Activity act left join act.ownUser ou where ou.userId='"+userId+"'";
		List<Activity> list= findByPage(hql,curPage,pagesize);
		
		return list;
	}
	
	public int loadOwnActivitesByUserCount(String userId){
		String hql = "select count(act) from Activity act left join act.ownUser ou where ou.userId='"+userId+"'";
		return  this.<Number>getCount(hql, null).intValue();
	}
	
	
	public List<Activity> loadJoinActivitesByUser(String userId,int curPage,int pagesize) {
		String hql = "select act from UserAndRole uar left join uar.role r  left join r.belongAct act where uar.user.userId='"+userId+"'";
		List<Activity> list= findByPage(hql,curPage,pagesize);
		
		return list;
	}
	
	public int loadJoinActivitesByUserCount(String userId){
		String hql = "select count(act) from UserAndRole uar left join uar.role r  left join r.belongAct act where uar.user.userId='"+userId+"'";
		return  this.<Number>getCount(hql, null).intValue();
	}
	
	
	
	/**
	 * 根据地区信息查询活动信息
	 * @param place
	 * @return
	 */
	public List<Activity> loadActByPlace(User user,Place place,int pn,int pageSize) {
		String hql = getHql4Place(user,place,false);
		List<Activity> actList =findByPage(hql, pn, pageSize, null);
		return actList;
	}

	
	
	public int lodActByPlaceCount(User user,Place place){
		String hql = getHql4Place(user,place, true);
		return  this.<Number>getCount(hql, null).intValue();
	}
	
	
	private String getHql4Place(User user,Place place,boolean isCount) {
		float longitude = place.getLongitude();
		float latitude = place.getLatitude();
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" join "+HQL_AILS+".place p "
				+ " where p.longitude>="+(longitude-GoMapHelper.COORD_RANGE)
				+ " And p.longitude<="+(longitude+GoMapHelper.COORD_RANGE)
				+ " And p.latitude="+ (latitude-GoMapHelper.COORD_RANGE)
				+ " And p.latitude="+ (latitude-GoMapHelper.COORD_RANGE));
		
		if(user != null){
			hql.append("AND "+HQL_AILS+".ownUser.userId !='"+user.getUserId()+"'");
		}
		
		hql.append(" order by p.hotPoint");
		return hql.toString();
	}
	
	
	/**
	 * 根据当地热点地区信息查询活动信息
	 * @return
	 */
	public List<Activity> loadActbyAddr(User user,City city,int pn,int pageSize) {
		
		String hql=getHql4City(user, city, false);
		List<Activity> actPage = findByPage(hql, pn, pageSize, null);
		return actPage;
	}
	
	public int loadActbyAddrCount(User user,City city){
		String hql=getHql4City(user, city, true);
		return  this.<Number>getCount(hql, null).intValue();
	}
	
	private String getHql4City(User user,City city,boolean isCount){
		StringBuffer hql = new StringBuffer();
		
		if(isCount){
			hql.append("select count("+HQL_AILS+") ");
			
		}else{
			hql.append("select "+HQL_AILS+" ");
		}
		hql.append(" "+HQL_LIST+" "+HQL_AILS+" ");
		
		if(user != null){
			hql.append("where "+HQL_AILS+".ownUser.userId !='"+user.getUserId()+"'");
		}
		
		
		return hql.toString();
	}

	/**
	 * 根据活动热点情况，查询活动信息
	 * @param ip
	 * @return
	 */
	public Page<Activity> loadActByHotPoint() {
		String hql=" from Activity ";
		Page<Activity> actPage = null;
		return actPage;
	}


	
}
