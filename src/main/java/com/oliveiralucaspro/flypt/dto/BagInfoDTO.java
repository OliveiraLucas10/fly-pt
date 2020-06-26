package com.oliveiralucaspro.flypt.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BagInfoDTO {

	@SerializedName("1")
	private BigDecimal onePrice;

	@SerializedName("2")
	private BigDecimal twoPrice;

}
