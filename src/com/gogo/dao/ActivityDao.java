package com.gogo.dao;

import java.io.Serializable;

import com.gogo.domain.Activity;


public interface ActivityDao{

	public Activity loadActbyActId(int actId);

	public Serializable saveActivity(Activity act);

	public void updateActivity(Activity act);
	
	public void deleteActivity(Activity act);
	
	
}
