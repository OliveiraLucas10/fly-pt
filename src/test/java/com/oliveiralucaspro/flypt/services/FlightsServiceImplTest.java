package com.oliveiralucaspro.flypt.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.enums.Airport;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.BusinessException;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Constants;

class FlightsServiceImplTest {
	
	private final static String WRONG_FORMAT_DATE = "2020/01/01";
	private final static String WRONG_PASSED_DATE = "01/01/2020";
	private final static String VALID_DATE = "01/01/2030";
	
	
	@Mock
	 private ProcessorRequest processorRequest;
	
	private FlightsServiceImpl flightsServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		flightsServiceImpl = new FlightsServiceImpl(processorRequest);
	}

	@Test
	void testGetFlightsDateFromFormatException() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			flightsServiceImpl.getFlights(Arrays.asList("TESTE"), WRONG_FORMAT_DATE, VALID_DATE);
	    });
		
		assertEquals(Constants.INVALID_DATE_FORMAT_ERROR_MESSAGE, exception.getMessage());
	}
	
	@Test
	void testGetFlightsDateFromPassedException() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			flightsServiceImpl.getFlights(Arrays.asList("TESTE"), WRONG_PASSED_DATE, VALID_DATE);
	    });
		
		assertEquals(Constants.PASSED_DATE_ERROR_MESSAGE, exception.getMessage());
	}
	
	@Test
	void testGetFlightsDateToFormatException() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			flightsServiceImpl.getFlights(Arrays.asList("TESTE"), VALID_DATE, WRONG_FORMAT_DATE);
	    });
		
		assertEquals(Constants.INVALID_DATE_FORMAT_ERROR_MESSAGE, exception.getMessage());
	}
	
	@Test
	void testGetFlightsDateToCrossedException() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			flightsServiceImpl.getFlights(Arrays.asList("TESTE"), VALID_DATE, WRONG_PASSED_DATE);
	    });
		
		assertEquals(Constants.CROSSED_DATE_ERROR_MESSAGE, exception.getMessage());
	}
	
	@Test
	void testGetFlightsDestinationException() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			flightsServiceImpl.getFlights(Arrays.asList("TESTE"), VALID_DATE, VALID_DATE);
	    });
		
		assertEquals(Constants.INVALID_DESTINATION_ERROR_MESSAGE, exception.getMessage());
	}

	@Test
	void testGetFlights() throws OutboundServiceException, BusinessException {
		when(processorRequest.getFlightDetailResponse(any())).thenReturn(Arrays.asList(getFlightDetailDTOToLis()));
		List<FlightDetailDTO> flights = flightsServiceImpl.getFlights(Arrays.asList("LIS"), VALID_DATE, VALID_DATE);
		assertEquals(1, flights.size());
		verify(processorRequest, times(1)).getFlightDetailResponse(any());
	}
	
	private FlightDetailDTO getFlightDetailDTOToLis(){
		return FlightDetailDTO.builder().destination(Destination.LIS.getDestinationCode()).averagePrice(0d)
				.bagOneAveragePrice(0d).bagTwoAveragePrice(0d)
				.dateFrom(VALID_DATE)
				.dateTo(VALID_DATE).airportName(Airport.LIS.getAirportName())
				.build();
	}
}
