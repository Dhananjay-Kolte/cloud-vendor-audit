package com.basic.sample.dto;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudtrail.AWSCloudTrail;
import com.amazonaws.services.config.AmazonConfig;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.s3.AmazonS3;

import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.service.aws.AttackRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackParams {
	
    Long userId;
    AmazonS3 s3Client;
    AWSCloudTrail cloudTrailClient;
    AmazonConfig configClient;
    AmazonEC2 ec2Client;
    AWSLambda lambdaClient;
    AmazonRDS rdsClient;
    AmazonIdentityManagement iamClient;
    //In the S3AttackRule
    AttackAwsDetailBo customerAwsDetail;
    Regions region;
    BasicSessionCredentials basicSessionCredentials;
    ConcurrentMap<AttackRule,Boolean> ruleProgressMetric;
    String sessionId;
    Map<String, Collection<?>> provisionMap;
    String accountNo;
}
