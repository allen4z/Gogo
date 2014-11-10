package com.gogo.domain;

import java.util.Date;

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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_place")
public class Place extends BaseDomain{

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="place_id",length=32)
	private String id;
	
	//地点编码
	@Column(name="place_code",length=10)
	private String code;
	
	
	@Column(name="place_name",length=50)
	private String name;
	
	//经度
	@Column(name="place_longitude",length=20)
	private float longitude;
	
	//纬度
	@Column(name="place_latitude",length=20)
	private float latitude;
	
	//热度
	@Column(name="place_hotPoint",length=20)
	private int hotPoint;
	
	//所在城市
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@Version
	@Column(name="update_time",length=10,nullable=false)
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public int getHotPoint() {
		return hotPoint;
	}

	public void setHotPoint(int hotPoint) {
		this.hotPoint = hotPoint;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
