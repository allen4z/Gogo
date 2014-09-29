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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 承办实体
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_undertake")
public class Undertake {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="ut_id",length=32)
	//主键
	private String ukId;
	
	//活动名称
	@Column(name="ut_name",length=20)
	//实体名称
	private String ukName;
	
	//创建人
	@ManyToOne
	@JoinColumn(name="ut_create_user")
	@OrderBy("userName ASC")
	private User createUser;
		
	//承办实体创建时间
	@Column(name="ut_create_time",length=10)
	private Date createTime;
	
	//承办的活动     多对一
	/*@OneToMany(mappedBy="undertake",cascade=CascadeType.ALL)
	private Set<Activity> undertakeActs;*/

	

	public String getUkId() {
		return ukId;
	}

	public void setUkId(String ukId) {
		this.ukId = ukId;
	}

	public String getUkName() {
		return ukName;
	}

	public void setUkName(String ukName) {
		this.ukName = ukName;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

/*	public Set<Activity> getUndertakeActs() {
		return undertakeActs;
	}

	public void setUndertakeActs(Set<Activity> undertakeActs) {
		this.undertakeActs = undertakeActs;
	}*/
	
	
	
	
}
