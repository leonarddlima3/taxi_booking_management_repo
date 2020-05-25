package com.fuber.taxi;

import java.util.Arrays;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class TaxiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxiApplication.class, args);
	}
	
	@Autowired
	Environment env;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Lets inspect the beans provided by spring boot!");
			
			String beanNames[]=ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for(String beanName: beanNames) {
				System.out.println(beanName);
			}
			
			//create the database tables
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS taxi(vehicleNumber VARCHAR(100) PRIMARY KEY, vehicleModel VARCHAR(100), driverName VARCHAR(100), taxiType VARCHAR(100))");
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS taxiLocationDetails(vehicleNumber VARCHAR(100) PRIMARY KEY, latitude INTEGER, longitude INTEGER, timestampValue VARCHAR(100))");
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS customer(customerKey VARCHAR(100) PRIMARY KEY, customerName VARCHAR(100), mobileNo INTEGER)");
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS customerLocationDetails(customerKey VARCHAR(100) PRIMARY KEY, latitude INTEGER, longitude INTEGER, timestampValue VARCHAR(100))");
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS ride(vehicleNumber VARCHAR(100), customerKey VARCHAR(100) PRIMARY KEY, sourceLatitude INTEGER, sourceLongitude INTEGER, destinationLatitude INTEGER, destinationLongitude INTEGER, sourceTimestampValue VARCHAR(100), destinationTimestampValue VARCHAR(100))");
			
			
		};
	}
	
	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setUsername(env.getProperty("user"));
		dataSource.setPassword(env.getProperty("password"));
		
		return dataSource;
	}
	
	

}
