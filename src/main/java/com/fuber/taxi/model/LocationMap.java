package com.fuber.taxi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("static")
public class LocationMap {
	
	private Map<String, Set<String>> latLongMap;
	
	public LocationMap() {
		latLongMap = new HashMap<>();
	}

	public Map<String, Set<String>> getLatLongMap() {
		return latLongMap;
	}

	public void setLatLongMap(Map<String, Set<String>> latLongMap) {
		this.latLongMap = latLongMap;
	}
	
	

}
