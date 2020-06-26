package com.oliveiralucaspro.flypt.dto;

import java.time.LocalDate;
import java.util.List;

import com.oliveiralucaspro.flypt.enums.Destination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRequestDTO {

	private List<Destination> destinations;
	private LocalDate dateFrom;
	private LocalDate dateTo;

}
