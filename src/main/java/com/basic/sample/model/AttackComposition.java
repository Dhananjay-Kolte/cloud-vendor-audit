package com.basic.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "attack_composition")
public class AttackComposition {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "attack_type")
	private String attackType;
	
	@Column(name = "attack_scenario_id")
	private Long attackScenarioId;

}
