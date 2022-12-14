package com.dynamoflightbookingservice.app.model;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class FlightBooking {

	
	private String bookingId;
	private String flightName;
	private String airlineName;
	private String source;
	private String destination;
	private String fromdate;
	private String todate;
	private int numberofseats;
	
	private List<Passenger> passengers ;
	
	
	@DynamoDBAutoGeneratedKey
	@DynamoDbPartitionKey
	@DynamoDBHashKey(attributeName="bookingId")
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	@DynamoDbAttribute(value = "flightName")
	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	
	@DynamoDbAttribute(value = "airlineName")
	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	@DynamoDbAttribute(value = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@DynamoDbAttribute(value = "destination")
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@DynamoDbAttribute(value = "fromdate")
	public String getFromdate() {
		return fromdate;
	}

	
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	@DynamoDbAttribute(value = "todate")
	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	@DynamoDbAttribute(value = "numberofseats")
	public int getNumberofseats() {
		return numberofseats;
	}



	public void setNumberofseats(int numberofseats) {
		this.numberofseats = numberofseats;
	}

	@DynamoDbAttribute(value = "passengers")
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public FlightBooking(String bookingId, String flightName, String airlineName, String source, String destination,
			String fromdate, String todate, int numberofseats, List<Passenger> passengers) {
		super();
		this.bookingId = bookingId;
		this.flightName = flightName;
		this.airlineName = airlineName;
		this.source = source;
		this.destination = destination;
		this.fromdate = fromdate;
		this.todate = todate;
		this.numberofseats = numberofseats;
		this.passengers = passengers;
	}

	@Override
	public String toString() {
		return "FlightBooking [bookingId=" + bookingId + ", flightName=" + flightName + ", airlineName=" + airlineName
				+ ", source=" + source + ", destination=" + destination + ", fromdate=" + fromdate + ", todate="
				+ todate + ", numberofseats=" + numberofseats + ", passengers=" + passengers + "]";
	}
	
	public FlightBooking() {}
	
	
	
}
