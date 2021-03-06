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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import com.gogo.domain.enums.UserAndGroupState;

/**
 * 角色和用户的关联关系
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user_group")
public class UserAndGroup extends BaseDomain{
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="user_group_id",length=32)
	private String id;
	
	//用户信息
	@ManyToOne
	@JoinColumn(name="user_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User user;
	
	@JsonIgnore
	//小组信息
	@ManyToOne
	@JoinColumn(name="group_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Group group;
	
	//权限信息 
	@Column(name="authority_state",length=1)
	private int authorityState;
	
	//人员和小组关系状态 是否退出
	@Column(name="user_group_state",length=1)
	private UserAndGroupState state;
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

	public int getAuthorityState() {
		return authorityState;
	}

	public void setAuthorityState(int authorityState) {
		this.authorityState = authorityState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public UserAndGroupState getState() {
		return state;
	}

	public void setState(UserAndGroupState state) {
		this.state = state;
	}
}
