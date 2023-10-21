package com.basic.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AwsCredentialsConfig {

	@Value("${sample.aws.access-key}")
    private String accessKey;
	
	@Value("${sample.aws.secret-key}")
    private String secretKey;
	
    @Bean
    public BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}