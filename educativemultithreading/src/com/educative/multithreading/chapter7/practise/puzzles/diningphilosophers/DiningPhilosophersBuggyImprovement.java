package com.educative.multithreading.chapter7.practise.puzzles.diningphilosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

//Limiting philosophers about to eat

/*
 * Convince yourself that with five forks and four philosophers deadlock is impossible, since at any point in time, 
 * even if each philosopher grabs one fork, there will still be one fork left that can be acquired by one of the philosophers to eat.
 * Implementing this solution requires us to introduce another semaphore with a permit of 4 which guards the logic for lifting/grabbing of the forks by the philosophers
 * 
 * 
 * 
 */
public class DiningPhilosophersBuggyImprovement {

	public static void main(String args[]) throws InterruptedException {
		DiningPhilosophersBuggyImprovement.runTest();
	}

	private static Random random = new Random(System.currentTimeMillis());

	private Semaphore[] forks = new Semaphore[5];
	private Semaphore maxDiners = new Semaphore(4);

	public DiningPhilosophersBuggyImprovement() {
		forks[0] = new Semaphore(1);
		forks[1] = new Semaphore(1);
		forks[2] = new Semaphore(1);
		forks[3] = new Semaphore(1);
		forks[4] = new Semaphore(1);
	}

	public void lifecycleOfPhilosopher(int id) throws InterruptedException {

		while (true) {
			contemplate();
			eat(id);
		}
	}

	void contemplate() throws InterruptedException {
		Thread.sleep(random.nextInt(500));
	}

	static void startPhilosoper(DiningPhilosophersBuggyImprovement dp, int id) {
		try {
			dp.lifecycleOfPhilosopher(id);
		} catch (InterruptedException ie) {

		}
	}

	void eat(int id) throws InterruptedException {
	    // maxDiners allows only 4 philosphers to attempt picking up forks.
		maxDiners.acquire();
		// acquire the left fork first
		forks[id].acquire();
		// acquire the right fork second
		forks[(id + 4) % 5].acquire();
		// eat to your heart's content
		System.out.println("Philosopher " + id + " is eating");
		// release forks for others to use
		forks[id].release();
		forks[(id + 4) % 5].release();
		maxDiners.release();
	}

	public static void runTest() throws InterruptedException {
		final DiningPhilosophersBuggyImprovement dp = new DiningPhilosophersBuggyImprovement();
		Thread p1 = new Thread(new Runnable() {
			public void run() {
				startPhilosoper(dp, 0);
			}
		});
		Thread p2 = new Thread(new Runnable() {
			public void run() {
				startPhilosoper(dp, 1);
			}
		});
		Thread p3 = new Thread(new Runnable() {
			public void run() {
				startPhilosoper(dp, 2);
			}
		});
		Thread p4 = new Thread(new Runnable() {
			public void run() {
				startPhilosoper(dp, 3);
			}
		});
		Thread p5 = new Thread(new Runnable() {
			public void run() {
				startPhilosoper(dp, 4);
			}
		});
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p1.join();
		p2.join();
		p3.join();
		p4.join();
		p5.join();
	}
}