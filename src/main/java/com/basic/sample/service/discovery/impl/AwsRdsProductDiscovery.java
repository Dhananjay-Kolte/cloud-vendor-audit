package com.basic.sample.service.discovery.impl;

import com.basic.sample.service.discovery.AWSProductDiscovery;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;

import com.basic.sample.dto.AttackParams.AttackParamsBuilder;

@Service
public class AwsRdsProductDiscovery  implements AWSProductDiscovery {

	@Override
	public void build(AttackParamsBuilder paramsBuilder, Regions region, AWSCredentials credentials) {
		 paramsBuilder.rdsClient(AmazonRDSClientBuilder
	                .standard()
	                .withRegion(region)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build());
		
	}

}
