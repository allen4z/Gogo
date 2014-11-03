package com.gogo.map;

import com.gogo.domain.City;

public class GoMapHelper {
	
	public static final float COORD_RANGE = 100.0f;
	
	//经度
	private float longitude;
	
	//纬度
	private float latitude;
	/**
	 * 根据ip得到城市信息
	 * @param addr
	 * @return
	 */
	public static City getCityInfo(String addr){
		//TODO 通过ip获得城市信息
		return null;
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
}
