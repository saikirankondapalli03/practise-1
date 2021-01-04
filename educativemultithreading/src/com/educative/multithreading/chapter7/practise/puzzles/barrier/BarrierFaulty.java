package com.educative.multithreading.chapter7.practise.puzzles.barrier;

class BarrierFaulty {
	public static void main(String args[]) throws Exception {
		BarrierFaulty.runTest();
	}

	int count = 0;
	int totalThreads;

	public BarrierFaulty(int totalThreads) {
		this.totalThreads = totalThreads;
	}

	public synchronized void await() throws InterruptedException {
		count++;

		if (count == totalThreads) {
			notifyAll();
			count = 0;
		} else {
			/*
			 * 
			 * The previous code would have been hunky dory if we were guaranteed that no
			 * "spurious wake-ups" could ever occur. The wait() method invocation without the
			 * while loop is an error. We discussed in previous sections that wait() should
			 * always be used with a while loop that checks for a condition and if found
			 * false should make the thread wait again.
			 */
			wait();

			/*
			 * The while loop introduces another problem. When the last thread does a
			 * notifyAll() it also resets the count to 0, which means the threads that are
			 * legitimately woken up will always be stuck in the while loop because count is
			 * immediately set to zero. What we really want is not to reset the count
			 * variable to zero until all the threads escape the while condition when count
			 * becomes totalThreads. Below is the improved version:
			 * 
			 */
		}
	}

	public static void runTest() throws InterruptedException {
		final BarrierFaulty barrier = new BarrierFaulty(3);

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
