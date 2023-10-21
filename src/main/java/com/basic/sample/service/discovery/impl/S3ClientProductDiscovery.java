package com.basic.sample.service.discovery.impl;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.basic.sample.dto.AttackParams.AttackParamsBuilder;
import com.basic.sample.service.discovery.AWSProductDiscovery;

@Service
public class S3ClientProductDiscovery implements AWSProductDiscovery{

	@Override
	public void build(AttackParamsBuilder paramsBuilder, Regions region, AWSCredentials credentials) {

		 paramsBuilder.s3Client(AmazonS3ClientBuilder
	                .standard()
	                .withRegion(region)
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .build());
		
	}

}
