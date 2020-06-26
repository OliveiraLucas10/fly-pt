package com.oliveiralucaspro.flypt.services;

import java.util.Set;

import com.oliveiralucaspro.flypt.domain.Request;

public interface RequestService {

	Set<Request> getRequests();
	
	void deleteRequests();
}
