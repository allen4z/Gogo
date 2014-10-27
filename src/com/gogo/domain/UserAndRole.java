package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

/**
 * 角色和用户的关联关系
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user_role")
public class UserAndRole {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="user_role_id",length=32)
	private String userAndRoleId;
	
	//用户信息
	@ManyToOne
	@JoinColumn(name="user_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User user;
	
	//角色信息
	@ManyToOne
	@JoinColumn(name="role_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Role role;
	
	//用户在当前活动的状态 
	@Column(name="ur_state",length=1)
	private int uarState;
	
	//版本
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;
	
	public int getUarState() {
		return uarState;
	}

	public void setUarState(int uarState) {
		this.uarState = uarState;
	}


	public String getUserAndRoleId() {
		return userAndRoleId;
	}

	public void setUserAndRoleId(String userAndRoleId) {
		this.userAndRoleId = userAndRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
