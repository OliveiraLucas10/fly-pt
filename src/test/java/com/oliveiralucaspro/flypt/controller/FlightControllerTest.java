package com.oliveiralucaspro.flypt.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.enums.Airport;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.BusinessException;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.repositories.RequestRepository;
import com.oliveiralucaspro.flypt.services.FlightsService;
import com.oliveiralucaspro.flypt.utils.Constants;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FlightController.class)
class FlightControllerTest {

	private final static String VALID_DATE = "01/01/2030";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlightsService flightsService;
	
	@MockBean
	private RequestRepository requestRepository;

	@Test
	void testGetFlights() throws Exception {
		when(flightsService.getFlights(any(), any(), any())).thenReturn(Arrays.asList(getFlightDetailDTOToLis()));

		mockMvc.perform(get("/flight").contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	void testGetFlightsBusinessException() throws Exception {
		when(flightsService.getFlights(any(), any(), any())).thenAnswer(invocation -> {
			throw new BusinessException(Constants.DATE_FORMAT);
		});

		mockMvc.perform(get("/flight").contentType("application/json")).andExpect(status().isBadRequest())
				.andExpect(status().reason(containsString(Constants.DATE_FORMAT)));
	}

	@Test
	void testGetFlightsOutboundServiceException() throws Exception {
		when(flightsService.getFlights(any(), any(), any())).thenAnswer(invocation -> {
			throw new OutboundServiceException(
					"\"message\": \"Here you will be notified of the error that occurred.\"");
		});

		mockMvc.perform(get("/flight").contentType("application/json")).andExpect(status().is(503)).andExpect(status()
				.reason("Error accessing external service. Try again later. ErrorDetails: \"message\": \"Here you will be notified of the error that occurred.\""));
	}

	private FlightDetailDTO getFlightDetailDTOToLis() {
		return FlightDetailDTO.builder().destination(Destination.LIS.getDestinationCode()).averagePrice(0d)
				.bagOneAveragePrice(0d).bagTwoAveragePrice(0d).dateFrom(VALID_DATE).dateTo(VALID_DATE)
				.airportName(Airport.LIS.getAirportName()).build();
	}

}
