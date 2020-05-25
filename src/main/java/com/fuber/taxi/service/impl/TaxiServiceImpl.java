package com.fuber.taxi.service.impl;

import static com.fuber.taxi.util.TaxiConstant.MAX_DISTANCE_THRESHOLD;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuber.taxi.dao.TaxiDao;
import com.fuber.taxi.model.LocationMap;
import com.fuber.taxi.service.TaxiService;
import com.fuber.taxi.to.Customer;
import com.fuber.taxi.to.CustomerLocation;
import com.fuber.taxi.to.CustomerRidePreference;
import com.fuber.taxi.to.Ride;
import com.fuber.taxi.to.RideCost;
import com.fuber.taxi.to.Taxi;
import com.fuber.taxi.to.TaxiLocation;
import com.fuber.taxi.util.TaxiUtil;

@Service
public class TaxiServiceImpl implements TaxiService{
	
	@Autowired
	TaxiDao taxiDao;
	
	@Autowired
	LocationMap locationMap;
	
	@Autowired
	TaxiUtil taxiUtil;
	
	@Override
	public void registerTaxi(Taxi taxi) {
		
		taxiDao.registerTaxi(taxi);
		
	}
	
	@Override
	public void updateTaxiLocationDetails(TaxiLocation taxiLocation) {
		
		//Remove already added taxi location to previous lat/long
		TaxiLocation existingTaxiLocation = taxiDao.getCurrentTaxiLocationDetails(taxiLocation.getVehicleNumber());
		Map<String, Set<String>> latLongMap = locationMap.getLatLongMap();
		if(latLongMap.containsKey(existingTaxiLocation.getLatitude()+"-"+existingTaxiLocation.getLongitude())) {
			Set<String> taxis = latLongMap.get(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude());
			taxis.remove(existingTaxiLocation.getVehicleNumber());
		}
		
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		String utcTimeStamp = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println("utcTimeStamp : "+utcTimeStamp);
		taxiLocation.setTimestampValue(utcTimeStamp);
		
		taxiDao.updateTaxiLocationDetails(taxiLocation);
		
		//Update taxi location to current lat/long
		if(latLongMap.containsKey(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude())) {
			Set<String> taxis = latLongMap.get(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude());
			taxis.add(taxiLocation.getVehicleNumber());
		}else {
			Set<String> taxis=new HashSet<>();
			taxis.add(taxiLocation.getVehicleNumber());
			latLongMap.put(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude(), taxis);
		}
		
	}

	@Override
	public void registerCustomer(Customer customer) {
		
		taxiDao.registerCustomer(customer);
		
	}

	@Override
	public void updateCustomerLocationDetails(CustomerLocation customerLocation) {
		
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		String utcTimeStamp = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println("utcTimeStamp : "+utcTimeStamp);
		customerLocation.setTimestampValue(utcTimeStamp);
		
		taxiDao.updateCustomerLocationDetails(customerLocation);
		
	}

	public Taxi callTaxi(CustomerRidePreference customerRidePreference) {
		
		Taxi taxi=null;
		
		TreeSet<String> availableTaxis=new TreeSet<>();
		
		Map<String, Set<String>> latLongMap = locationMap.getLatLongMap();
		
		for(Entry<String, Set<String>> entry:latLongMap.entrySet()) {
			int taxiLatitude = Integer.parseInt(entry.getKey().split("-")[0]);
			int taxiLongitude = Integer.parseInt(entry.getKey().split("-")[1]);
			double distance = TaxiUtil.getDistanceOfTwoPointsInKilometres(customerRidePreference.getLatitude(), customerRidePreference.getLongitude(), taxiLatitude, taxiLongitude);
			
			if(distance<=MAX_DISTANCE_THRESHOLD) {
				availableTaxis.addAll(entry.getValue());
			}
		}
		
		//No available taxis within the distance threshold
		if(availableTaxis.isEmpty()) {
			return taxi;
		}
		
		//Check for available nearest taxi within the distance threshold having taxi as preferred by customer
		if(customerRidePreference.getTaxiType().equals("pink")) {
			for(String availableTaxi: availableTaxis) {
				taxi = taxiDao.callTaxi(availableTaxi);
				if(taxi.getTaxiType().equals("pink")) {
					return taxi;
				}
			}
			taxi=null;
			return taxi;
		} else {//Take the nearest taxi if no preference is requested
			taxi = taxiDao.callTaxi(availableTaxis.first());
		}
		
		return taxi;
	}
	
	@Override
	public void blockRide(Taxi taxi) {
		
		Map<String, Set<String>> latLongMap = locationMap.getLatLongMap();
		
		TaxiLocation taxiLocation = taxiDao.getCurrentTaxiLocationDetails(taxi.getVehicleNumber());
		
		if(latLongMap.containsKey(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude())) {
			Set<String> availableTaxis = latLongMap.get(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude());
			availableTaxis.remove(taxi.getVehicleNumber());
		}
	}

	@Override
	public void startRide(Ride ride) {
		
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		String utcTimeStamp = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println("utcTimeStamp : "+utcTimeStamp);
		ride.setSourceTimestampValue(utcTimeStamp);
		
		taxiDao.startRide(ride);
		
	}
	
	@Override
	public RideCost endRide(Ride ride) {
		
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		String utcTimeStamp = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println("utcTimeStamp : "+utcTimeStamp);
		ride.setDestinationTimestampValue(utcTimeStamp);
		
		taxiDao.endRide(ride);
		
		TaxiLocation taxiLocation=new TaxiLocation();
		taxiLocation.setLatitude(ride.getDestinationLatitude());
		taxiLocation.setLongitude(ride.getDestinationLongitude());
		taxiLocation.setTimestampValue(ride.getDestinationTimestampValue());
		taxiLocation.setVehicleNumber(ride.getVehicleNumber());
		
		taxiDao.updateTaxiLocationDetails(taxiLocation);//update taxi location to the destination location
		
		unblockRide(ride);//unblock taxi ride after ride is over
		
		String taxiType = taxiDao.getTaxiType(ride.getVehicleNumber());
		
		//compute ride cost
		int cost=TaxiUtil.computeRideCost(ride, taxiType);
		
		RideCost rideCost=new RideCost();
		rideCost.setRide(ride);
		rideCost.setCost(cost);
		
		
		return rideCost;
	}
	
	//@Override
	public void unblockRide(Ride ride) {
		
		Map<String, Set<String>> latLongMap = locationMap.getLatLongMap();
		
		TaxiLocation taxiLocation = taxiDao.getCurrentTaxiLocationDetails(ride.getVehicleNumber());
		
		if(latLongMap.containsKey(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude())) {
			Set<String> availableTaxis = latLongMap.get(taxiLocation.getLatitude()+"-"+taxiLocation.getLongitude());
			availableTaxis.add(ride.getVehicleNumber());
		}
	}

}
