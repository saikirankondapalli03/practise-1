package com.educative.multithreading.chapter5.monitor.semaphore;

import java.util.concurrent.Semaphore;
//use of semaphores(for both locking and signalling)
class FixedMissedSignalExample {

	public static void example() throws InterruptedException {

		final Semaphore semaphore = new Semaphore(1);

		Thread signaller = new Thread(new Runnable() {

			public void run() {
				semaphore.release();
				System.out.println("Sent signal");
			}
		});

		Thread waiter = new Thread(new Runnable() {

			public void run() {
				try {
					semaphore.acquire();
					System.out.println("Received signal");
				} catch (InterruptedException ie) {
					// handle interruption
				}
			}
		});

				
		signaller.start();
		signaller.join();
		Thread.sleep(5000);
		waiter.start();
		waiter.join();
		
		System.out.println("Program Exiting.");
	}

	public static void main(String args[]) throws InterruptedException {
		FixedMissedSignalExample.example();
	}
}
