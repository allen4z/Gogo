package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

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
	
	/*@ManyToOne
	@JoinColumn(name="invite_group")
	@OrderBy("name ASC")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="invite_activity")
	@OrderBy("name ASC")
	private Activity activity;*/
	@Column(name="invite_entityid",length=32,nullable=false)
	private String entityId;
	
	
	@Column(name="invite_type",length=1)
	private int type;

	//版本
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;


	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

/*	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
		public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

		*
		*/
	
}
