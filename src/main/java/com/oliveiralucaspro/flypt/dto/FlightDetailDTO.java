package com.oliveiralucaspro.flypt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetailDTO {

	private String destination;
	@Builder.Default
	private String currency = "EUR";
	private Double averagePrice;
	private Double bagOneAveragePrice;
	private Double bagTwoAveragePrice;
	private String dateFrom;
	private String dateTo;
	private String airportName;
	@Builder.Default
	private boolean flightsAvailable = true;

}
