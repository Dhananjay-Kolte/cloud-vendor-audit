package com.basic.sample.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttackProgress {
	
	public String description;
	
	public Double duration;
	
	public Boolean status;
	
	

}
