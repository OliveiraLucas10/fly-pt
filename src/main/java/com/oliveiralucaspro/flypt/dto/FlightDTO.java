package com.oliveiralucaspro.flypt.dto;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {

	private String flyTo;
	private BigDecimal price;

	@SerializedName("bags_price")
	private BagInfoDTO bag;

	public BagInfoDTO getBag() {
		return this.bag == null ? new BagInfoDTO() : this.bag;
	}
}
