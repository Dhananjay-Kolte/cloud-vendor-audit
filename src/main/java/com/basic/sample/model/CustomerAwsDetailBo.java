package com.basic.sample.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.amazonaws.regions.Regions;

import com.basic.sample.converter.RegionsConverter;
import lombok.Data;


@Table(name = "customer_aws_detail")
@Entity
@Data
public class CustomerAwsDetailBo extends BaseBo {
	
	private static final long serialVersionUID = 9152801732195844788L;
	
	@Column(name = "account_name")
    private String accountName;
    
	@Column(name = "role_arn")
	@NotEmpty(message = "Role Arn is required")
    @Size(min = 20,message = "Role Arn must be greater or equal to 20")
    private String roleArn;
    
	@Column(name = "external_id")
	@NotEmpty(message = "User ID is required")
    private String externalId;
	
    @Column(name = "region")
    @NotEmpty(message = "User ID is required")
    @Convert(converter = RegionsConverter.class)
    private Regions region;
    
    @Column
    private String description;
    
    @Column(name = "user_id")
    @NotEmpty(message = "User ID is required")
    private Long user;

}
