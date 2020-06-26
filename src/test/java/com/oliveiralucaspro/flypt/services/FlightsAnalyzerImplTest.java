package com.oliveiralucaspro.flypt.services;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import com.oliveiralucaspro.flypt.dto.BagInfoDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.utils.Utils;

class FlightsAnalyzerImplTest {

	private final static String LISBON = "Lisbon";
	private final static BigDecimal priceOne = new BigDecimal("100.25");
	private final static BigDecimal priceTwo = new BigDecimal("95.73");
	private final static BigDecimal priceBagOne = new BigDecimal("33.71");
	private final static BigDecimal priceBagOneSecond = new BigDecimal("35.71");
	private final static BigDecimal priceBagTwo = new BigDecimal("63.13");
	private final static BigDecimal priceBagTwoSecond = new BigDecimal("65.13");

	@Mock
	private Environment env;

	private Utils utils;

	private FlightsAnalyzerImpl flightsAnalyzerImpl;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		utils = new Utils(env);
		flightsAnalyzerImpl = new FlightsAnalyzerImpl(utils);

	}

	@Test
	void testGetPricesAverageWithoutNull() {
		BigDecimal expected, result;

		expected = new BigDecimal("97.99");
		result = flightsAnalyzerImpl.getPricesAverage(getFlights(false));
		assertTrue(expected.compareTo(result) == 0);
	}

	@Test
	void testGetPricesAverageWithNull() {
		BigDecimal expected, result;

		expected = new BigDecimal("97.99");
		result = flightsAnalyzerImpl.getPricesAverage(getFlights(true));
		assertTrue(expected.compareTo(result) == 0);
	}
 
	@Test
	void testGetBagOnePricesAverageWithoutNull() {
		BigDecimal expected, result;

		expected = new BigDecimal("34.71");
		result = flightsAnalyzerImpl.getBagOnePricesAverage(getFlights(false));
		assertTrue(expected.compareTo(result) == 0);
	}
	
	@Test
	void testGetBagOnePricesAverageWithNull() {
		BigDecimal expected, result;

		expected = new BigDecimal("35.71");
		result = flightsAnalyzerImpl.getBagOnePricesAverage(getFlightsWithOneBagPriceNull());
		assertTrue(expected.compareTo(result) == 0);
	}

	@Test
	void testGetBagTwoPricesAverageWithoutNull() {
		BigDecimal expected, result;
		
		expected = new BigDecimal("64.13");
		result = flightsAnalyzerImpl.getBagTwoPricesAverage(getFlights(false));
		assertTrue(expected.compareTo(result) == 0);
	}
	
	@Test
	void testGetBagTwoPricesAverageWithNull() {
		BigDecimal expected, result;
		
		expected = new BigDecimal("63.13");
		result = flightsAnalyzerImpl.getBagTwoPricesAverage(getFlightsWithTwoBagPriceNull());
		assertTrue(expected.compareTo(result) == 0);
	}

	private List<FlightDTO> getFlights(Boolean withNull) {

		FlightDTO flightOne = FlightDTO.builder().flyTo(LISBON).price(priceOne)
				.bag(BagInfoDTO.builder().onePrice(priceBagOne).twoPrice(priceBagTwo).build()).build();

		FlightDTO flightTwo = FlightDTO.builder().flyTo(LISBON).price(priceTwo)
				.bag(BagInfoDTO.builder().onePrice(priceBagOneSecond).twoPrice(priceBagTwoSecond).build()).build();

		return withNull ? Arrays.asList(flightOne, flightTwo, null) : Arrays.asList(flightOne, flightTwo);
	}

	private List<FlightDTO> getFlightsWithOneBagPriceNull() {

		FlightDTO flightOne = FlightDTO.builder().flyTo(LISBON).price(priceOne)
				.bag(BagInfoDTO.builder().onePrice(null).twoPrice(priceBagTwo).build()).build();

		FlightDTO flightTwo = FlightDTO.builder().flyTo(LISBON).price(priceTwo)
				.bag(BagInfoDTO.builder().onePrice(priceBagOneSecond).twoPrice(priceBagTwoSecond).build()).build();

		return Arrays.asList(flightOne, flightTwo);
	}
	
	private List<FlightDTO> getFlightsWithTwoBagPriceNull() {

		FlightDTO flightOne = FlightDTO.builder().flyTo(LISBON).price(priceOne)
				.bag(BagInfoDTO.builder().onePrice(priceBagOne).twoPrice(priceBagTwo).build()).build();

		FlightDTO flightTwo = FlightDTO.builder().flyTo(LISBON).price(priceTwo)
				.bag(BagInfoDTO.builder().onePrice(priceBagOneSecond).twoPrice(null).build()).build();

		return Arrays.asList(flightOne, flightTwo);
	}

}
