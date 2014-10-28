package com.gogo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;


@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_group")
public class Group {
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="goup_id",length=32,nullable=false)
	private String id;
	
	//活动名称
	@NotNull(message="{group.name.not.empty}")
	@Length(min=4,max=20,message="{group.name.length.error}")
	@Column(name="group_name",length=20,nullable=false)
	private String name;
//	
//	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
//	private Set<Activity> acts;
	
	//活动拥有角色  (管理员、成员)
	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
	private Set<UserAndGroup> joinUser;

	@Column(name="group_max_user",length=20,nullable=false)
	private int maxJoinUser;
	
	@Column(name="group_current_user",length=20,nullable=false)
	private int curJoinUser;
	
	@ManyToOne
	@JoinColumn(name="group_create_user")
	@OrderBy("userName ASC")
	private User createUser;
	
	
	
	public Set<UserAndGroup> getJoinUser() {
		return joinUser;
	}

	public void setJoinUser(Set<UserAndGroup> joinUser) {
		this.joinUser = joinUser;
	}

	public int getCurJoinUser() {
		return curJoinUser;
	}

	public void setCurJoinUser(int curJoinUser) {
		this.curJoinUser = curJoinUser;
	}

	public int getMaxJoinUser() {
		return maxJoinUser;
	}

	public void setMaxJoinUser(int maxJoinUser) {
		this.maxJoinUser = maxJoinUser;
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


	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
}