package com.oliveiralucaspro.flypt.utils;

public class Constants {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String URL_AND = "&";
	
	public static final String INVALID_DESTINATION_ERROR_MESSAGE = "Invalid Destination. Destinations available are Lisbon and Porto.";
	public static final String INVALID_DATE_FORMAT_ERROR_MESSAGE = "Invalid date format. Use " + DATE_FORMAT + " format.";
	public static final String PASSED_DATE_ERROR_MESSAGE = "Departure and Arrival date cannot be before today.";
	public static final String CROSSED_DATE_ERROR_MESSAGE = "Arrival date cannot be before departure date.";
}
