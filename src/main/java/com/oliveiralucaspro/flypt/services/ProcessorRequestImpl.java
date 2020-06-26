package com.oliveiralucaspro.flypt.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.enums.Airport;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessorRequestImpl implements ProcessorRequest {

	private final JsonReader jsonReader;
	private final FlightsAnalyzer flightsAnalyzer;
	private final Utils utils;

	@Override
	public List<FlightDetailDTO> getFlightDetailResponse(ExternalRequestDTO externalRequestDTO)
			throws OutboundServiceException {
		List<FlightDetailDTO> response = new ArrayList<>();

		for (Destination destination : externalRequestDTO.getDestinations()) {
			response.add(getFlightDetailDTO(destination, externalRequestDTO));
		}

		return response;
	}

	private FlightDetailDTO getFlightDetailDTO(Destination destination, ExternalRequestDTO externalRequestDTO)
			throws OutboundServiceException {
		List<FlightDTO> flightsFromJson = jsonReader.getFlightsFromJson(destination, externalRequestDTO);

		String airportName = destination == Destination.OPO ? Airport.OPO.getAirportName()
				: Airport.LIS.getAirportName();

		if (flightsFromJson.isEmpty()) {
			return FlightDetailDTO.builder().destination(destination.getDestinationCode()).averagePrice(0d)
					.bagOneAveragePrice(0d).bagTwoAveragePrice(0d)
					.dateFrom(utils.getFormattedDate(externalRequestDTO.getDateFrom()))
					.dateTo(utils.getFormattedDate(externalRequestDTO.getDateTo())).airportName(airportName)
					.flightsAvailable(false).build();
		} else {
			return FlightDetailDTO.builder().destination(destination.getDestinationCode())
					.averagePrice(flightsAnalyzer.getPricesAverage(flightsFromJson).doubleValue())
					.bagOneAveragePrice(flightsAnalyzer.getBagOnePricesAverage(flightsFromJson).doubleValue())
					.bagTwoAveragePrice(flightsAnalyzer.getBagTwoPricesAverage(flightsFromJson).doubleValue())
					.dateFrom(utils.getFormattedDate(externalRequestDTO.getDateFrom()))
					.dateTo(utils.getFormattedDate(externalRequestDTO.getDateTo())).airportName(airportName).build();
		}
	}

}
