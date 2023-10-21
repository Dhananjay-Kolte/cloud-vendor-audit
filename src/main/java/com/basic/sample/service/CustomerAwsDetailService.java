package com.basic.sample.service;

import java.util.Optional;

import com.basic.sample.model.AttackAwsDetailBo;


public interface CustomerAwsDetailService {

	Optional<AttackAwsDetailBo> findByUserId(Long id);
	void addAwsDetails(AttackAwsDetailBo customerAwsDetail) throws Exception;
}
