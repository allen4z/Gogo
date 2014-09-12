package com.gogo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.City;
import com.gogo.domain.Place;
import com.gogo.map.GoMapHelper;
import com.gogo.page.Page;

@Repository
public class ActivityDao extends BaseDao<Activity>{

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

	/**
	 * 根据地区信息查询活动信息
	 * @param place
	 * @return
	 */
	public Page<Activity> loadActByPlace(Place place) {
		float longitude = place.getLongitude();
		float latitude = place.getLatitude();
		
		String hql = " select a from Activity a join a.place p "
				+ " where p.longitude>="+(longitude-GoMapHelper.COORD_RANGE)
				+ " And p.longitude<="+(longitude+GoMapHelper.COORD_RANGE)
				+ " And p.latitude="+ (latitude-GoMapHelper.COORD_RANGE)
				+ " And p.latitude="+ (latitude-GoMapHelper.COORD_RANGE)
				+ " order by p.hotPoint";
		
		Page<Activity> actList = find(hql,1);
		
		return actList;
	}
	
	/**
	 * 根据当地热点地区信息查询活动信息
	 * @return
	 */
	public Page<Activity> loadActbyAddr(City city) {
		
		String hql=" from Activity ";
		Page<Activity> actPage = find(hql,1);
		return actPage;
	}

	/**
	 * 根据活动热点情况，查询活动信息
	 * @param ip
	 * @return
	 */
	public Page<Activity> loadActByHotPoint() {
		String hql=" from Activity ";
		Page<Activity> actPage = find(hql,1);
		return actPage;
	}


	
}
