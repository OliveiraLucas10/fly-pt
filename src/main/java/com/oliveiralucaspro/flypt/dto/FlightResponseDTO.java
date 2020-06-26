package com.oliveiralucaspro.flypt.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDTO {
	@JsonValue
	List<FlightDetailDTO> flights;

}
