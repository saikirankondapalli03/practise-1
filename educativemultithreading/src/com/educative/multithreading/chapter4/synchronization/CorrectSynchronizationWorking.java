package com.educative.multithreading.chapter4.synchronization;

class CorrectSynchronizationWorking {
	public static void main(String args[]) throws InterruptedException {
		CorrectSynchronizationWorking.runExample();
	}

	Boolean flag = new Boolean(true);

	public void example() throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				synchronized (CorrectSynchronizationWorking.this) {
					try {
						while (flag) {
							System.out.println("First thread about to sleep");
							System.out.println("Woke up and about to invoke wait()");
							CorrectSynchronizationWorking.this.wait(); // waiting, not running
						}
					} catch (InterruptedException ie) {

					}
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				synchronized (CorrectSynchronizationWorking.this) {
					flag=false;
					CorrectSynchronizationWorking.this.notify();
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
		CorrectSynchronizationWorking incorrectSynchronization = new CorrectSynchronizationWorking();
		incorrectSynchronization.example();
	}
}
