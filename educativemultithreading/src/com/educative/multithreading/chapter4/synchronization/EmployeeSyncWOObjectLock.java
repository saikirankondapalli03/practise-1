package com.educative.multithreading.chapter4.synchronization;

class EmployeeSyncWOObjectLock {

	// shared variable
	private String name;

	// method is synchronize on 'this' object
	public synchronized void setName(String name) {
		this.name = name;
	}

	// also synchronized on the same object
	public synchronized void resetName() {

		this.name = "";
	}

	// equivalent of adding synchronized in method
	// definition
	public String getName() {
		synchronized (this) {
			return this.name;
		}
	}
}