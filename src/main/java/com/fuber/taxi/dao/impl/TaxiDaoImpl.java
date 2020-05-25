package com.fuber.taxi.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fuber.taxi.dao.TaxiDao;
import com.fuber.taxi.to.Customer;
import com.fuber.taxi.to.CustomerLocation;
import com.fuber.taxi.to.Ride;
import com.fuber.taxi.to.Taxi;
import com.fuber.taxi.to.TaxiLocation;

@Repository
public class TaxiDaoImpl implements TaxiDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void registerTaxi(Taxi taxi) {
		
		String sql = "INSERT INTO taxi(vehicleModel, vehicleNumber, driverName, taxiType) VALUES (?,?,?,?)";
		Object[] params = new Object[] {taxi.getVehicleModel(), taxi.getVehicleNumber(), taxi.getDriverName(), taxi.getTaxiType()};
		
		jdbcTemplate.update(sql, params);
		
	}
	
	@Override
	public void updateTaxiLocationDetails(TaxiLocation taxiLocation) {
		
		String sql = "SELECT * FROM taxiLocationDetails WHERE vehicleNumber = ?";
        Object[] param = new Object[] {taxiLocation.getVehicleNumber()};

        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, param, countCallback);
        int rowCount = countCallback.getRowCount();
        
        if(rowCount>0) {
        	sql = "UPDATE taxiLocationDetails SET latitude=?, longitude=?, timestampValue=? WHERE vehicleNumber=?";
    		Object[] params = new Object[] {taxiLocation.getLatitude(), taxiLocation.getLongitude(), taxiLocation.getTimestampValue(), taxiLocation.getVehicleNumber()};
    		
    		jdbcTemplate.update(sql, params);
        } else {
        	sql = "INSERT INTO taxiLocationDetails(vehicleModel, latitude, longitude, timestampValue) VALUES (?,?,?,?)";
    		Object[] params = new Object[] {taxiLocation.getVehicleNumber(), taxiLocation.getLatitude(), taxiLocation.getLongitude(), taxiLocation.getTimestampValue()};
    		
    		jdbcTemplate.update(sql, params);
        }
		
	}

	@Override
	public void registerCustomer(Customer customer) {
		
		String sql = "INSERT INTO customer(customerKey, customerName, mobileNo) VALUES (?,?,?,?)";
		Object[] params = new Object[] {customer.getCustomerKey(), customer.getCustomerName(), customer.getMobileNo()};
		
		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void updateCustomerLocationDetails(CustomerLocation customerLocation) {
		
		String sql = "SELECT * FROM customerLocationDetails WHERE customerKey = ?";
        Object[] param = new Object[] {customerLocation.getCustomerKey()};

        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, param, countCallback);
        int rowCount = countCallback.getRowCount();
        
        if(rowCount>0) {
        	sql = "UPDATE taxiLocationDetails SET latitude=?, longitude=?, timestampValue=? WHERE customerKey=?";
    		Object[] params = new Object[] {customerLocation.getLatitude(), customerLocation.getLongitude(), customerLocation.getTimestampValue(), customerLocation.getCustomerKey()};
    		
    		jdbcTemplate.update(sql, params);
        } else {
        	sql = "INSERT INTO taxiLocationDetails(customerKey, latitude, longitude, timestampValue) VALUES (?,?,?,?)";
    		Object[] params = new Object[] {customerLocation.getCustomerKey(), customerLocation.getLatitude(), customerLocation.getLongitude(), customerLocation.getTimestampValue()};
    		
    		jdbcTemplate.update(sql, params);
        }
		
	}
	
	@Override
	public TaxiLocation getCurrentTaxiLocationDetails(String vehicleNumber) {
		String sql = "SELECT * FROM taxiLocationDetails WHERE vehicleNumber = ?";
        Object[] param = new Object[] {vehicleNumber};

        return jdbcTemplate.queryForObject(sql, param,
                BeanPropertyRowMapper.newInstance(TaxiLocation.class));
        
        
        
	}
	
	@Override
	public void startRide(Ride ride) {
		
		String sql = "INSERT INTO ride(vehicleNumber, customerKey, sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, sourceTimestampValue) VALUES (?,?,?,?,?,?,?)";
		Object[] params = new Object[] {ride.getVehicleNumber(), ride.getCustomerKey(), ride.getSourceLatitude(), ride.getSourceLongitude(), ride.getDestinationLatitude(), ride.getDestinationLongitude(), ride.getSourceTimestampValue()};
		
		jdbcTemplate.update(sql, params);
		
	}
	
	@Override
	public void endRide(Ride ride) {
		
		String sql = "SELECT * FROM ride WHERE id = ?";//need to change id
        Object[] param = new Object[] {ride.getId()};

        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, param, countCallback);
        int rowCount = countCallback.getRowCount();
        
        if(rowCount>0) {
        	sql = "UPDATE ride SET destinationLatitude=?, destinationLongitude=?, destinationTimestampValue=? WHERE id=?";
    		Object[] params = new Object[] {ride.getDestinationLatitude(), ride.getDestinationLongitude(), ride.getDestinationTimestampValue()};
    		
    		jdbcTemplate.update(sql, params);
        }
		
	}

	@Override
	public Taxi callTaxi(String taxiType) {
		
		String sql = "SELECT vehicleModel, vehicleNumber, driverName, taxiType FROM taxi WHERE vehicleNumber=?";
		Object[] params = new Object[] {taxiType};
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Taxi.class));
		
		//return jdbcTemplate.queryForList(sql, params, BeanPropertyRowMapper.newInstance()).get(0);
	}
	
	@Override
	public String getTaxiType(String vehicleNumber) {
		String sql = "SELECT taxiType FROM taxi WHERE vehicleNumber=?";
		Object[] params = new Object[] {vehicleNumber};
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(String.class));
	}
	
	

}
