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
 * 球队与联赛分组/轮数分组 的关联关系
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_group_lmgroup")
public class GroupAndLMGroup {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="id",length=32)
	private String id;
	
	//积分
	@Column(name="galmg_score",length=10)
	private int score;
	
	//球队
	@ManyToOne
	@JoinColumn(name="galmg_group")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Group group;
	
	//分组
	@ManyToOne
	@JoinColumn(name="galmg_lmgroup")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private LeagueMatchesGroup lmGroup;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public LeagueMatchesGroup getLmGroup() {
		return lmGroup;
	}

	public void setLmGroup(LeagueMatchesGroup lmGroup) {
		this.lmGroup = lmGroup;
	}
}
