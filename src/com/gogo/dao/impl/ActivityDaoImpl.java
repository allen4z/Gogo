package com.gogo.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.gogo.dao.ActivityDao;
import com.gogo.domain.Activity;

@Repository
public class ActivityDaoImpl extends BaseDao<Activity> implements ActivityDao{

	public Activity loadActbyActId(int actId){
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
