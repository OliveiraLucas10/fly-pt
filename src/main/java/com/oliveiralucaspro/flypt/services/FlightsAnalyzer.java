package com.oliveiralucaspro.flypt.services;

import java.math.BigDecimal;
import java.util.List;

import com.oliveiralucaspro.flypt.dto.FlightDTO;

public interface FlightsAnalyzer {

	BigDecimal getPricesAverage(List<FlightDTO> flights);

	BigDecimal getBagOnePricesAverage(List<FlightDTO> flights);

	BigDecimal getBagTwoPricesAverage(List<FlightDTO> flights);

}
