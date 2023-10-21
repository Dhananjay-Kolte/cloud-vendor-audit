package com.basic.sample.service.aws;

import java.util.Arrays;
import java.util.Objects;

import com.basic.sample.contract.exception.RegionNotFoundException;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AWSSecurityTokenServiceFactory {

    private final BasicAWSCredentials credentials;

    public AWSSecurityTokenService getTokenService(Regions regionName) {
        Regions region = Arrays.stream(Regions.values()).filter(rg -> Objects.equals(rg, regionName))
                               .findFirst().orElseThrow(RegionNotFoundException::new);
        return AWSSecurityTokenServiceClientBuilder.standard()
                                                   .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                                   .withRegion(region)
                                                   .build();
    }

}
