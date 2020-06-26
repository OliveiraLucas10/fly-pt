package com.oliveiralucaspro.flypt.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.oliveiralucaspro.flypt.domain.Request;
import com.oliveiralucaspro.flypt.repositories.RequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;

	@Override
	public Set<Request> getRequests() {
		Set<Request> requestSet = new HashSet<>();
		requestRepository.findAll().iterator().forEachRemaining(requestSet::add);
		return requestSet;
	}

	@Override
	public void deleteRequests() {
		requestRepository.deleteAll();
	}

}
