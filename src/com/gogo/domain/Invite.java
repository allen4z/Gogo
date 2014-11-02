package com.gogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;



@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_invite")
public class Invite {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="invite_id",length=32,nullable=false)
	private String id;
	
	@ManyToOne
	@JoinColumn(name="invite_user")
	@OrderBy("name ASC")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="invite_beinvited")
	@OrderBy("name ASC")
	private User beInvited;
	
	@ManyToOne
	@JoinColumn(name="invite_group")
	@OrderBy("name ASC")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="invite_activity")
	@OrderBy("name ASC")
	private Activity activity;
	
	
	@Column(name="invite_type",length=1)
	private int type;



	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getBeInvited() {
		return beInvited;
	}

	public void setBeInvited(User beInvited) {
		this.beInvited = beInvited;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}	
	
}
