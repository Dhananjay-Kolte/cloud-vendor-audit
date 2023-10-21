package com.basic.sample.service.impl;

import java.util.Optional;

import com.basic.sample.contract.exception.EntityNotFound;
import com.basic.sample.repository.AttackerAwsDetailRepository;
import com.basic.sample.repository.CustomerAwsDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.sample.dto.ResponseCode;
import com.basic.sample.dto.Utils;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.model.CustomerAwsDetailBo;
import com.basic.sample.service.AppService;
import com.basic.sample.service.CustomerAwsDetailService;

@Service
public class CustomerAwsDetailServiceImpl extends AppService implements CustomerAwsDetailService {

    @Autowired
    private AttackerAwsDetailRepository awsDetailRepository;
    
    @Autowired
    private CustomerAwsDetailRepository customerAwsDetailRepository;
    
    @Override
    public Optional<AttackAwsDetailBo> findByUserId(Long id) {
    	Optional<AttackAwsDetailBo> attackAwsDetailBo = Optional.empty();
    	
    	Optional<CustomerAwsDetailBo> exist = customerAwsDetailRepository.findByUser(id);
    	if(exist.isPresent()) {
    		attackAwsDetailBo = awsDetailRepository.findByUser(id);
    		
    		if(!attackAwsDetailBo.isPresent()) {
    			attackAwsDetailBo = Optional.of(AttackAwsDetailBo.builder()
    									.accountId(Utils.getAccountId(exist.get().getRoleArn()))
    									.region(exist.get().getRegion())
    									.build());
    		} else {
    			attackAwsDetailBo.get().setAccountId(Utils.getAccountId(attackAwsDetailBo.get().getRoleArn()));
    		}
    	}
    	
    	return attackAwsDetailBo;
    }

	@Override
	public void addAwsDetails(AttackAwsDetailBo customerAwsDetail) throws Exception {
		
		AttackAwsDetailBo existing = findByUserId(customerAwsDetail.getUser()).orElseThrow(() -> new EntityNotFound(ResponseCode.INVALID_USER_ID.getMessage()));
		
		if(existing.getAccountId() != Utils.getAccountId(customerAwsDetail.getRoleArn()))
			throw new Exception("The AWS account you created the Chaos Profile is not the same with the AWS account mapped to this User.");
		
		logger.info("Received : {}",customerAwsDetail);
		awsDetailRepository.save(customerAwsDetail);
	}
}