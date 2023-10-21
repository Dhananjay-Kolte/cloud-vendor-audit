package com.basic.sample.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "attack_scenario")
public class AttackScenarioBo extends BaseBo {

	@Column(name = "scenario_name")
	private String scenarioName;
	
	@Column(name = "key_name")
	private String key;
	
	@OneToMany(targetEntity = AttackComposition.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinColumn(name = "attack_scenario_id", referencedColumnName = "id" )
	private List<AttackComposition> composition;
	
	private String impact;
	private String description;
	
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "date_created")
    private LocalDateTime dateCreated;
	
	@Column(name = "date_modified")
    private LocalDateTime dateModified;
	
	@Transient
	@Builder.Default
	private String category = "Custom";
	
	@Transient
	private List<String> compositionDetails;
}
