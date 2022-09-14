package com.dynamoflightbookingservice.app.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dynamoflightbookingservice.app.model.FlightBooking;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class DynamoBookingService {

	
	@Autowired
	private DynamoDbEnhancedClient enhancedClient;
	
	 @Autowired
	    private DynamoDbTable<FlightBooking> bookingTable;
	 
	 @Autowired
	 private RestTemplate resttemplate;
	 
	 public Integer adjustSeats(String flightName, Integer numberoftickets)
		{
			RestTemplate template= new RestTemplate();
			int temp1=template.getForObject("http://34.216.12.139:8082/flight/adjustseat/"+flightName+"/"+numberoftickets, Integer.class);
			return temp1;
		}
	 public Integer adjustSeatsAfterCancellation(String flightName, Integer numberoftickets)
		{
			RestTemplate template= new RestTemplate();
			int temp1=template.getForObject("http://34.216.12.139:8082/flight/adjustseatsaftercancellation/"+flightName+"/"+numberoftickets, Integer.class);
			return temp1;
		}
	 public List<FlightBooking> getallBookings()
	 {
		 return bookingTable.scan().items().stream().collect(Collectors.toList());
	 }
	 
	 public FlightBooking getBooking(String bookingId) {
		 return bookingTable.scan().items().stream().filter(a-> a.getBookingId().equals(bookingId)).findFirst().get();
	 }
	 public String bookFlightdata(FlightBooking booking)
	 {
		 int numberoftickets = booking.getPassengers().size();
		 
		 int remainingseats = adjustSeats(booking.getFlightName(), numberoftickets);
		 bookingTable.putItem(booking);
		 return booking.getBookingId();
	 }
	 
	 public String deleteBooking(String bookingId)
	 {
		 try {
			 FlightBooking b1 = bookingTable.scan().items().stream().filter(bookings -> bookings.getBookingId().equals(bookingId)).findFirst().get();
		
			 Date current = new Date();
			 DateTime fromdt1 = new DateTime(b1.getFromdate());
			 DateTime todt1 = new DateTime(b1.getTodate());
			 Date fromdate1 = fromdt1.toDate();
			 Date todate1= todt1.toDate();
			 long millis = fromdate1.getTime() - current.getTime();
			 long diffInHours = TimeUnit.MILLISECONDS.toHours(millis);
			 if(diffInHours>24)
			 {
				 int remainingseats = adjustSeatsAfterCancellation(b1.getFlightName(), b1.getNumberofseats());
				 bookingTable.deleteItem(b1);
				 return "Booking deleted successfully";
			 }
			 else
				 return "Less than 24 hrs to Cancel the booking"; 
			 
		 }catch(Exception e)
		 {
			 throw new RuntimeException("Error in cancelling the boking");
		 }
	 }
	 
	 
}

