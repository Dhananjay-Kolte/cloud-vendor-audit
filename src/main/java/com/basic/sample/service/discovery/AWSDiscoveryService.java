package com.basic.sample.service.discovery;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.basic.sample.exception.SampleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

import com.basic.sample.dto.AttackParams;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.service.aws.AWSSecurityTokenServiceFactory;
import com.basic.sample.service.aws.AttackRule;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AWSDiscoveryService {

    @Autowired
    AWSSecurityTokenServiceFactory awsSecurityTokenServiceFactory;
    
    @Autowired
    List<AttackRule> attackRule;
    
    @Autowired
    List<AWSProductDiscovery> awsProductDiscoveries;

    
    private final static String ROLE_SESSION_NAME = "test";


	public AttackParams getAttackParameters(String sessionId,
			AttackAwsDetailBo customerAwsDetail) {

		log.info("Resource Discovery initiated for customer with details {} ", customerAwsDetail);
		String errormessage;

		try {

			if (Objects.isNull(customerAwsDetail)) {
				return null;
			}

			Regions region = customerAwsDetail.getRegion();

			final AWSSecurityTokenService tokenService = awsSecurityTokenServiceFactory
					.getTokenService(region);
			log.debug("Token Service obtained {}", tokenService);

			AssumeRoleRequest roleRequest = new AssumeRoleRequest()
					.withRoleArn(customerAwsDetail.getRoleArn())
					.withExternalId(customerAwsDetail.getExternalId())
					.withRoleSessionName(ROLE_SESSION_NAME);

			log.debug("Assume role obtained {}", roleRequest);

			AssumeRoleResult roleResponse = tokenService.assumeRole(roleRequest);
			Credentials sessionCredentials = roleResponse.getCredentials();
			BasicSessionCredentials awsCredentials = new BasicSessionCredentials(
					sessionCredentials.getAccessKeyId(),
					sessionCredentials.getSecretAccessKey(),
					sessionCredentials.getSessionToken());

			log.debug("Role Response {}", roleResponse);

			AttackParams.AttackParamsBuilder paramsBuilder = AttackParams
					.builder().region(region).basicSessionCredentials(awsCredentials)
					.ruleProgressMetric(attackRule.stream().collect(Collectors.toConcurrentMap((s1) -> s1, (s1) -> false)))
					.sessionId(sessionId).provisionMap(new HashMap<String, Collection<?>>())
					.userId(customerAwsDetail.getUser())
					.accountNo(customerAwsDetail.getRoleArn());

			this.awsProductDiscoveries.stream()
			.map(dscvr -> CompletableFuture.runAsync(() -> dscvr.build(paramsBuilder,region,awsCredentials)))
			.map(CompletableFuture::join)
			.forEach(e -> { });

			return paramsBuilder.build();

		} catch (AmazonServiceException ex) {
			ex.printStackTrace();
            errormessage = ex.getErrorMessage();
		}
		throw new SampleException(errormessage);
	}


}
