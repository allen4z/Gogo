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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_place")
public class Place {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="place_id",length=32)
	private String placeId;
	
	@Column(name="place_code",length=10)
	private String placeCode;
	
	@Column(name="place_longitude",length=20)
	private float longitude;
	
	@Column(name="place_latitude",length=20)
	private float latitude;
	
	@Column(name="place_hotPoint",length=20)
	private int hotPoint;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@OneToMany(mappedBy="placeNameId",cascade=CascadeType.ALL)
	private Set<PlaceName> placeNames;
	
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

	public Set<PlaceName> getPlaceNames() {
		return placeNames;
	}

	public void setPlaceNames(Set<PlaceName> placeNames) {
		this.placeNames = placeNames;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
}
