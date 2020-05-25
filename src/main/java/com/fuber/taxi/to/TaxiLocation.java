package com.fuber.taxi.to;

public class TaxiLocation {
	
	String vehicleNumber;
	int latitude;
	int longitude;
	String timestampValue;
	
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public String getTimestampValue() {
		return timestampValue;
	}
	public void setTimestampValue(String timestampValue) {
		this.timestampValue = timestampValue;
	}
	
}
