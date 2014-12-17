package com.gogo.domain;

import java.util.Date;

import javax.persistence.CascadeType;
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

import com.gogo.domain.enums.GroupMatchState;
import com.gogo.domain.enums.MatchType;

//对战信息 
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_matchlist")
public class MatchList extends BaseDomain {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="ml_id",length=32,nullable=false)
	private String id;
	
	//发起小组
	@ManyToOne
	@JoinColumn(name="belongGroup")
	private Group belongGroup;
	
	//对方小组
	@ManyToOne
	@JoinColumn(name="otherGroup")
	private Group otherGroup;
	
	//主队进球数
	@Column(name="ml_belonggoals",length=10)
	private int belongGroupGoals;
	
	//客队进球数
	@Column(name="ml_othergoals",length=10)
	private int otherGroupGoals;
	
	//比赛时间
	@Column(name="ml_date",length=10)
	private Date matchDate;
	
	//比赛地点
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ml_place_id")
	private Place matchPlace;
	
	//比赛类型 联赛 杯赛 友谊赛
	@Column(name="ml_type",length=1)
	private MatchType type;
	
	//如果不是友谊赛（联赛、杯赛），需要关联分组
	@ManyToOne
	@JoinColumn(name="ml_lmgroup")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private LeagueMatchesGroup leagueGroup;
	
	//是否同意（应战）
	@Column(name="ml_isagree",length=1)
	private GroupMatchState isAgree;
	
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

	public Group getBelongGroup() {
		return belongGroup;
	}

	public void setBelongGroup(Group belongGroup) {
		this.belongGroup = belongGroup;
	}

	public Group getOtherGroup() {
		return otherGroup;
	}

	public void setOtherGroup(Group otherGroup) {
		this.otherGroup = otherGroup;
	}

	public GroupMatchState getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(GroupMatchState isAgree) {
		this.isAgree = isAgree;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public Place getMatchPlace() {
		return matchPlace;
	}

	public void setMatchPlace(Place matchPlace) {
		this.matchPlace = matchPlace;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public int getBelongGroupGoals() {
		return belongGroupGoals;
	}

	public void setBelongGroupGoals(int belongGroupGoals) {
		this.belongGroupGoals = belongGroupGoals;
	}

	public int getOtherGroupGoals() {
		return otherGroupGoals;
	}

	public void setOtherGroupGoals(int otherGroupGoals) {
		this.otherGroupGoals = otherGroupGoals;
	}

	public MatchType getType() {
		return type;
	}

	public void setType(MatchType type) {
		this.type = type;
	}

	public LeagueMatchesGroup getLeagueGroup() {
		return leagueGroup;
	}

	public void setLeagueGroup(LeagueMatchesGroup leagueGroup) {
		this.leagueGroup = leagueGroup;
	}
	
}
