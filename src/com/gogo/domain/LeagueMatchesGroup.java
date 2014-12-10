package com.gogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;


/**
 * 杯赛分组/联赛轮数
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_leaguematchesgroup")
public class LeagueMatchesGroup extends BaseDomain {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id",length=32,nullable=false)
	private String id;
	
	//小组排名、所在轮数  
	@Column(name="lmg_no")
	private int no;

	//所属联赛
	@ManyToOne
	@JoinColumn(name="lmg_lm")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private LeagueMatches leagueMatches;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public LeagueMatches getLeagueMatches() {
		return leagueMatches;
	}

	public void setLeagueMatches(LeagueMatches leagueMatches) {
		this.leagueMatches = leagueMatches;
	}
}
