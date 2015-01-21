package com.gogo.domain;

import java.util.Date;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.gogo.domain.enums.GroupType;


@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_group")
public class Group extends BaseDomain{
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="group_id",length=32,nullable=false)
	private String id;
	
	//小组名称
	@NotNull(message="{group.name.not.empty}")
	@Length(min=4,max=20,message="{group.name.length.error}")
	@Column(name="group_name",length=20,nullable=false)
	private String name;
	
	//简称
	@Column(name="group_nickName",length=20,nullable=false)
	private String nickName;
	
	//小组LOGO
	@Column(name="group_logo",length=100)
	private String logoUrl;
	
	//简介
	@Column(name="group_content",length=200)
	private String content;
	
	//球队性质
	@Column(name = "group_type", length = 1)
	private GroupType type;
	
	//创建时间
	@Column(name = "group_create_time", length = 10)
	private Date createTime;

	//是否调整期（暂停）
	@Column(name="group_adjustment",length=1)
	private boolean isAdjustment;
	
	//小组主场
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="place_id")
	private Place place;
	
	//活动拥有角色  (管理员、成员)
	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
	private Set<UserAndGroup> joinUser;

	//最多加入人数
	@Column(name="group_max_user",length=20,nullable=false)
	private int maxJoinUser;
	//当前球队人数
	@Column(name="group_current_user",length=20,nullable=false)
	private int curJoinUser;
	
	//创建人
	@ManyToOne
	@JoinColumn(name="group_create_user")
	@OrderBy("userName ASC")
	private User createUser;
	
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
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public boolean isAdjustment() {
		return isAdjustment;
	}

	public void setAdjustment(boolean isAdjustment) {
		this.isAdjustment = isAdjustment;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}
}
