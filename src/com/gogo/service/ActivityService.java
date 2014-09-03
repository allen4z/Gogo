package com.gogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gogo.domain.Activity;
import com.gogo.domain.User;


	
public interface ActivityService {
	
	public Activity loadActbyActId(int actId);
	

	public List<User> loadJoinUserByActId(String actId);
	
	public void saveActivity(Activity act);
	
	public int saveActivity(Activity act, int userId);

	public boolean delActivity(String actId);


}
