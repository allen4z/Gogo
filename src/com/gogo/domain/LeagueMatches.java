package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gogo.domain.enums.LeagueMatchesType;

/**
 * 联赛
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_leaguematches")
public class LeagueMatches extends BaseDomain  {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id",length=32,nullable=false)
	private String id;
	
	//联赛类型  
	@Column(name="lm_type",length=1)
	private LeagueMatchesType type;
	
	//需要球队数量
	@Column(name="lm_need",length=10)
	private int needGroupCount;
	
	//当前球队数量
	@Column(name="lm_curent",length=10)
	private int currentGroupCount;
	
	//当前轮数
	@Column(name="lm_round",length=10)
	private int round;
	
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

	public LeagueMatchesType getType() {
		return type;
	}

	public void setType(LeagueMatchesType type) {
		this.type = type;
	}

	public int getNeedGroupCount() {
		return needGroupCount;
	}

	public void setNeedGroupCount(int needGroupCount) {
		this.needGroupCount = needGroupCount;
	}

	public int getCurrentGroupCount() {
		return currentGroupCount;
	}

	public void setCurrentGroupCount(int currentGroupCount) {
		this.currentGroupCount = currentGroupCount;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	
}
