package com.fuber.taxi.service;

import com.fuber.taxi.to.Customer;
import com.fuber.taxi.to.CustomerLocation;
import com.fuber.taxi.to.CustomerRidePreference;
import com.fuber.taxi.to.Ride;
import com.fuber.taxi.to.RideCost;
import com.fuber.taxi.to.Taxi;
import com.fuber.taxi.to.TaxiLocation;


public interface TaxiService {
	
	void registerTaxi(Taxi taxi);
	
	void updateTaxiLocationDetails(TaxiLocation taxiLocation);
	
	void registerCustomer(Customer customer);
	
	void updateCustomerLocationDetails(CustomerLocation customerLocation);
	
	Taxi callTaxi(CustomerRidePreference customerRidePreference);
	
	public void blockRide(Taxi taxi);
	
	void startRide(Ride ride);
	
	RideCost endRide(Ride ride);

}
