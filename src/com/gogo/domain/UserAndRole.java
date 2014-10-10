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
 * 角色和用户的关联关系
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user_role")
public class UserAndRole {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="user_role_id",length=32)
	private String userAndRoleId;
	
	//用户信息
	@ManyToOne
	@JoinColumn(name="user_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private User user;
	
	//角色信息
	@ManyToOne
	@JoinColumn(name="role_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Role role;
	
	//支出（票款）
	@Column(name="ur_cost",length=50)
	private double cost;
	
	//用户在当前活动的状态 
	@Column(name="ur_cost",length=1)
	private int uarState;
	
	//待支付（票款）
	@Column(name="ur_waitcost",length=50)
	private double waitCost;
	
	//报酬
	@Column(name="ur_remuneration",length=50)
	private double remuneration;
	//投资金额
	@Column(name="ur_invest_amount",length=50)
	private double investAmount;
	
	
	
	public int getUarState() {
		return uarState;
	}

	public void setUarState(int uarState) {
		this.uarState = uarState;
	}

	public double getWaitCost() {
		return waitCost;
	}

	public void setWaitCost(double waitCost) {
		this.waitCost = waitCost;
	}

	public double getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(double remuneration) {
		this.remuneration = remuneration;
	}

	public double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(double investAmount) {
		this.investAmount = investAmount;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getUserAndRoleId() {
		return userAndRoleId;
	}

	public void setUserAndRoleId(String userAndRoleId) {
		this.userAndRoleId = userAndRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
