package com.oliveiralucaspro.flypt.controller;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oliveiralucaspro.flypt.domain.Request;
import com.oliveiralucaspro.flypt.services.RequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RequestController {

	private static final String REQUESTS_DELETED = "All requests have been deleted!!";
	private final RequestService requestService;

	@GetMapping(value = "/flight/request", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<Request> getRequests() {
		return requestService.getRequests();
	}

	@GetMapping(value = "/flight/request/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDeleteRequests() {
		requestService.deleteRequests();

		return REQUESTS_DELETED;
	}
}
