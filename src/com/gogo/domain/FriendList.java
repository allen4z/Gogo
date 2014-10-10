package com.gogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_friendlist")
public class FriendList {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="fg_id",length=32,nullable=false)
	private String id;
	
	//所属用户
	@ManyToOne
	@JoinColumn(name="belongUser")
	private User belongUser;

	//好友用户
	@ManyToOne
	@JoinColumn(name="friendUser")
	private User friendUser;
	
	//好友昵称
	@NotNull(message="friend.ailsname.null.error")
	@Length(min=4,max=20,message="{friend.alisname.length.error}")
	@Pattern(regexp = "[A-Za-z0-9]*", message = "{friend.alisname.regexp.error}") 
	@Column(name="f_ailsname",length=20)
	private String fAlisName;
	
	//是否通过认证
	@Column(name="fg_passed",length=1)
	private boolean passed;

	
	
	
	public String getfAlisName() {
		return fAlisName;
	}

	public void setfAlisName(String fAlisName) {
		this.fAlisName = fAlisName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getBelongUser() {
		return belongUser;
	}

	public void setBelongUser(User belongUser) {
		this.belongUser = belongUser;
	}

	public User getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
}