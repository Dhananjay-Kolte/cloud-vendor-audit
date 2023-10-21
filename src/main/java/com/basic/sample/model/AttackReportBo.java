package com.basic.sample.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.basic.sample.dto.AttackStatus;
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
@Table(name = "attack_report")
public class AttackReportBo extends BaseBo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3769879746626183851L;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "attack_id")
	private String attackId;
	
	@Enumerated(EnumType.STRING)
	private ServiceProvider csp;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
    private LocalDateTime endTime;
	
	@Enumerated(EnumType.STRING)
	private AttackStatus status;
	
	@Transient
	private List<AttackSummaryBo> summaryElements;
}