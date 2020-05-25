package com.fuber.taxi.util;

import java.sql.Timestamp;

import com.fuber.taxi.to.Ride;

public class TaxiUtil {
	
	public static double getDistanceOfTwoPointsInKilometres(int point1x, int point1y, int point2x, int point2y) {
		
		return Math.sqrt(Math.pow(point2x-point1x, 2)+Math.pow(point2y-point1y, 2))*1.609344;
	}
	
	//Ride Cost Compute method
	public static int computeRideCost(Ride ride, String taxiType) {
		
		int cost=0;
		
		double distance = getDistanceOfTwoPointsInKilometres(ride.getSourceLatitude(), ride.getDestinationLatitude(), ride.getSourceLongitude(), ride.getDestinationLongitude());
		
		cost += distance*2;
		
		cost += (((Timestamp.valueOf(ride.getDestinationTimestampValue()).getTime()-Timestamp.valueOf(ride.getSourceTimestampValue()).getTime())/1000)%3600)/60;
		
		if(taxiType.equals("pink")) {
			cost += 5;
		}
		
		return cost;
	}

}
