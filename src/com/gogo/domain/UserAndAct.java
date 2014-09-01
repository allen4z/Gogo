package com.gogo.domain;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_user_act")
public class UserAndAct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_act_id")
	public int userAndActId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User user;
	
	@ManyToOne
	@JoinColumn(name="act_id")
	public Activity act;

	public int getUserAndActId() {
		return userAndActId;
	}

	public void setUserAndActId(int userAndActId) {
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

	
}
