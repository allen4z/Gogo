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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * 活动标签
 * @author allen
 *
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_label")
public class Label {
	
	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="act_label_id",length=32,nullable=false)
	private String id;
	
	//活动名称
	@NotNull(message="{act.labelname.not.empty}")
	@Length(min=4,max=20,message="{act.labelname.length.error}")
	@Column(name="act_label_name",length=20,nullable=false)
	private String name;
	
	//标签所属活动
	@ManyToOne
	@JoinColumn(name="belongact")
	@OrderBy("actSignTime ASC")
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private Activity act;
	
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Activity getAct() {
		return act;
	}

	public void setAct(Activity act) {
		this.act = act;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
}
