package com.gogo.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.gogo.dao.ActivityDao;
import com.gogo.domain.Activity;

@Repository
public class ActivityDao extends BaseDao<Activity>{

	public Activity loadActbyActId(String actId){
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
	
}
