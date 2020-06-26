package com.oliveiralucaspro.flypt.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonWrapperDTO<T> {

	private List<T> data;

}
