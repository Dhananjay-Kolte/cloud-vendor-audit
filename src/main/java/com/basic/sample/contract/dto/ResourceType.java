package com.basic.sample.contract.dto;

import lombok.Getter;

@Getter
public enum ResourceType {

    BUCKET("Bucket"),
    USER("User"),
    AWS_ACCOUNT("AWS Account"),
    KEY("Key"),
    NETWORK_ACL("Network ACL"),
    TRAIL("Trail"),
    DB_INSTANCE("DB Instance"),
    SECURITY_GROUP("Security Group"),
    FUNCTION("Function"),
    MYSQL("MySQL"),
    POSTGRES("Postgres"),
    AURORA("Aurora"),
    MARIADB("MariaDB"),
    ORACLE("Oracle"),
    SQL_SERVER("SQL Server"),
    DATABASE("Database"),
    EC2_INSTANCE("EC2 Instance"),
    DYNAMO_DB("DynamoDB"),
    SQS_QUEUE("Sqs Queue"),
    EKS_CLUSTER("EKS cluster"),
    GROUP("Group"),
    IAM_ROLE("IAM Role"),
    RESTAPI("Rest API"),
    SAGE_MAKER_NOTEBOOK("Sage Maker Notebook"),
    DISTRIBUTION("Distribution"),
    ELASTICLOADBALANCER("ElasticLoadBalancer"),
    ECSCLUSTER("ECSCluster"),
    WEBACL("WebAcl"),
    SIMPLE_EMAIL_SERVICE("SimpleEmailService"),
    ELASTIC_CONTAINER_REGISTRY("ElasticContainerService"),
    CONFIG("Config"),
    VPC("Virtual Private Cloud");

    private String desc;

    private ResourceType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
