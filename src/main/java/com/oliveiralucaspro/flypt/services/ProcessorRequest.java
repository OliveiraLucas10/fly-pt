package com.oliveiralucaspro.flypt.services;

import java.util.List;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;

public interface ProcessorRequest {
	
	List<FlightDetailDTO> getFlightDetailResponse(ExternalRequestDTO externalRequestDTO) throws OutboundServiceException ;

}
