package com.gogo.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_act")
public class Activity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="act_id")
	private int actId;
	
	@Column(name="act_name",length=20)
	private String actName;
	
	@Column(name="act_contents",length=20)
	private String actContent;
	
	//创建时间
	@Column(name="act_create_time",length=10)
	private Date actCreateTime;
	
	//开始时间
	@Column(name="act_start_time",length=10)
	private Date actStartTime;
	
	//结束时间
	@Column(name="act_end_time",length=10)
	private Date actEndTime;
	
	//报名时间
	@Column(name="act_sign_time",length=10)
	private Date actSignTime;
	
	//...
	//活动状态 ：未发布、未发布
	@Column(name="act_state",length=1)
	private int actState;
	
	
	@ManyToOne
	@JoinColumn(name="act_own_user")
	@OrderBy("userName ASC")
	private User ownUser;
	
	@OneToMany(mappedBy="act",cascade=CascadeType.ALL)
	private Set<UserAndAct> joinUser;
	
	
	public Activity(){
		
	}
	
	public int getActId() {
		return actId;
	}
	public void setActId(int actId) {
		this.actId = actId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getActContent() {
		return actContent;
	}
	public void setActContent(String actContent) {
		this.actContent = actContent;
	}
	public Date getActCreateTime() {
		return actCreateTime;
	}
	public void setActCreateTime(Date actCreateTime) {
		this.actCreateTime = actCreateTime;
	}
	public Date getActStartTime() {
		return actStartTime;
	}
	public void setActStartTime(Date actStartTime) {
		this.actStartTime = actStartTime;
	}
	public Date getActEndTime() {
		return actEndTime;
	}
	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}
	public Date getActSignTime() {
		return actSignTime;
	}
	public void setActSignTime(Date actSignTime) {
		this.actSignTime = actSignTime;
	}
	public int getActState() {
		return actState;
	}
	public void setActState(int actState) {
		this.actState = actState;
	}
	
	public User getOwnUser() {
		return ownUser;
	}
	public void setOwnUser(User ownUser) {
		this.ownUser = ownUser;
	}
	public Set<UserAndAct> getJoinUser() {
		return joinUser;
	}
	public void setJoinUser(Set<UserAndAct> joinUser) {
		this.joinUser = joinUser;
	}
}
