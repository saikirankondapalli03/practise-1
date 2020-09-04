package com.educative.interviewrefersher.chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Example {
	/**
	 * Iterate through each line of input.
	 */
	public static void main(String[] args) throws IOException {
		InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
		BufferedReader in = new BufferedReader(reader);
		Map<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> exceptionMap = new HashMap<String, String>();
		exceptionMap.put("errorcode", "100");
		exceptionMap.put("severity", "High");
		map.put("IOException", exceptionMap);

		exceptionMap = new HashMap<String, String>();
		exceptionMap.put("errorcode", "110");
		exceptionMap.put("severity", "High");
		map.put("MemoryException", exceptionMap);

		exceptionMap = new HashMap<String, String>();
		exceptionMap.put("errorcode", "200");
		exceptionMap.put("severity", "Medium");
		map.put("ThreadAbortException", exceptionMap);

		exceptionMap = new HashMap<String, String>();
		exceptionMap.put("errorcode", "300");
		exceptionMap.put("severity", "Low");
		map.put("ResponseTimeoutException", exceptionMap);

		exceptionMap = new HashMap<String, String>();
		exceptionMap.put("errorcode", "301");
		exceptionMap.put("severity", "Low");
		map.put("ParameterException", exceptionMap);
		String key = "MemoryException";
		String comment = "you are good to go";

		Map<String, String> ecode_sevrity = map.get(key);
		String result = ecode_sevrity.get("severity") + "|" + ecode_sevrity.get("errorcode") + "|" + key + "|"
				+ comment;
		System.out.println(result);
	}
}