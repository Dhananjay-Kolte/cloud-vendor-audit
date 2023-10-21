package com.basic.sample.service.discovery;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Regions;

import com.basic.sample.dto.AttackParams;

public interface AWSProductDiscovery {
    void build(AttackParams.AttackParamsBuilder paramsBuilder, Regions region,AWSCredentials credentials);
}
