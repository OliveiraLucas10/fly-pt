package com.oliveiralucaspro.flypt.services;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDTO;
import com.oliveiralucaspro.flypt.dto.JsonWrapperDTO;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonReaderHttpRequest implements JsonReader {

	private final Utils utils;

	@Override
	public List<FlightDTO> getFlightsFromJson(Destination destination, ExternalRequestDTO requestDTO)
			throws OutboundServiceException {
		try {
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(utils.getRequestUrl(destination, requestDTO))).GET().build();

			HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest,HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() != HttpStatus.OK.value()) {
				throw new OutboundServiceException(response.body());
			}

			Type listType = new TypeToken<JsonWrapperDTO<FlightDTO>>() {
			}.getType();
			JsonWrapperDTO<FlightDTO> jsonWrapper = new Gson().fromJson(response.body(), listType);

			return jsonWrapper.getData();
		} catch (IOException | InterruptedException e) {
			log.error(e.getMessage());
		}

		return Collections.emptyList();
	}

}
