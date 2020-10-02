package com.educative.multithreading.chapter3.deadlock;

class SingleResourceDeadLock {

	//NonReentrantLock
	public static void main(String args[]) throws Exception {
		NonReentrantLock nreLock = new NonReentrantLock();

		// First locking would be successful
		nreLock.lock();
		
		System.out.println("Acquired first lock");
		System.out.println("Trying to acquire second lock");

		nreLock.lock();

		// Second locking results in a self deadlock
		System.out.println("Acquired second lock");
	}
}

class NonReentrantLock {

	boolean isLocked=true;

	public NonReentrantLock() {
		isLocked = false;
	}

	public synchronized void lock() throws InterruptedException {

		while (isLocked) {
			wait();
		}
		isLocked = true;
	}

	public synchronized void unlock() {
		isLocked = false;
		notify();
	}
}