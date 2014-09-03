package com.gogo.dao;

import java.io.Serializable;
import java.util.List;

import com.gogo.domain.Activity;
import com.gogo.domain.User;


public interface ActivityDao{

	public Activity loadActbyActId(int actId);

	public List<User> loadJoinUserByActId(String actId) ;

	public Serializable saveActivity(Activity act);

	public boolean delActivity(String actId);
	
}
