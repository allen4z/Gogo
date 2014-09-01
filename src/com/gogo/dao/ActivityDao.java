package com.gogo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Activity;
import com.gogo.domain.User;

@Repository
public class ActivityDao extends BaseDao<Activity>{

	public Activity loadActbyActId(int actId){
		return get(actId);
	}

	public List<User> loadJoinUserByActId(String actId) {
		return null;
	}

	public Serializable saveActivity(Activity act) {
		return save(act);
	}

	public boolean delActivity(String actId) {
		return false;
	}
	
}
