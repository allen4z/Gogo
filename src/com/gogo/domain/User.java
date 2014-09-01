package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="user_name",length=20)
	private String userName;
	
	@Column(name="user_password",length=20)
	private String password;
	
	@Column(name="user_alis_name",length=20)
	private String alisName;
	
	@Column(name="user_register_time",length=10)
	private Date userRegisterTime;
	
	//用户状态：删除
	@Column(name="user_state",length=1)
	private int userState;

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAlisName() {
		return alisName;
	}
	public void setAlisName(String alisName) {
		this.alisName = alisName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getUserRegisterTime() {
		return userRegisterTime;
	}
	public void setUserRegisterTime(Date userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	
}
