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
public class Place {

	//主键
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="place_id",length=32)
	private String placeId;
	
	//地点编码
	@Column(name="place_code",length=10)
	private String placeCode;
	
	//地点名称（可能同意地点有多个名称）
//	@OneToMany(mappedBy="placeNameId",cascade=CascadeType.ALL)
//	private Set<PlaceName> placeNames;
	
	@Column(name="place_name",length=50)
	private String placeName;
	
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

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceCode() {
		return placeCode;
	}

	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
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

//	public Set<PlaceName> getPlaceNames() {
//		return placeNames;
//	}
//
//	public void setPlaceNames(Set<PlaceName> placeNames) {
//		this.placeNames = placeNames;
//	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	
	
	
}
