package com.oliveiralucaspro.flypt.services;

import java.util.List;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;

public interface JsonReader {

	List<FlightDTO> getFlightsFromJson(Destination destination, ExternalRequestDTO externalRequestDTO) throws OutboundServiceException;

}
