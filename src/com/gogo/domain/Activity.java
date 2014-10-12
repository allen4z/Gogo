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

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_act")
public class Activity {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="act_id",length=32,nullable=false)
	private String actId;
	
	//活动名称
	@NotNull(message="{act.actname.not.empty}")
	@Length(min=4,max=20,message="{act.actname.length.error}")
	@Column(name="act_name",length=20,nullable=false)
	private String actName;
	
	//活动内容
	@NotNull(message="{act.actcount.not.empty}")
	@Length(min=0,max=200,message="{act.actcount.length.error}")
	@Column(name="act_contents",length=200)
	private String actContent;
	
	//活动logo
	@Column(name="act_image_url",length=100)
	private String imageUrl;
	
	//创建时间
	@Column(name="act_create_time",length=10)
	private Date actCreateTime;
	
	//是否循环任务
	@Column(name="act_isloop",length=1)
	private boolean needLoop;
	
	//开始时间
	@NotNull(message="{act.actstarttime.not.empty}")
	@Column(name="act_start_time",length=10)
	private Date actStartTime;
	
	//结束时间
	@NotNull(message="{act.actendtime.not.empty}")
	@Column(name="act_end_time",length=10)
	private Date actEndTime;
	
	//报名时间
	@Column(name="act_sign_time",length=10)
	private Date actSignTime;
	
	//活动状态 ：未发布、未发布
	@Column(name="act_state",length=1)
	private int actState;
	
	//热点
	@Column(name="act_hotpoint",length=10)
	private int hotPoind;
	
	//活动创建人
	@ManyToOne
	@JoinColumn(name="act_own_user")
	@OrderBy("userName ASC")
	private User ownUser;
	
	//活动地点
	@ManyToOne
	@JoinColumn(name="place_id")
	private Place place;
	
	//版本
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;
	
	//活动拥有角色  (发起人、投资人、参与者、观众)
	@OneToMany(mappedBy="belongAct",cascade=CascadeType.ALL)
	private Set<Role> roles;
	
	//是否需要投资人
	@Column(name="act_need_invest",length=1)
	private boolean needInvest;
	
	//是否需要承办方
	@Column(name="act_need_undertake",length=1)
	private boolean needUndertake;
	
	//是否需要参与者
	@Column(name="act_need_actor",length=1)
	private boolean needActor;
	
	//是否需要观众
	@Column(name="act_isopen",length=1)
	private boolean needOpen;
	
	//活总需金额
	@Column(name="act_need_amount",length=50)
	private double needAmount;
	//已拥有资金
	@Column(name="act_have_amount",length=50)
	private double haveAmount;
	
	//活动承办方
	@ManyToOne
	@JoinColumn(name="uk_id")
	private Undertake undertake;
	
	//参与者最多
	@Column(name="act_max_join",length=50)
	private int maxJoin;
	
	//观众最多
	@Column(name="act_max_signup",length=50)
	private int maxSignUp;
	
	//每名参与者需要支付
	@Column(name="act_join_needpay",length=50)
	private double joinNeedPay;
	
	//每名观众需要支付
	@Column(name="act_signup_needpay",length=50)
	private double signUpNeedPay;
	
	public Activity(){
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public double getJoinNeedPay() {
		return joinNeedPay;
	}
	public void setJoinNeedPay(double joinNeedPay) {
		this.joinNeedPay = joinNeedPay;
	}
	public double getSignUpNeedPay() {
		return signUpNeedPay;
	}
	public void setSignUpNeedPay(double signUpNeedPay) {
		this.signUpNeedPay = signUpNeedPay;
	}
	public int getMaxJoin() {
		return maxJoin;
	}
	public void setMaxJoin(int maxJoin) {
		this.maxJoin = maxJoin;
	}
	public int getMaxSignUp() {
		return maxSignUp;
	}
	public void setMaxSignUp(int maxSignUp) {
		this.maxSignUp = maxSignUp;
	}

	public boolean isNeedLoop() {
		return needLoop;
	}

	public void setNeedLoop(boolean needLoop) {
		this.needLoop = needLoop;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
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
	
	public int getHotPoind() {
		return hotPoind;
	}


	public void setHotPoind(int hotPoind) {
		this.hotPoind = hotPoind;
	}


	public Place getPlace() {
		return place;
	}


	public void setPlace(Place place) {
		this.place = place;
	}


	public User getOwnUser() {
		return ownUser;
	}
	public void setOwnUser(User ownUser) {
		this.ownUser = ownUser;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isNeedInvest() {
		return needInvest;
	}

	public void setNeedInvest(boolean needInvest) {
		this.needInvest = needInvest;
	}

	public boolean isNeedUndertake() {
		return needUndertake;
	}

	public void setNeedUndertake(boolean needUndertake) {
		this.needUndertake = needUndertake;
	}

	public boolean isNeedActor() {
		return needActor;
	}

	public void setNeedActor(boolean needActor) {
		this.needActor = needActor;
	}



	public boolean isNeedOpen() {
		return needOpen;
	}

	public void setNeedOpen(boolean needOpen) {
		this.needOpen = needOpen;
	}

	public double getNeedAmount() {
		return needAmount;
	}

	public void setNeedAmount(double needAmount) {
		this.needAmount = needAmount;
	}

	public double getHaveAmount() {
		return haveAmount;
	}

	public void setHaveAmount(double haveAmount) {
		this.haveAmount = haveAmount;
	}

	public Undertake getUndertake() {
		return undertake;
	}

	public void setUndertake(Undertake undertake) {
		this.undertake = undertake;
	}
	
}
