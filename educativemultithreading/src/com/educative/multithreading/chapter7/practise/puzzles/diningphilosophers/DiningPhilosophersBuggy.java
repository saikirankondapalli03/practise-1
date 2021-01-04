package com.educative.multithreading.chapter7.practise.puzzles.diningphilosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

/*
 * 
 * If you run the code eventually, 
 * it'll at some point end up in a deadlock. Realize if all the philosophers simultaneously grab their left fork,
 * none would be able to eat. we discuss a couple of ways to avoid this deadlock and arrive at the final solution.
 */
public class DiningPhilosophersBuggy {

    private static Random random = new Random(System.currentTimeMillis());

    private Semaphore[] forks = new Semaphore[5];

    public DiningPhilosophersBuggy() {
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

    void eat(int id) throws InterruptedException {

        // acquire the left fork first
        forks[id].acquire();

        // acquire the right fork second
        forks[(id + 4) % 5].acquire();

        // eat to your heart's content
        System.out.println("Philosopher " + id + " is eating");

        // release forks for others to use
        forks[id].release();
        forks[(id + 4) % 5].release();

    }
}