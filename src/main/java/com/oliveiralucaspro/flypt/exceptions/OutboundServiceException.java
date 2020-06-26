package com.oliveiralucaspro.flypt.exceptions;

public class OutboundServiceException extends Exception {
	private static final long serialVersionUID = 7666474106895562689L;

	public OutboundServiceException(String errorMessage) {
		super("Error accessing external service. Try again later. ErrorDetails: " + errorMessage);
	}
}
