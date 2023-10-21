package com.basic.sample.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.basic.sample.contract.dto.AWSServiceType;

public enum AttackType {

	IAM_CREATE_USER(false, AWSServiceType.IAM,
    		"New IAM User", "T1136", 
    		"With a sufficient level of access, created accounts may be used to establish secondary credentialed access that does not require persistent remote access tools to be deployed on the system.", 
    		"Adversaries may create an account to maintain access to victim systems.",
    		"", 
    		Arrays.asList(AttackCategory.AUTHENTICATION_ATTACK)),
    
    IAM_CREATE_USER_ACCESS_KEY(false, AWSServiceType.IAM, 
    		"IAM User backdoor", 
    		"T1098", 
    		"Having compromised a bucket and gained access, attackers could exfiltrate the contents of the bucket.", 
    		"Adversaries may manipulate accounts to maintain access to victim systems. Account manipulation may consist of any action that preserves adversary access to a compromised account, such as modifying credentials or permission groups.",
    		"", 
    		Arrays.asList(AttackCategory.AUTHENTICATION_ATTACK)),
    
    S3_BUCKET_DISABLE_LOGGING(false, AWSServiceType.S3, 
    		"Disable S3 Bucket Logging", 
    		"T1562.008", 
    		"", 
    		"",
    		"",
    		Arrays.asList(AttackCategory.STORAGE_ATTACK)),
    
    S3_BUCKET_PUBLIC(false, AWSServiceType.S3, 
    		"Public S3  Bucket ", 
    		"T1619", 
    		"", 
    		"",
    		"",
    		Arrays.asList(AttackCategory.STORAGE_ATTACK)),
    
    S3_BUCKET_MFA_DELETE(false, AWSServiceType.S3, 
    		"Disable S3 Bucket MFA", 
    		"T1562", 
    		"The ability to identify or react to malicious actions is impaired.", 
    		"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive, detective and auditing mechanisms.",
    		"",
    		Arrays.asList(AttackCategory.STORAGE_ATTACK)),
    
    EC2_DEFAULT_VPC_SECURITY_GROUP(false, AWSServiceType.EC2, 
    		"Manipulate security group default VPC", 
    		"T1098", 
    		"Manipulation of the security group may expose the EC instances within the Security group to unauthorized access.", 
    		"Adversaries may expose alter the configuration of the security group in order to establish remote access or a backdoor.",
    		"", 
    		Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK )),
    
    EC2_RANDOM_VPC_SECURITY_GROUP(false, AWSServiceType.EC2, 
    		"Manipulate security group random VPC", 
    		"T1098", 
    		"Manipulation of the security group may expose the EC instances within the Security group to unauthorized access.", 
    		"Adversaries may expose alter the configuration of the security group in order to establish remote access or a backdoor",
    		"",
    		Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK)),
    
    EC2_VPC_DISABLE_FLOW_LOGS(false, AWSServiceType.EC2, 
			"Disable VPC Flow Logs", 
			"T1562", 
			"Manipulation of the Config might hide the evidence that an attack ever occurred thereby making it difficult to investigate", 
			"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive mechanisms.",
			"",
			Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK)),
    
    EC2_CREATE_INSTANCE(false, AWSServiceType.EC2, 
    		"Malicious EC2 Instance", 
    		"T1578.002", 
    		"Manipulation of the security group may expose the EC instances within the Security group to unauthorized access.", 
    		"An adversary may create a new instance or virtual machine (VM) within the compute service for several reasons e.g. to evade defenses, as a foothold for further attacks and bitcoin-mining.",
    		"",
    		Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK)),
    
    CLOUD_TRAIL_DISABLE_LOGGING(false, AWSServiceType.CLOUDTRAIL, 
    		"Disable Cloudtrail Logging", 
    		"T1562", 
    		"Manipulation of the security group may expose the EC instances within the Security group to unauthorized access.", 
    		"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive mechanisms.",
    		"",
    		Arrays.asList(AttackCategory.GENERAL_ATTACK)),

	RDS_SECURITY_GROUP(false, AWSServiceType.RDS, 
			"Expose an RDS Instance to the public", 
			"T1562.007", 
			"Manipulation of the security group may expose the EC instances within the Security group to unauthorized access.", 
			"Cloud environments typically utilize restrictive security groups and firewall rules that only allow network activity from trusted IP addresses via expected ports and protocols. An adversary may introduce new firewall rules or policies to allow access into a victim cloud environment.",
			"", 
			Arrays.asList(AttackCategory.DATABASE_ATTACK)),
	
	CONFIG_RECORDER_DISABLE(false, AWSServiceType.CONFIG, 
			"Disable AWS Config Recorder", 
			"T1562", 
			"Manipulation of the Config might hide the evidence that an attack ever occurred thereby making it difficult to investigate", 
			"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive mechanisms.",
			"",
			Arrays.asList(AttackCategory.GENERAL_ATTACK)),
	
	LAMBDA_DETACH_FROM_VPC(false, AWSServiceType.LAMBDA, 
			"Lambda public exposure", 
			"T1562", 
			"Manipulation of the Config might hide the evidence that an attack ever occurred thereby making it difficult to investigate", 
			"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive mechanisms.",
			"",
			Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK)),
	
	LAMBDA_ATTACH_MALICIOUS_ROLE(false, AWSServiceType.LAMBDA, 
			"Malicious Lambda access", 
			"T1562", 
			"Manipulation of the Config might hide the evidence that an attack ever occurred thereby making it difficult to investigate", 
			"Adversaries may maliciously modify components of a victim environment in order to hinder or disable defensive mechanisms.",
			"", 
			Arrays.asList(AttackCategory.COMPUTE_ATTACK, AttackCategory.DATABASE_ATTACK)),
	
	S3_BUCKET_UNAUTHORIZED_ACCESS(true, "Grant S3 bucket access to fake IAM User", 
			Arrays.asList(IAM_CREATE_USER, IAM_CREATE_USER_ACCESS_KEY), 
    		"Having compromised a bucket and gained access, attackers could exfiltrate the contents of the bucket.", 
    		"Adversaries may compromise an S3 bucket to gain access to sensitive information.",
    		"", 
    		Arrays.asList(AttackCategory.STORAGE_ATTACK)),
    
	S3_BUCKET_RANSOMWARE(true, "Ransomware S3 Bucket Attack Scenario", 
			Arrays.asList(S3_BUCKET_DISABLE_LOGGING, S3_BUCKET_MFA_DELETE), 
    		"Having compromised a bucket and gained access, attackers could exfiltrate the contents of the bucket.", 
    		"Adversaries may compromise an S3 bucket to gain access to sensitive information.",
    		"", 
    		Arrays.asList(AttackCategory.STORAGE_ATTACK));
	
	
	private boolean isScenario;
    private String name;
    private String attNckId;
    private String impact;
    private String description;
    private AWSServiceType service;
    private String additionalInfo;
	private List<AttackType> composition;
	private List<AttackCategory> categories;
	
	AttackType(boolean isScenario, String name, List<AttackType> composition, String impact, String description, String additionalInfo, List<AttackCategory> categories) {
        this.isScenario = isScenario;
		this.impact = impact;
        this.description = description;
        this.name = name;
        this.additionalInfo = additionalInfo;
        this.composition = composition;
        this.categories = categories;
    }
	
    AttackType(boolean isScenario, AWSServiceType service, String name, String attNckId, String impact, String description, String additionalInfo, List<AttackCategory> categories) {
    	this.isScenario = isScenario;
    	this.impact = impact;
        this.description = description;
        this.attNckId = attNckId;
        this.name = name;
        this.service = service;
        this.additionalInfo = additionalInfo;
        this.categories = categories;
    }

    public String getAttNckId() {
        return attNckId;
    }

    public String getImpact() {
        return this.impact;
    }

    public String getDescription() {
        return this.description;
    }
    
    public List<String> getCategoriesMap() {
    	return categories.stream().map(AttackCategory::getMessage).collect(Collectors.toList());
	}

    public String getName() {
        return name;
    }

	public AWSServiceType getService() {
		return service;
	}
	
	public String getAdditionalInfo() {
        return additionalInfo;
    }

	public List<AttackType> getComposition() {
		return composition;
	}
	
	public static List<Map<String, Object>> getAttackTypes() {
		return Arrays.stream(AttackType.values())
		.filter(e -> !e.isScenario)
		.map( e -> Map.of( "attackName", e.getName(), "key", e.name(), "attNckId", e.getAttNckId(), "impact", e.getImpact(), "description", e.getDescription(), 
				"service", e.getService().name(), "additionalInfo", e.getAdditionalInfo(), "categories", e.getCategoriesMap()))
        .collect(Collectors.toList());
	}
	
	public static List<Map<String, Object>> getAttackScenarios() {
		return Arrays.stream(AttackType.values())
		.filter(e -> e.isScenario)
		.map( e -> Map.of( "scenarioName", e.getName(), "key", e.name(), "composition", e.getComposition(), "impact", e.getImpact(), "description", e.getDescription(), "additionalInfo", e.getAdditionalInfo(), "category", "Managed"))
        .collect(Collectors.toList());
	}
	

}