package com.basic.sample.service.aws.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.basic.sample.repository.AttackSummaryRepository;
import com.basic.sample.service.aws.AWSSecurityTokenServiceFactory;
import com.basic.sample.service.AppService;
import com.basic.sample.service.CustomerAwsDetailService;
import com.basic.sample.service.aws.AttackRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.amazonaws.AmazonServiceException;

import com.basic.sample.dto.AttackParams;
import com.basic.sample.dto.AttackRequest;
import com.basic.sample.dto.AttackStatus;
import com.basic.sample.dto.AttackSummaryStatus;
import com.basic.sample.dto.AttackTypeRequest;
import com.basic.sample.dto.ObservableProgress;
import com.basic.sample.dto.RollbackRequest;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.model.AttackReportBo;
import com.basic.sample.model.AttackSummaryBo;

@Service
public class AwsServiceImpl extends AppService {

	@Value("${aws.access-key}")
	private String accessKey;

	@Value("${aws.secret-key}")
	private String secretKey;

	@Value("${aws.s3.exclude-buckets}")
	protected String[] excludeBuckets;

	@Autowired
	protected CustomerAwsDetailService customerAwsDetailService;

	@Autowired
	protected AttackSummaryRepository attackSummaryRepository;

	@Autowired
	private AWSSecurityTokenServiceFactory awsSecurityTokenServiceFactory;

	private final static String ROLE_SESSION_NAME = "test";
	protected final static String AWS_LABEL = "AWS";
	protected final static String NOT_APPLICABLE = "NOT APPLICABLE";

	@Autowired
	private List<AttackRule> attackRule;



	public AttackReportBo attack(AttackRequest attackRequest, AttackAwsDetailBo customerAwsDetail, AttackReportBo attackReport, SseEmitter sseEmitter, ObservableProgress progress, AttackParams attackParam) {

		for(AttackTypeRequest attackTypeRequest: attackRequest.getAttackTypeRequest()) {
			List<AttackSummaryBo> attackResult = null;

			attackResult = attackRule.stream().filter(attackRule -> attackRule.getAttackType() == attackTypeRequest.getAttackType()).map(attackRule -> attackRule.attack(attackRequest, customerAwsDetail, attackParam, sseEmitter)).collect(Collectors.toList());

			if(attackResult!= null && !attackResult.isEmpty()) {
				attackResult.forEach(attackSummary -> {
					if(attackSummary != null && !(AttackSummaryStatus.SUCCESS.name()+" ").equalsIgnoreCase(attackSummary.getStatus()) && attackSummary.getPersist()) {
						attackSummary.setAttackId(attackRequest.getAttackId());
						attackSummary.setUserId(customerAwsDetail.getUser());
						attackSummary.setAction(attackTypeRequest.getAttackType());
						attackSummary.setTimeStamp(new Date());
						if(attackSummary.getStatus() == null)
							attackSummary.setStatus(AttackSummaryStatus.FAIL.name());
						attackSummaryRepository.save(attackSummary);
					}
				});
			}

			try {
				sseEmitter.send(SseEmitter.event().name("Progress").data(progress.increment((int) 80/attackRequest.getAttackTypeRequest().size()), MediaType.APPLICATION_JSON));
			} catch (IOException e) {
				logger.error("Error while sending SSE", e);
			}
		}

		attackReport.setStatus(AttackStatus.COMPLETED);
		attackReport.setEndTime(LocalDateTime.now());
		return attackReport;

	}

	public Boolean rollback(RollbackRequest rollbackRequest, AttackAwsDetailBo customerAwsDetail, AttackParams attackParam) {

		List<AttackSummaryBo> recoverAttacks = null;

		if(rollbackRequest.getSummaryIds() == null || rollbackRequest.getSummaryIds().isEmpty())
			recoverAttacks = attackSummaryRepository.findAllByAttackId(rollbackRequest.getAttackId());
		else {
			recoverAttacks = attackSummaryRepository.findByIdIn(rollbackRequest.getSummaryIds());
		}

		for(AttackSummaryBo recoverAttack : recoverAttacks) {
			
			try {
				attackRule.stream().filter(attackRule -> attackRule.getAttackType() == recoverAttack.getAction() && recoverAttack.getStatus().equals(AttackSummaryStatus.SUCCESS.name())).map(attackRule -> attackRule.recover(recoverAttack, attackParam)).collect(Collectors.toList());
			} catch (AmazonServiceException e) {
				logger.error("Error while rollback : {}", e.getErrorMessage());
				return false;
			} catch (Exception e) {
				logger.error("Error while rollback : {}", e.getMessage());
				return false;
			}
		}

		return true;
	}
}