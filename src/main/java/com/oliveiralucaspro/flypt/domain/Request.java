package com.oliveiralucaspro.flypt.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Request {
	@Id
	private String id;

	private String requestReceived;
	private String requestTime;
	private String statusCode;

}
