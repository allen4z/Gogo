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

import com.gogo.domain.enums.GroupApplyState;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_groupapply")
public class GroupApplyInfo {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="groupapply_id",length=32,nullable=false)
	private String id;
	
	@ManyToOne
	@JoinColumn(name="groupapply_group")
	@OrderBy("name ASC")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="groupapply_user")
	@OrderBy("name ASC")
	private User user;
	
	@Column(name="groupapply_state",length=1)
	private GroupApplyState state;
	
	//版本
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GroupApplyState getState() {
		return state;
	}

	public void setState(GroupApplyState state) {
		this.state = state;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
