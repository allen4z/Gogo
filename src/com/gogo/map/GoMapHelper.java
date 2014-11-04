package com.gogo.map;

import com.gogo.domain.City;

public class GoMapHelper {
	
	public static final float COORD_RANGE = 100.0f;
	
	//经度
	private double longitude;
	
	//纬度
	private double latitude;
	/**
	 * 根据ip得到城市信息
	 * @param addr
	 * @return
	 */
	public static City getCityInfo(String addr){
		//TODO 通过ip获得城市信息
		return null;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
