package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user")
public class User {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="user_id",length=32)
	private String userId;
	
	//用户名称
//	@Length(min=4,max=30,message="{user.username.legth.error}")
//	@NotNull(message="{user.username.not.empty}")
	@Pattern(regexp = "[A-Za-z0-9]{5,20}", message = "{user.username.legth.error}") //java validator验证（用户名字母数字组成，长度为5-10）
	@Column(name="user_name",length=20,nullable=false)
	private String userName;
	
	//用户密码
	@Pattern(regexp = "[A-Za-z0-9]{6,32}", message = "{user.password.legth.error}") 
	@Column(name="user_password",length=32,nullable=false)
	private String userPassword;
	
	//昵称
	@Pattern(regexp = "[A-Za-z0-9]{2,20}", message = "{user.alisname.legth.error}") 
	@Column(name="user_alis_name",length=20)
	private String alisName;
	
	//注册时间
	@Column(name="user_register_time",length=10,nullable=false)
	private Date userRegisterTime;
	
	//用户状态：删除、正常
	@Column(name="user_state",length=1,nullable=false)
	private int userState;
	
	//版本信息
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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

	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
