package com.gogo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.gogo.domain.enums.NotifyType;

/**
 * 通知实体类
 * @author Allen
 *
 */

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_notify")
public class Notify extends BaseDomain{
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="notify_id",length=32,nullable=false)
	private String id;
	
	@Column(name="notify_title",length=20)
	private String title;
	
	@Column(name="notify_content",length=100)
	private String content;
	
	@Column(name="notify_type",length=10)
	private NotifyType type;
	
	@ManyToOne
	@JoinColumn(name="notify_own_user")
	@OrderBy("name ASC")
	private User createUser;

	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NotifyType getType() {
		return type;
	}

	public void setType(NotifyType type) {
		this.type = type;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
