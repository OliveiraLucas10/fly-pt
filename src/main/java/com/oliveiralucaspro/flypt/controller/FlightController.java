package com.oliveiralucaspro.flypt.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.oliveiralucaspro.flypt.domain.Request;
import com.oliveiralucaspro.flypt.dto.FlightResponseDTO;
import com.oliveiralucaspro.flypt.exceptions.BusinessException;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.repositories.RequestRepository;
import com.oliveiralucaspro.flypt.services.FlightsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlightController {

	private final FlightsService flightsService;
	private final RequestRepository requestRepository;

	@GetMapping(value = "/flight", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FlightResponseDTO> getFlights(
			@RequestParam(value = "dest", defaultValue = "LIS,OPO", required = false) List<String> destinations,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Request request = new Request();
		request.setRequestReceived(String.format("/flight?dateFrom=%s&dateTo=%s&dest=%s", dateFrom, dateTo,
				destinations.stream().map(Object::toString).collect(Collectors.joining(","))));
		request.setRequestTime(timestamp.toString());

		try {

			FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
			flightResponseDTO.setFlights(flightsService.getFlights(destinations, dateFrom, dateTo));
			request.setStatusCode(HttpStatus.OK.toString());
			requestRepository.save(request);
			return new ResponseEntity<>(flightResponseDTO, HttpStatus.OK);
		} catch (BusinessException exc) {
			request.setStatusCode(HttpStatus.BAD_REQUEST.toString());
			requestRepository.save(request);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getMessage());
		} catch (OutboundServiceException exc) {
			request.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.toString());
			requestRepository.save(request);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, exc.getMessage());
		}
	}
}
