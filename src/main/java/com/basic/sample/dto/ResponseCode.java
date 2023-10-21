package com.basic.sample.dto;

public enum ResponseCode {

	INVALID_USER_ID("Invalid User ID."),
	ATTACK_STARTED("Attack Started."),
	ATTACK_STOPPED("Attack Stopped."),
	ATTACK_REPORT("Attack Report."),
	ATTACK_TYPE("Attack Types / Scenarios."),
	RECOVERY_REPORT("Recovery Report."),
	AWS_DETAILS("Customer AWS details saved successfully."),
	ATTACK_STATS("Attack Statistics"),
	ATTACK_SCHEDULE("Attack Schedule"),
	ATTACK_SCENARIO_CREATED("Attack Scenarion Created Successfully"),
	ATTACK_SCENARIO_UPDATED("Attack Scenarion Updated Successfully"),
	ATTACK_SCHEDULE_DELETED("Scheduled Attack Deleted Successfully"),
	ATTACK_RE_SCHEDULED("Scheduled Attack Updated Successfully"),
	SCHEDULED_ATTACK("Scheduled Attacks"), 
	ATTACK_SCENARIO_DELETED("Attack Scenarion Deleted Successfully"),
	ATTACK_SCENARIOS("Attack Scenarios"),
	ATTACK_SUMMARY("Attack Summary");
	
	private String message;
	
	ResponseCode(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}