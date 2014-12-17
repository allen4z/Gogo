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
import org.springframework.format.annotation.DateTimeFormat;

import com.gogo.domain.enums.ACTState;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "t_act")
public class Activity extends BaseDomain {

	// 主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "act_id", length = 32, nullable = false)
	private String id;

	// 活动名称
	@NotNull(message = "{act.actname.not.empty}")
	@Length(min = 4, max = 20, message = "{act.actname.length.error}")
	@Column(name = "act_name", length = 20, nullable = false)
	private String name;

	// //活动内容
	// @NotNull(message="{act.actcount.not.empty}")
	// @Length(min=0,max=200,message="{act.actcount.length.error}")
	// @Column(name="act_contents",length=200)
	// private String content;

	@OneToMany(mappedBy = "act", cascade = CascadeType.ALL)
	private Set<Label> label;

	// 活动logo
	@Column(name = "act_image_url", length = 100)
	private String imageUrl;

	// 活动创建人
	@ManyToOne
	@JoinColumn(name = "act_own_user")
	@OrderBy("name ASC")
	private User ownUser;

	@OneToMany(mappedBy = "act", cascade = CascadeType.ALL)
	private Set<UserAndAct> joinUser;

	// 创建时间
	@Column(name = "act_create_time", length = 10)
	private Date actCreateTime;

	// 开始时间
	@NotNull(message = "{act.actstarttime.not.empty}")
	@Column(name = "act_start_time", length = 10)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	// 活动状态 ：未发布、未发布
	@Column(name = "act_state", length = 1)
	private ACTState state;

	// 关联的地点
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "place_id")
	private Place place;

	// 内部训练、野球
	@Column(name = "act_isopen", length = 1)
	private boolean open;

	// 活总需金额 根据缴费金额自动计算
	@Column(name = "act_need_amount", length = 50)
	private double amount;

	// 已拥有资金
	@Column(name = "act_has_amount", length = 50)
	private double hasAmount;

	// 每名参与者需要支付
	@Column(name = "act_join_needpay", length = 50)
	private double joinNeedPay;

	// -------------------JOIN INFO -----------------------
	// 参与者最少
	@Column(name = "act_min_join", length = 50)
	private int minJoin;

	// 参与者最多
	@Column(name = "act_max_join", length = 50)
	private int maxJoin;
	@Column(name = "act_current_join", length = 50)
	private int cutJoin;

	// 留言数量
	@Column(name = "act_current_message", length = 50)
	private int cutMessage;

	// 球队
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id")
	private Group gorup;

	// 比赛
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "match_id")
	private MatchList matchList;

	// 版本
	@Version
	@Column(name = "update_time", length = 10, nullable = false)
	private Date update_time;

	public int getCutMessage() {
		return cutMessage;
	}

	public void setCutMessage(int cutMessage) {
		this.cutMessage = cutMessage;
	}

	public int getMinJoin() {
		return minJoin;
	}

	public void setMinJoin(int minJoin) {
		this.minJoin = minJoin;
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

	public int getMaxJoin() {
		return maxJoin;
	}

	public void setMaxJoin(int maxJoin) {
		this.maxJoin = maxJoin;
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

	// public String getContent() {
	// return content;
	// }
	//
	// public void setContent(String content) {
	// this.content = content;
	// }

	public Set<Label> getLabel() {
		return label;
	}

	public void setLabel(Set<Label> label) {
		this.label = label;
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

	public Date getActCreateTime() {
		return actCreateTime;
	}

	public void setActCreateTime(Date actCreateTime) {
		this.actCreateTime = actCreateTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public ACTState getState() {
		return state;
	}

	public void setState(ACTState state) {
		this.state = state;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getHasAmount() {
		return hasAmount;
	}

	public void setHasAmount(double hasAmount) {
		this.hasAmount = hasAmount;
	}

	public int getCutJoin() {
		return cutJoin;
	}

	public void setCutJoin(int cutJoin) {
		this.cutJoin = cutJoin;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Group getGorup() {
		return gorup;
	}

	public void setGorup(Group gorup) {
		this.gorup = gorup;
	}

	public MatchList getMatchList() {
		return matchList;
	}

	public void setMatchList(MatchList matchList) {
		this.matchList = matchList;
	}

	@Override
	public String getCategory() {
		return this.getClass().getName();
	}
}
