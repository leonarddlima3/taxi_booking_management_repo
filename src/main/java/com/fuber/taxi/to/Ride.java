package com.fuber.taxi.to;

public class Ride {
	
	private int id;
	private String vehicleNumber;
	private String customerKey;
	private int sourceLatitude;
	private int sourceLongitude;
	private int destinationLatitude;
	private int destinationLongitude;
	private String sourceTimestampValue;
	private String destinationTimestampValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public int getSourceLatitude() {
		return sourceLatitude;
	}
	public void setSourceLatitude(int sourceLatitude) {
		this.sourceLatitude = sourceLatitude;
	}
	public int getSourceLongitude() {
		return sourceLongitude;
	}
	public void setSourceLongitude(int sourceLongitude) {
		this.sourceLongitude = sourceLongitude;
	}
	public int getDestinationLatitude() {
		return destinationLatitude;
	}
	public void setDestinationLatitude(int destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}
	public int getDestinationLongitude() {
		return destinationLongitude;
	}
	public void setDestinationLongitude(int destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}
	public String getSourceTimestampValue() {
		return sourceTimestampValue;
	}
	public void setSourceTimestampValue(String sourceTimestampValue) {
		this.sourceTimestampValue = sourceTimestampValue;
	}
	public String getDestinationTimestampValue() {
		return destinationTimestampValue;
	}
	public void setDestinationTimestampValue(String destinationTimestampValue) {
		this.destinationTimestampValue = destinationTimestampValue;
	}
	
	

}
