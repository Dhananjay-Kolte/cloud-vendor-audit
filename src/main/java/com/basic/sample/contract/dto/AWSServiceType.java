package com.basic.sample.contract.dto;

public enum AWSServiceType {

    NETWORK_ACL("Network ACL"),
    IAM("IAM"),
    S3("S3"),
    KMS("KMS"),
    CONFIG("Config"),
    CLOUDTRAIL("CloudTrail"),
    EC2("EC2"),
    UNMAPPED("UNMAPPED"),
    GUARDDUTY("GuardDuty"),
    RDS("RDS"),
    LAMBDA("Lambda"),
    ELASTICSEARCH("Elasticsearch"),
    DYNAMODB("DynamoDB"),
    SQS("SQS"),
    EKS("EKS"),
    APIGATEWAY("API Gateway"),
    SAGEMAKER("SageMaker"),
    CLOUDFRONT("CloudFront"),
    ELASTICLOADBALANCER("ELB"),
    WAF("WAF"),
    ECSCLUSTER("ECS"),
    SIMPLE_EMAIL_SERVICE("SES"),
    DOCUMENT_DB("DocumentDB"),
    ELASTIC_CONTAINER_REGISTRY("ECR"),
    SECRET_MANAGER("Secrets Manager"),
    REDSHIFT("Redshift"),
    BEANSTALK("Elastic BeanStalk");

    private String desc ;

    private AWSServiceType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
