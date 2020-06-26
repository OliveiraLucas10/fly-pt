package com.oliveiralucaspro.flypt.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.enums.Destination;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class UtilsTest {

	@Autowired
	private Environment env;

	private Utils utils;

	@BeforeEach
	void setUp() {
		utils = new Utils(env);
	}

	@Test
	void averageTest() {
		BigDecimal expected, result;
		List<BigDecimal> bigDecimals = Arrays.asList(new BigDecimal(5), new BigDecimal(3));

		expected = new BigDecimal("4.00");
		result = utils.average(bigDecimals);
		assertTrue(expected.compareTo(result) == 0);

		bigDecimals = Arrays.asList(new BigDecimal(3.33), new BigDecimal(3));
		expected = new BigDecimal("3.17");
		result = utils.average(bigDecimals);
		assertTrue(expected.compareTo(result) == 0);
	}

	@Test
	void getRequestUrlLisTest() {
		String expected, result;
		Destination destination = Destination.LIS;
		LocalDate dateFrom = LocalDate.of(2020, 1, 8);
		LocalDate dateTo = LocalDate.of(2020, 1, 9);

		ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder().destinations(Arrays.asList(destination))
				.dateFrom(dateFrom).dateTo(dateTo).build();

		expected = "http://localhost:9800/flightSucess?fly_from=OPO&fly_to=LIS&date_from=08/01/2020&date_to=09/01/2020&select_airlines=FR,TP&curr=EUR&partner=picky";
		result = utils.getRequestUrl(destination, externalRequestDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void getRequestUrlOpoTest() {
		String expected, result;
		Destination destination = Destination.OPO;
		LocalDate dateFrom = LocalDate.of(2020, 1, 8);
		LocalDate dateTo = LocalDate.of(2020, 1, 9);

		ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder().destinations(Arrays.asList(destination))
				.dateFrom(dateFrom).dateTo(dateTo).build();

		expected = "http://localhost:9800/flightSucess?fly_from=LIS&fly_to=OPO&date_from=08/01/2020&date_to=09/01/2020&select_airlines=FR,TP&curr=EUR&partner=picky";
		result = utils.getRequestUrl(destination, externalRequestDTO);
		assertEquals(expected, result);
	}
	
	@Test
	void getFormattedDateTest() {
		String expected, result;
		LocalDate date = LocalDate.of(2020, 1, 9);
		
		expected = "09/01/2020";
		result = utils.getFormattedDate(date);
		assertEquals(expected, result);
	}

}
