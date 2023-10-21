package com.basic.sample.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "scheduled_attack")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledAttackBo extends BaseBo {
	
	private static final long serialVersionUID = 9152801732195844788L;
	
	@Transient
	private Long id;
	
	@Column(name = "attack_type")
    private String attackType;
    
	@Column(name = "exclude_resources")
	private String excludeResources;
    
	@Column(name = "attack_date")
	private LocalDateTime attackDate;
	
	@Column(name = "date_created")
    private LocalDateTime dateCreated;
	
	@Column(name = "date_modified")
    private LocalDateTime dateModified;
    
    @Column(name = "user_id")
    @NotEmpty(message = "User ID is required")
    private Long user;
}
