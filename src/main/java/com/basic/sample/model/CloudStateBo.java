package com.basic.sample.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.basic.sample.contract.dto.ResourceType;
import com.basic.sample.dto.ServiceProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cloudstate")
public class CloudStateBo extends BaseBo {

	@Enumerated(EnumType.STRING)
	private ServiceProvider csp;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "resource_type")
    ResourceType resourceType;
	
	@Column(name = "resource_name")
    String resourceName;
	
	private String snapshot;
	
	@Builder.Default
	Boolean recovered = false;
	
	Date timeStamp;
}
