package com.educative.multithreading.chapter7.practise.puzzles.barrier;

class BarrierFaulty1 {
	public static void main(String args[]) throws Exception {
		BarrierFaulty1.runTest();
	}

	int count = 0;
	int totalThreads;
	int released = 0;
	public BarrierFaulty1(int totalThreads) {
		this.totalThreads = totalThreads;
	}

	public synchronized void await() throws InterruptedException {
		 // increment the counter whenever a thread arrives at the barrier.
		count++;
		
		/*
		 * problem with count++
		 * 
		 * If thread t3 attempts to invoke await() immediately after exiting it and 
		 * is also granted the monitor before threads t1 or t2 get a chance to acquire the monitor then the count variable will be incremented to 4.


		 * 
		 * 
		 */

		if (count == totalThreads) {
			 // wake up all the threds.
			notifyAll();
			// remember to reset count so that barrier can be reused
			released = totalThreads;
		} else {
			
			/*
			 * The while loop introduces another problem. When the last thread does a
			 * notifyAll() it also resets the count to 0, which means the threads that are
			 * legitimately woken up will always be stuck in the while loop because count is
			 * immediately set to zero. What we really want is not to reset the count
			 * variable to zero until all the threads escape the while condition when count
			 * becomes totalThreads. Below is the improved version:
			 * 
			 */
			// wait till all threads reach barrier
			//A thread that wakes up spuriously should go back to sleep if the count is less than the total number of threads. 
		         while (count < totalThreads)
			             wait();
		}
		released--;
		if (released == 0) count = 0;
	}

	public static void runTest() throws InterruptedException {
		final BarrierFaulty1 barrier = new BarrierFaulty1(3);

		Thread p1 = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("Thread 1");
					barrier.await();
					System.out.println("Thread 1");
					barrier.await();
					System.out.println("Thread 1");
					barrier.await();
				} catch (InterruptedException ie) {
				}
			}
		});

		Thread p2 = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(500);
					System.out.println("Thread 2");
					barrier.await();
					Thread.sleep(500);
					System.out.println("Thread 2");
					barrier.await();
					Thread.sleep(500);
					System.out.println("Thread 2");
					barrier.await();
				} catch (InterruptedException ie) {
				}
			}
		});

		Thread p3 = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1500);
					System.out.println("Thread 3");
					barrier.await();
					Thread.sleep(1500);
					System.out.println("Thread 3");
					barrier.await();
					Thread.sleep(1500);
					System.out.println("Thread 3");
					barrier.await();
				} catch (InterruptedException ie) {
				}
			}
		});

		p1.start();
		p2.start();
		p3.start();

		p1.join();
		p2.join();
		p3.join();
	}
}
