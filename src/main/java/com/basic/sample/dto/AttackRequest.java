package com.basic.sample.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttackRequest {

	@NotEmpty
	private Long userId;
	
	@NotEmpty
	private ServiceProvider target;
	
	@NotEmpty
	private List<AttackTypeRequest> attackTypeRequest;
	
	private Date scheduledDate;
	
	@JsonIgnore
	private String attackId;
	
	private String scenariokey;
}
