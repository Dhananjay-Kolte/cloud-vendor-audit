package com.basic.sample.service.discovery.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudtrail.AWSCloudTrailClientBuilder;

import com.basic.sample.dto.AttackParams.AttackParamsBuilder;
import com.basic.sample.service.discovery.AWSProductDiscovery;

@Service
public class CloudTrailProductDiscovery implements AWSProductDiscovery {

	@Override
	public void build(AttackParamsBuilder paramsBuilder, Regions region, AWSCredentials credentials) {

		 paramsBuilder.cloudTrailClient(AWSCloudTrailClientBuilder
	                .standard()
	                .withRegion(region)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build());
		
	}

}
