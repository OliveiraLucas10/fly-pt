package com.oliveiralucaspro.flypt.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.oliveiralucaspro.flypt.dto.BagInfoDTO;
import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.enums.Airport;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Utils;

class ProcessorRequestImplTest {
	
	private final static String LISBON = "Lisbon";
	private final static BigDecimal PRICE_ONE = new BigDecimal("100.00");
	private final static BigDecimal PRICE_TWO = new BigDecimal("100.00");
	private final static BigDecimal PRICE_BAG_ONE = new BigDecimal("30.00");
	private final static BigDecimal PRICE_BAG_ONE_SECOND = new BigDecimal("30.00");
	private final static BigDecimal PRICE_BAG_TWO = new BigDecimal("50.00");
	private final static BigDecimal PRICE_BAG_TWO_SECOND = new BigDecimal("50.00");
	private final static String DATE = "09/01/2020";

	@Mock
	private Environment env;

	@Mock
	private JsonReader jsonReader;

	@Mock
	private FlightsAnalyzer flightsAnalyzer;
	
	@Mock
	private Utils utils;
	
	private ProcessorRequestImpl processorRequestImpl;


	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		processorRequestImpl =  new ProcessorRequestImpl(jsonReader, flightsAnalyzer, utils);
		
	}

	@Test
	void testGetFlightDetailResponseLisOpo() throws OutboundServiceException {
		
		List<FlightDetailDTO> expected, result;
		
		ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder()
				.destinations(Arrays.asList(Destination.LIS, Destination.OPO))
				.dateFrom(LocalDate.now())
				.dateTo(LocalDate.now())
				.build();
		
		expected = Arrays.asList(getFlightDetailDTOToLis(), getFlightDetailDTOToOpo());
		
		when(jsonReader.getFlightsFromJson(any(), any())).thenReturn(getFlights());
		when(flightsAnalyzer.getPricesAverage(any())).thenReturn(new BigDecimal("50.00"));
		when(flightsAnalyzer.getBagOnePricesAverage(any())).thenReturn(new BigDecimal("15.00"));
		when(flightsAnalyzer.getBagTwoPricesAverage(any())).thenReturn(new BigDecimal("25.00"));
		when(utils.getFormattedDate(any())).thenReturn(DATE);
		
		result = processorRequestImpl.getFlightDetailResponse(externalRequestDTO);
		
		assertEquals(expected.size(), result.size());
		
		verify(jsonReader, times(2)).getFlightsFromJson(any(), any());
		verify(flightsAnalyzer, times(2)).getPricesAverage(any());
		verify(flightsAnalyzer, times(2)).getBagOnePricesAverage(any());
		verify(flightsAnalyzer, times(2)).getBagTwoPricesAverage(any());
		verify(utils, times(4)).getFormattedDate(any());
	}
	
	@Test
	void testGetFlightDetailResponseLis() throws OutboundServiceException {
		
		List<FlightDetailDTO> expected, result;
		
		ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder()
				.destinations(Arrays.asList(Destination.LIS))
				.dateFrom(LocalDate.now())
				.dateTo(LocalDate.now())
				.build();
		
		expected = Arrays.asList(getFlightDetailDTOToLis());
		
		when(jsonReader.getFlightsFromJson(any(), any())).thenReturn(getFlights());
		when(flightsAnalyzer.getPricesAverage(any())).thenReturn(new BigDecimal("50.00"));
		when(flightsAnalyzer.getBagOnePricesAverage(any())).thenReturn(new BigDecimal("15.00"));
		when(flightsAnalyzer.getBagTwoPricesAverage(any())).thenReturn(new BigDecimal("25.00"));
		when(utils.getFormattedDate(any())).thenReturn(DATE);
		
		result = processorRequestImpl.getFlightDetailResponse(externalRequestDTO);
		
		assertEquals(expected.size(), result.size());
		
		verify(jsonReader, times(1)).getFlightsFromJson(any(), any());
		verify(flightsAnalyzer, times(1)).getPricesAverage(any());
		verify(flightsAnalyzer, times(1)).getBagOnePricesAverage(any());
		verify(flightsAnalyzer, times(1)).getBagTwoPricesAverage(any());
		verify(utils, times(2)).getFormattedDate(any());
	}
	
	@Test
	void testGetFlightDetailResponseEmptyList() throws OutboundServiceException {
		
		List<FlightDetailDTO> expected, result;
		
		ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder()
				.destinations(Arrays.asList(Destination.LIS))
				.dateFrom(LocalDate.now())
				.dateTo(LocalDate.now())
				.build();
		
		expected = Arrays.asList(getFlightDetailDTOToLis());
		
		when(jsonReader.getFlightsFromJson(any(), any())).thenReturn(Collections.emptyList());
		when(flightsAnalyzer.getPricesAverage(any())).thenReturn(new BigDecimal("50.00"));
		when(flightsAnalyzer.getBagOnePricesAverage(any())).thenReturn(new BigDecimal("15.00"));
		when(flightsAnalyzer.getBagTwoPricesAverage(any())).thenReturn(new BigDecimal("25.00"));
		when(utils.getFormattedDate(any())).thenReturn(DATE);
		
		result = processorRequestImpl.getFlightDetailResponse(externalRequestDTO);
		
		assertEquals(expected.size(), result.size());
		
		verify(jsonReader, times(1)).getFlightsFromJson(any(), any());
		verify(flightsAnalyzer, never()).getPricesAverage(any());
		verify(flightsAnalyzer, never()).getBagOnePricesAverage(any());
		verify(flightsAnalyzer, never()).getBagTwoPricesAverage(any());
		verify(utils, times(2)).getFormattedDate(any());
	}
	
	private FlightDetailDTO getFlightDetailDTOToLis(){
		return FlightDetailDTO.builder().destination(Destination.LIS.getDestinationCode()).averagePrice(0d)
				.bagOneAveragePrice(0d).bagTwoAveragePrice(0d)
				.dateFrom(DATE)
				.dateTo(DATE).airportName(Airport.LIS.getAirportName())
				.build();
	}
	
	private FlightDetailDTO getFlightDetailDTOToOpo(){
		return FlightDetailDTO.builder().destination(Destination.OPO.getDestinationCode()).averagePrice(0d)
				.bagOneAveragePrice(0d).bagTwoAveragePrice(0d)
				.dateFrom(DATE)
				.dateTo(DATE).airportName(Airport.OPO.getAirportName())
				.build();
	}
	
	private List<FlightDTO> getFlights() {

		FlightDTO flightOne = FlightDTO.builder().flyTo(LISBON).price(PRICE_ONE)
				.bag(BagInfoDTO.builder().onePrice(PRICE_BAG_ONE).twoPrice(PRICE_BAG_TWO).build()).build();

		FlightDTO flightTwo = FlightDTO.builder().flyTo(LISBON).price(PRICE_TWO)
				.bag(BagInfoDTO.builder().onePrice(PRICE_BAG_ONE_SECOND).twoPrice(PRICE_BAG_TWO_SECOND).build()).build();

		return Arrays.asList(flightOne, flightTwo);
	}

}
