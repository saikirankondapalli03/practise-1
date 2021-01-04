package com.educative.multithreading.chapter5.monitor.semaphore;

import java.util.concurrent.Semaphore;
//use of semaphores(for both locking and signalling)
class CorrectSemaphoreExample {
	public static void main(String args[]) throws InterruptedException {
		CorrectSemaphoreExample.example();
	}

	public static void example() throws InterruptedException {

		final Semaphore semaphore = new Semaphore(1);

		Thread badThread = new Thread(new Runnable() {

			public void run() {

				while (true) {

					try {
						semaphore.acquire();
						try {
							throw new RuntimeException("");
						} catch (Exception e) {
							// handle any program logic exception and exit the function
							return;
						} finally {
							System.out.println("Bad thread releasing semahore.");
							semaphore.release();
						}

					} catch (InterruptedException ie) {
						// handle thread interruption
					}
				}
			}
		});

		badThread.start();

		// Wait for the bad thread to go belly-up
		Thread.sleep(1000);

		final Thread goodThread = new Thread(new Runnable() {

			public void run() {
				System.out.println("Good thread patiently waiting to be signalled.");
				try {
					semaphore.acquire();
				} catch (InterruptedException ie) {
					// handle thread interruption
				}
			}
		});

		goodThread.start();
		badThread.join();
		goodThread.join();
		System.out.println("Exiting Program");
	}
}

/*
 * 
 * * A thread can also wake up without being notified, interrupted, or
* timing out, a so-called <i>spurious wakeup</i>.  While this will rarely
* occur in practice, applications must guard against it by testing for
* the condition that should have caused the thread to be awakened and
* continuing to wait if the condition is not satisfied.  In other words,
*
* 
*     synchronized (obj) {
*         while (condition does not hold)
*             obj.wait(timeout);
*         ... // Perform action appropriate to condition
*     }
*/
 