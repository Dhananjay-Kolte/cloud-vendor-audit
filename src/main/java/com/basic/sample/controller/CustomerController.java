package com.basic.sample.controller;

import java.util.Arrays;

import javax.validation.Valid;

import com.basic.sample.contract.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.basic.sample.dto.ResponseCode;
import com.basic.sample.model.AttackAwsDetailBo;
import com.basic.sample.service.CustomerAwsDetailService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	@Autowired
	protected CustomerAwsDetailService customerAwsDetailService;
	
    @GetMapping("/aws-details/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AttackAwsDetailBo> getAwsDetails(@PathVariable Long userId) {
        return ApiResponse.<AttackAwsDetailBo>builder()
                .responseObject(customerAwsDetailService.findByUserId(userId).orElse(null))
                .successMessage(ResponseCode.AWS_DETAILS.getMessage()).build();
    }

    @PostMapping("/aws-details")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> addAwsDetails(@Valid @RequestBody AttackAwsDetailBo customerAwsDetail) {
    	try {
			customerAwsDetailService.addAwsDetails(customerAwsDetail);
			return ApiResponse.<String>builder()
	                .successMessage(ResponseCode.AWS_DETAILS.getMessage()).build();
		} catch (Exception e) {
			return ApiResponse.<String>builder()
					.hasError(true)
					.errors(Arrays.asList(e.getMessage()))
					.build();
		}
    }
}