package com.oliveiralucaspro.flypt.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.oliveiralucaspro.flypt.dto.BagInfoDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.utils.Utils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightsAnalyzerImpl implements FlightsAnalyzer {

	private final Utils utils;

	@Override
	public BigDecimal getPricesAverage(List<FlightDTO> flights) {
		List<BigDecimal> prices = flights.stream().filter(Objects::nonNull).map(FlightDTO::getPrice)
				.collect(Collectors.toList());
		return utils.average(prices);
	}

	@Override
	public BigDecimal getBagOnePricesAverage(List<FlightDTO> flights) {
		List<BigDecimal> bagOnePrices = flights.stream().map(FlightDTO::getBag).map(BagInfoDTO::getOnePrice)
				.filter(Objects::nonNull).collect(Collectors.toList());
		return utils.average(bagOnePrices);
	}

	@Override
	public BigDecimal getBagTwoPricesAverage(List<FlightDTO> flights) {
		List<BigDecimal> bagTwoPrices = flights.stream().map(FlightDTO::getBag).map(BagInfoDTO::getTwoPrice)
				.filter(Objects::nonNull).collect(Collectors.toList());
		return utils.average(bagTwoPrices);
	}

}
