package com.dynamoflightbookingservice.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dynamoflightbookingservice.app.model.FlightBooking;
import com.dynamoflightbookingservice.app.service.DynamoBookingService;

@RestController
@RequestMapping("/flightbooking")
public class FlightBookingController {
	
	@Autowired
	private DynamoBookingService bookingservice;
	
	
	@PostMapping("/book")
	public String bookFlight(@RequestHeader Map<String, String> headers,@RequestBody FlightBooking flightbooking)
	{
		FlightBookingController.validateToken(headers);
		if(validated)
		return bookingservice.bookFlightdata(flightbooking);
		else
			throw new RuntimeException("Token is not valid ");
	}
	
	@GetMapping("/getbooking/{bookingId}")
	public FlightBooking getBookinfById(@RequestHeader Map<String, String> headers,@PathVariable String bookingId)
	{
		FlightBookingController.validateToken(headers);
		if(validated)
		return bookingservice.getBooking(bookingId);
		else
			throw new RuntimeException("Token is not valid");
	}
	
	@DeleteMapping("/deletebooking/{bookingId}")
	public String removeBooking(@RequestHeader Map<String, String> headers,@PathVariable String bookingId)
	{
		FlightBookingController.validateToken(headers);
		if(validated)
		return bookingservice.deleteBooking(bookingId);
		else
			throw  new RuntimeException("Token is not valid");
	}
	
private static boolean validated=false;
	
	
	public  static void validateToken (Map<String, String> header)
	{
	
		String token="";
		for(String key : header.keySet())
		{
			if(key.equals("authorization"))
				token=header.get(key);
		}
		HttpHeaders httpheader = new HttpHeaders();
		httpheader.set("Authorization", token);
		HttpEntity<Void> requestentity = new HttpEntity<>(httpheader);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.exchange("http://34.216.12.139:9004/validatejwt",HttpMethod.GET, requestentity,boolean.class);
		validated=response.getBody().booleanValue();
	}
	

}
