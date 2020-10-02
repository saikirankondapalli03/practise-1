package com.educative.multithreading.chapter4.synchronization;

class EmployeeSyncWObjectLock {
	// shared variable
	private String name;
	private Object lock = new Object();

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
		// Using a different object to synchronize on
		synchronized (lock) {
			return this.name;
		}
	}
}