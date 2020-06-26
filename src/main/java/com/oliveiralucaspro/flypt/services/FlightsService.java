package com.oliveiralucaspro.flypt.services;

import java.util.List;

import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.exceptions.BusinessException;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;

public interface FlightsService {
	
	List<FlightDetailDTO> getFlights(List<String> destinations, String dateFrom, String dateTo) throws BusinessException, OutboundServiceException;

}
