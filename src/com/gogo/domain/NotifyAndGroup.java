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


@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_notify_group")
public class NotifyAndGroup {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="notify_group_id",length=32,nullable=false)
	private String id;
	
	@ManyToOne
	@JoinColumn(name="notify_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Notify notify;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Group group;
	@Column(name="notify_ispushed",length=1)
	private boolean isPushed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public boolean isPushed() {
		return isPushed;
	}

	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
	}
	
	
}
