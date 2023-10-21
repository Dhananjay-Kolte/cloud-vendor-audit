package com.basic.sample.service.discovery.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

import com.basic.sample.dto.AttackParams.AttackParamsBuilder;
import com.basic.sample.service.discovery.AWSProductDiscovery;

@Service
public class EC2ProductDiscovery implements AWSProductDiscovery {

	@Override
	public void build(AttackParamsBuilder paramsBuilder, Regions region, AWSCredentials credentials) {
		 paramsBuilder.ec2Client(AmazonEC2ClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(region)
					.build());		
	}

}
