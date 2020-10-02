package com.educative.tc.chapter4;

class Item {
	public Item(String key, String value, int priority, int expireAfter) {
		this.preference = priority;
		this.expireAfter = expireAfter;
		this.key = key;
		this.value = value;
	}

	int preference;
	int expireAfter;
	String key;
	String value;
}