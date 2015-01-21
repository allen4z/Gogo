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

import com.gogo.domain.enums.InviteState;
import com.gogo.domain.enums.InviteType;


/**
 * 邀请信息
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_invite")
public class Invite extends BaseDomain{

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
	
	@Column(name="invite_entityid",length=32,nullable=false)
	private String entityId;
	
	//类型  活动/球队
	@Column(name="invite_type",length=1)
	private InviteType type;
	
	//邀请状态
	@Column(name="invite_state",length=1)
	private InviteState state;

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

	public InviteType getType() {
		return type;
	}

	public void setType(InviteType type) {
		this.type = type;
	}

	public InviteState getState() {
		return state;
	}

	public void setState(InviteState state) {
		this.state = state;
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

}
