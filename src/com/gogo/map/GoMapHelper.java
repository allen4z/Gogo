package com.gogo.map;

import com.gogo.domain.City;

public class GoMapHelper {
	
	public static final float COORD_RANGE = 100.0f;
	
	//经度
	private Double longitude;
	
	//纬度
	private Double latitude;
	/**
	 * 根据ip得到城市信息
	 * @param addr
	 * @return
	 */
	public static City getCityInfo(String addr){
		//TODO 通过ip获得城市信息
		return null;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public static float getCoordRange() {
		return COORD_RANGE;
	}

	
}
