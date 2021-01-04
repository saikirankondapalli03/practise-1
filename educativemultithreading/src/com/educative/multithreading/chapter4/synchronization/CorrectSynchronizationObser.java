package com.educative.multithreading.chapter4.synchronization;

class CorrectSynchronizationObser {
	public static void main(String args[]) throws InterruptedException {
		CorrectSynchronizationObser.runExample();
	}

	Boolean flag = new Boolean(true);

	public void example() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				synchronized (this) { // if keep this instead of CorrectSynchronizationObser.this, deadlock happens
					try {
						while (flag) {
							System.out.println("First thread about to sleep");
							System.out.println("Woke up and about to invoke wait()");
							wait(); // waiting, not running
						}
					} catch (InterruptedException ie) {

					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				synchronized (this) {
					flag=false;
					notify();
					System.out.println("Boolean assignment done.");
				}
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	public static void runExample() throws InterruptedException {
		CorrectSynchronizationObser incorrectSynchronization = new CorrectSynchronizationObser();
		incorrectSynchronization.example();
	}
}
