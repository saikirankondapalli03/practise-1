package com.educative.multithreading.chapter7.practise.puzzles.uber;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;


class UberSeatingProblem {
	public static void main(String args[]) throws InterruptedException {
		UberSeatingProblem.runTest();
	}
	//every small java util concurrency collection has a concept of "State". the behavior of a state depends on that collection and number of threads accessing that collection
	
	
	/*
	 * 
	 * To make up an allowed combination of riders, 
	 * we'll need to keep a count of Democrats and Republicans who have requested for rides. We create two variables for this purpose
	 * and modify them within a lock/mutex. 
	 * In this problem, we'll use the ReentrantLock class provided by java's util.concurrent package when manipulating counts for democrats and republicans.
	 */
	
	private int republicans = 0;
	private int democrats = 0;
	
	
	private Semaphore demsWaiting = new Semaphore(0);
	private Semaphore repubsWaiting = new Semaphore(0);
	
	/*
	 * 
	 * we'll also need a barrier where all the four threads, that have been selected for the Uber ride arrive at, before riding away.
	 * This is analogous to the four riders being seated in the car and the doors being shut.
	 */
	CyclicBarrier barrier = new CyclicBarrier(4);
	ReentrantLock lock = new ReentrantLock();

	
	//Once the doors are shut, one of the riders has to tell the driver to drive which we simulate with a call to the drive() method.
	void drive() {
		System.out.println("Uber Ride on Its wayyyy... with ride leader " + Thread.currentThread().getName());
		System.out.flush();
	}

	void seatDemocrat() throws InterruptedException, BrokenBarrierException {
		boolean rideLeader = false;
		lock.lock();
		democrats++;
		if (democrats == 4) {
			//1. If there are already 3 waiting democrats, then we signal the demsWaiting three times
			//   so that all these four democrats can ride together in the next Uber ride.
			
			// Seat all the democrats in the Uber ride.
			demsWaiting.release(3); // release those who are already in waiting.(i.e 3)
			democrats -= 4;
			rideLeader = true;
			System.out.println("all democrats");
		} else if (democrats == 2 && republicans >= 2) {
			// Seat 2 democrats & 2 republicans
			demsWaiting.release(1);
			repubsWaiting.release(2);
			rideLeader = true;
			democrats -= 2;
			republicans -= 2;
		} else {
			lock.unlock();
			/*
			 * Imagine the first thread is a democrat and invokes seatDemocrat().
			 * Since there's no other rider available, it should be put to wait. We can use a semaphore to make this thread wait. 
			 * 
			 */
			demsWaiting.acquire();
		}
		seated();
		barrier.await();
		if (rideLeader == true) {
			drive();
			lock.unlock();
		}
	}

	void seated() {
		System.out.println(Thread.currentThread().getName() + "  seated");
		System.out.flush();
	}

	void seatRepublican() throws InterruptedException, BrokenBarrierException {
		boolean rideLeader = false;
		lock.lock();
		republicans++;
		if (republicans == 4) {
			// Seat all the republicans in the Uber ride.
			repubsWaiting.release(3);
			rideLeader = true;
			republicans -= 4;
		} else if (republicans == 2 && democrats >= 2) {
			// Seat 2 democrats & 2 republicans
			repubsWaiting.release(1);
			demsWaiting.release(2);
			rideLeader = true;
			republicans -= 2;
			democrats -= 2;
		} else {
			lock.unlock();
			repubsWaiting.acquire();
		}
		seated();
		barrier.await();
		if (rideLeader) {
			drive();
			lock.unlock();
		}
	}

	public static void runTest() throws InterruptedException {

		final UberSeatingProblem uberSeatingProblem = new UberSeatingProblem();
		Set<Thread> allThreads = new HashSet<Thread>();

		for (int i = 0; i < 10; i++) {

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						uberSeatingProblem.seatDemocrat();
					} catch (InterruptedException ie) {
						System.out.println("We have a problem");

					} catch (BrokenBarrierException bbe) {
						System.out.println("We have a problem");
					}

				}
			});
			thread.setName("Democrat_" + (i + 1));
			allThreads.add(thread);

			Thread.sleep(50);
		}

		for (int i = 0; i < 14; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						uberSeatingProblem.seatRepublican();
					} catch (InterruptedException ie) {
						System.out.println("We have a problem");

					} catch (BrokenBarrierException bbe) {
						System.out.println("We have a problem");
					}
				}
			});
			thread.setName("Republican_" + (i + 1));
			allThreads.add(thread);
			Thread.sleep(20);
		}

		for (Thread t : allThreads) {
			t.start();
		}

		for (Thread t : allThreads) {
			t.join();
		}
	}
}
