package com.gogo.dao;

import org.springframework.stereotype.Repository;

import com.gogo.domain.Place;


@Repository
public class PlaceDao extends BaseDao<Place>{

	public Place findPlaceByNameAndLocal(Place place){
		String name = place.getName();
		double longitude = place.getLongitude();
		double latitude = place.getLatitude();
		
		String hql = "from Place place where place.name=? and place.longitude=? and place.latitude=?";
		return findUnique(hql, name,longitude,latitude);
	}
	
}
