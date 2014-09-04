package com.gogo.service;

import com.gogo.domain.Activity;
import com.gogo.domain.User;


	
public interface ActivityService {
	
	public Activity loadActbyActId(int actId);
	
	
	public int saveActivity(Activity act,User user);

	public void updateActivity(Activity act,int userId) throws Exception;
	
	public void deleteActivity(int actId,int userId);
}
