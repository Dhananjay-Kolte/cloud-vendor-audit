package com.basic.sample.service.aws;

import com.basic.sample.repository.CloudStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basic.sample.dto.AttackParams;
import com.basic.sample.dto.AttackRequest;
import com.basic.sample.dto.AttackType;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.model.AttackSummaryBo;

public abstract class AttackRule {
	
	@Autowired
	protected CloudStateRepository cloudStateRepository;

	@Autowired
	protected ObjectMapper objectMapper;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract AttackSummaryBo attack(AttackRequest attackRequest, AttackAwsDetailBo customerAwsDetail, AttackParams attackParams, SseEmitter sseEmiter);
	public abstract boolean recover(AttackSummaryBo attackSummaryBo, AttackParams attackParams);
	public abstract AttackType getAttackType();

}
