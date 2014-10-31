package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String id;

	//用户名称
	
	@Length(min=4,max=20,message="{user.username.length.error}")
	@NotNull(message="{user.username.not.empty}")
	@Pattern(regexp = "[A-Za-z0-9]*", message = "{user.username.regexp.error}")
	@Column(name="user_name",length=20,nullable=false)
	private String name;
	
	//用户密码
	@Length(min=6,max=32,message="{user.password,length.error}")
	@NotNull(message="{user.password.not,empty}")
	@Column(name="user_password",length=32,nullable=false)
	private String password;
	
	//昵称
	@NotNull(message="{user.aliasname.not.empty}")
	@Length(min=4,max=20,message="{user.aliasname.length.error}")
	@Pattern(regexp = "[A-Za-z0-9]*", message = "{user.aliasname.regexp.error}") 
	@Column(name="user_alias_name",length=20,nullable=false)
	private String aliasName;
	
	//头像
	@Column(name="user_image_url",length=100)
	private String imageUrl;
	
	//个性签名
	@Column(name="user_signature",length=100)
	@Length(min=0,max=100,message="{user.signature.length.error}")
	private String signature;
	
	
	@Pattern(regexp ="[0-9]{9,11}", message = "{user.phonenum.regexp.error}") 
	@Column(name="user_phonenum",length=20,nullable=true)
	private String phoneNum;
	
	@Pattern(regexp="^([a-zA-Z0-9-._]*)+@+([a-zA-Z0-9]*)+.+([a-zA-Z0-9]{1,4})$",message="{user.email.regexp.error}")
	@Column(name="user_email",length=30,nullable=true)
	private String email;
	
	//注册时间
	@Column(name="user_register_time",length=10,nullable=false)
	private Date registerTime;
	
	//用户状态：删除、正常
	@Column(name="user_state",length=1,nullable=false)
	private int state;	
	
	//版本信息
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;
		
	public String getPhoneNum() {
		return phoneNum;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

}
