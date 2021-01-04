package com.educative.multithreading.chapter7.practise.blockingqueue.countingsemaphore;

class CountingSemaphore {
	int usedPermits = 0;
	int maxCount;

	public CountingSemaphore(int count) {
		this.maxCount = count;
	}

	public CountingSemaphore(int count, int initialPermits) {
		this.maxCount = count;
		this.usedPermits = this.maxCount - initialPermits;
	}

	public synchronized void acquire() throws InterruptedException {
		System.out.println("Acquire Counting Semaphore => " + Thread.currentThread().getName());
		while (usedPermits == maxCount)
			wait();
		notify();
		usedPermits++;
	}

	public synchronized void release() throws InterruptedException {
		System.out.println("Release Counting Semaphore => " + Thread.currentThread().getName());
		while (usedPermits == 0)
			wait();
		usedPermits--;
		notify();
	}
}