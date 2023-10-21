package com.basic.sample.dto;

public enum AttackCategory {

	INITIAL_ACCESS("Initial Access"),
	COLLECTION("Collection"),
	EXFILTRATION("Exfiltration"),
	
	DATABASE_ATTACK("Database Attack"),
	AUTHENTICATION_ATTACK("Identity, Authentication and Authorization Attack"),
	STORAGE_ATTACK("Storage Attack"),
	COMPUTE_ATTACK("Compute Attack"),
	GENERAL_ATTACK("General Attack");
	
	private String message;
	
	AttackCategory(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
