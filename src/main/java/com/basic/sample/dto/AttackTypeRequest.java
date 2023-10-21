package com.basic.sample.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttackTypeRequest {

	@NotEmpty
	private AttackType attackType;
	
	private List<String> excludeResources;
	
}
