package com.oliveiralucaspro.flypt.utils;

import static com.oliveiralucaspro.flypt.utils.Constants.URL_AND;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.enums.Destination;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Utils {

	private final Environment env;

	public BigDecimal average(List<BigDecimal> bigDecimals) {
		BigDecimal sum = bigDecimals.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
		return sum.divide(new BigDecimal(bigDecimals.size()), 2, RoundingMode.HALF_EVEN);
	}

	public String getRequestUrl(Destination destination, ExternalRequestDTO externalRequestDTO) {
		Destination origin = destination == Destination.OPO ? Destination.LIS : Destination.OPO;
		StringBuilder url = new StringBuilder();

		url.append(env.getProperty("skypicker.api.base.url"));
		url.append(env.getProperty("skypicker.api.fly.from"));
		url.append(origin.getDestinationCode());
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.fly.to"));
		url.append(destination.getDestinationCode());
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.date.from"));
		url.append(getFormattedDate(externalRequestDTO.getDateFrom()));
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.date.to"));
		url.append(getFormattedDate(externalRequestDTO.getDateTo()));
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.airlines"));
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.currency"));
		url.append(URL_AND);
		url.append(env.getProperty("skypicker.api.partner"));

		return url.toString();
	}


	public String getFormattedDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
	}

}
