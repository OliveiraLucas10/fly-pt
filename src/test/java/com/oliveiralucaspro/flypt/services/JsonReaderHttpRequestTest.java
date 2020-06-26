package com.oliveiralucaspro.flypt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Utils;

class JsonReaderHttpRequestTest {
	
	@Mock
	private Utils utils;
	
	private JsonReaderHttpRequest jsonReaderHttpRequest;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		jsonReaderHttpRequest =  new JsonReaderHttpRequest(utils);
	}

	@Test
	void getFlightsFromJsonTestSucessRequest() throws OutboundServiceException {
		when(utils.getRequestUrl(any(), any())).thenReturn("http://localhost:9800/flightSucess");
		List<FlightDTO> flightsFromJson = jsonReaderHttpRequest.getFlightsFromJson(Destination.LIS, new ExternalRequestDTO());
		assertEquals(5, flightsFromJson.size());
	}
	
	@Test
	void getFlightsFromJsonTestErrorRequest(){
		when(utils.getRequestUrl(any(), any())).thenReturn("http://localhost:9800/flightError");
		
		Exception exception = assertThrows(OutboundServiceException.class, () -> {
			List<FlightDTO> flightsFromJson = jsonReaderHttpRequest.getFlightsFromJson(Destination.LIS, new ExternalRequestDTO());
			assertTrue(flightsFromJson.isEmpty());
	    });
		
		assertEquals("Error accessing external service. Try again later. ErrorDetails: {\n" + 
				"  \"message\": \"Here you will be notified of the error that occurred.\"\n" + 
				"}", exception.getMessage());
	}

}
