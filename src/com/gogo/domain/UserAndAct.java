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


@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user_activity")
public class UserAndAct {

	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="user_act_id",length=32)
	private String userAndActId;
	
	//用户信息
	@ManyToOne
	@JoinColumn(name="user_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User user;
	
	@JsonIgnore
	//活动信息
	@ManyToOne
	@JoinColumn(name="act_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Activity act;
	
	//待支付（票款）
	@Column(name="ur_waitcost",length=50)
	private double waitCost;

	//用户在当前活动的状态 
	@Column(name="ua_state",length=1)
	private int uaaState;
	
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;
	
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getUaaState() {
		return uaaState;
	}

	public void setUaaState(int uaaState) {
		this.uaaState = uaaState;
	}

	public String getUserAndActId() {
		return userAndActId;
	}

	public void setUserAndActId(String userAndActId) {
		this.userAndActId = userAndActId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getAct() {
		return act;
	}

	public void setAct(Activity act) {
		this.act = act;
	}
	
	public double getWaitCost() {
		return waitCost;
	}

	public void setWaitCost(double waitCost) {
		this.waitCost = waitCost;
	}

	
}
