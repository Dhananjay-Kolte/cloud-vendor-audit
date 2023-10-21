package com.basic.sample.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

	public static final String sample = "sample";
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static List<Integer> randomInt(int max, int count) {
		List<Integer> list = new ArrayList<>();
		if (max > 0) {
			int[] indexes = (new Random().ints(count, 0, max)).toArray();
			for (int i : indexes) {
				list.add(i);
			}
		}
		return list;
	}
	
	public static long getAccountId(String roleArn) {
    	if(roleArn != null) {
    		return roleArn.split(":").length >= 5 ? Long.valueOf(roleArn.split(":")[4]) : 0L;
    	}
    	
    	return 0L;
    }
}