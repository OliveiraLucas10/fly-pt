package com.oliveiralucaspro.flypt.enums;

public enum Airport {

	OPO("Francisco Sá Carneiro"), LIS("Humberto Delgado");

	private String name;

	Airport(String airportName) {
		this.name = airportName;
	}

	public String getAirportName() {
		return name;
	}

}
