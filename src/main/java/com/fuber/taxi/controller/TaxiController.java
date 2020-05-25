package com.fuber.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fuber.taxi.service.TaxiService;
import com.fuber.taxi.to.Customer;
import com.fuber.taxi.to.CustomerLocation;
import com.fuber.taxi.to.CustomerRidePreference;
import com.fuber.taxi.to.Response;
import com.fuber.taxi.to.Ride;
import com.fuber.taxi.to.RideCost;
import com.fuber.taxi.to.Taxi;
import com.fuber.taxi.to.TaxiLocation;

@RestController
public class TaxiController {
	
	@Autowired
	TaxiService taxiService;

	/*
	 * On-board taxi on the platform
	 * */
	@RequestMapping(value="/registertaxi", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> registerTaxi(@RequestBody Taxi taxi) {
		
		try {
			taxiService.registerTaxi(taxi);
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to register taxi!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<Response>(new Response("200", "Taxi Registered Successfully", ""), HttpStatus.ACCEPTED);
	}
	
	/*
	 * Update taxi location details
	 * */
	@RequestMapping(value="/updatetaxilocation", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> updateTaxiLocationDetails(@RequestBody TaxiLocation taxiLocation) {
		
		try {
			taxiService.updateTaxiLocationDetails(taxiLocation);
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to update taxi location details!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<Response>(new Response("200", "Taxi Location Details Updated Successfully", ""), HttpStatus.ACCEPTED);
	}
	
	/*
	 * Registers Customer on the platform
	 * */
	@RequestMapping(value="/registercustomer", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> registerCustomer(@RequestBody Customer customer) {
		
		try {
			taxiService.registerCustomer(customer);
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to register taxi!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<Response>(new Response("200", "Customer Registered Successfully", ""), HttpStatus.ACCEPTED);
	}
	
	/*
	 * Update customer location details
	 * */
	@RequestMapping(value="/updatecustomerlocation", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> updateCustomerLocationDetails(@RequestBody CustomerLocation customerLocation) {
		
		try {
			taxiService.updateCustomerLocationDetails(customerLocation);
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to update customer location details!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<Response>(new Response("200", "Customer Location Details Updated Successfully", ""), HttpStatus.ACCEPTED);
	}
	
	
	/*
	 * Taxi called by the Customer based on the location and taxi preferences
	 * 
	 * Response : taxi and its location details
	 * */
	@RequestMapping(value="/calltaxi", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> callTaxi(@RequestBody CustomerRidePreference customerRidePreference) {
		
		Taxi taxi = taxiService.callTaxi(customerRidePreference);
		
		try {
			if(taxi==null) {
				return new ResponseEntity<Response>(new Response("200", "Taxi Not Available near your location", taxi), HttpStatus.ACCEPTED);
			} else {
				taxiService.blockRide(taxi);//taxi blocked after assigned for a ride
				return new ResponseEntity<Response>(new Response("200", "Called Taxi Successfully", taxi), HttpStatus.ACCEPTED);
			}
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to find a taxi!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		
		
	}
	
	/*
	 * Ride started by the driver
	 * */
	@RequestMapping(value="/startride", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> startRide(@RequestBody Ride ride) {
		try {
			
			taxiService.startRide(ride);
			
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to start a ride!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		return new ResponseEntity<Response>(new Response("200", "Ride Started Successfully", ""), HttpStatus.ACCEPTED);
		
	}
	
	/*
	 * Ride ended by the driver
	 * */
	@RequestMapping(value="/endride", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> endRide(@RequestBody Ride ride) {
		
		
		RideCost rideCost=null;
		try {
			rideCost = taxiService.endRide(ride);
		} catch(Exception e) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to end a ride!", ""), HttpStatus.BAD_GATEWAY);
		}
		
		if(rideCost==null) {
			return new ResponseEntity<Response>(new Response("500", "Error occured while trying to end a ride!", ""), HttpStatus.BAD_GATEWAY);
		} else {
			return new ResponseEntity<Response>(new Response("200", "Ride Ended Successfully", rideCost), HttpStatus.ACCEPTED);
		}
		
		
	}
	
}
