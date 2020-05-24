package com.fuber.taxi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fuber.taxi.to.Response;

@RestController
public class TaxiController {

	@RequestMapping(value="/taxi", method=RequestMethod.GET)
	public Response callTaxi() {
		return new Response("200", "Called Taxi Successfully", "");
	}
	
	
}
