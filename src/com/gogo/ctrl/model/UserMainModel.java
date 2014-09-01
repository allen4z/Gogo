package com.gogo.ctrl.model;

import java.util.List;

import com.gogo.domain.Activity;
import com.gogo.domain.User;

public class UserMainModel{
	private User user;

	private List<Activity> ownActivity;
	
	private List<Activity> joinActivity;

	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Activity> getOwnActivity() {
		return ownActivity;
	}

	public void setOwnActivity(List<Activity> ownActivity) {
		this.ownActivity = ownActivity;
	}

	public List<Activity> getJoinActivity() {
		return joinActivity;
	}

	public void setJoinActivity(List<Activity> joinActivity) {
		this.joinActivity = joinActivity;
	}

	
	
	
}
