package com.basic.sample.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackRequest {

	@NotEmpty
	private Long userId;
	
	@NotEmpty
	private ServiceProvider target;
	
	@NotEmpty
	private String attackId;
	
	@NotEmpty
	private List<Long> summaryIds;
	
}