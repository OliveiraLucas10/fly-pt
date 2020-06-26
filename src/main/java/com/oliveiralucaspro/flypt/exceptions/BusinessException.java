package com.oliveiralucaspro.flypt.exceptions;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 4736227108008766717L;

	public BusinessException(String errorMessage) {
		super(errorMessage);
	}
}
