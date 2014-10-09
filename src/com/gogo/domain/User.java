package com.gogo.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

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
	@Length(min=4,max=20,message="{user.username.length.error}")
	@NotNull(message="{user.username.not.empty}")
	@Pattern(regexp = "[A-Za-z0-9]*", message = "{user.username.regexp.error}")
	@Column(name="user_name",length=20,nullable=false)
	private String userName;
	
	//用户密码
	@Length(min=6,max=32,message="{user.password,length.error}")
	@NotNull(message="{user.password.not,empty}")
	@Column(name="user_password",length=32,nullable=false)
	private String userPassword;
	
	//昵称
	@NotNull(message="{user.alisname.not.empty}")
	@Length(min=4,max=20,message="{user.alisname.length.error}")
	@Pattern(regexp = "[A-Za-z0-9]*", message = "{user.alisname.regexp.error}") 
	@Column(name="user_alis_name",length=20,nullable=false)
	private String alisName;
	
	@Pattern(regexp ="[0-9]{9,11}", message = "{user.phonenum.regexp.error}") 
	@Column(name="user_phonenum",length=20,nullable=true)
	private String phoneNum;
	
	@Pattern(regexp="^([a-zA-Z0-9-._]*)+@+([a-zA-Z0-9]*)+.+([a-zA-Z0-9]{1,4})$",message="{user.email.regexp.error}")
	@Column(name="user_email",length=30,nullable=true)
	private String email;
	
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
	
	//是否是演员 -- 雇佣相关（暂时没有用）
	@Column(name="user_isactor",length=1)
	private boolean isActor;
	
	//角色所有人员
	/*@OneToMany(mappedBy="belongUser",cascade=CascadeType.ALL)
	private Set<FriendGroup> firneds;*/

	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/*public Set<FriendGroup> getFirneds() {
		return firneds;
	}
	public void setFirneds(Set<FriendGroup> firneds) {
		this.firneds = firneds;
	}*/
	public boolean isActor() {
		return isActor;
	}
	public void setActor(boolean isActor) {
		this.isActor = isActor;
	}
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
