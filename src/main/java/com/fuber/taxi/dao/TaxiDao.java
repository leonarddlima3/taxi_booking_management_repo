package com.fuber.taxi.dao;

import com.fuber.taxi.to.Customer;
import com.fuber.taxi.to.CustomerLocation;
import com.fuber.taxi.to.Ride;
import com.fuber.taxi.to.Taxi;
import com.fuber.taxi.to.TaxiLocation;


public interface TaxiDao {
	
	public void registerTaxi(Taxi taxi);
	
	void updateTaxiLocationDetails(TaxiLocation taxiLocation);
	
	void registerCustomer(Customer customer);
	
	void updateCustomerLocationDetails(CustomerLocation customerLocation);
	
	Taxi callTaxi(String taxiType);
	
	TaxiLocation getCurrentTaxiLocationDetails(String vehicleNumber);
	
	void startRide(Ride ride);
	
	void endRide(Ride ride);
	
	String getTaxiType(String vehicleNumber);

}
