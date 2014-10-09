package com.gogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

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
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User belongUser;

	//好友用户
	@ManyToOne
	@JoinColumn(name="friendUser")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User friendUser;
	
	//是否通过认证
	@Column(name="fg_passed",length=1)
	private boolean passed;

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
