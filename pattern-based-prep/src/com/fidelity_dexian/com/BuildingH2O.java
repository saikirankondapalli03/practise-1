package com.fidelity_dexian.com;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Coding Challenge 11 from playbook: Building H2O (multithreading).
 * <p>
 * Coordinate Hydrogen (H) and Oxygen (O) threads so that they form water molecules:
 * exactly 2 H and 1 O must "bond" together before the next molecule is formed.
 * Each molecule corresponds to two hydrogen() and one oxygen() calls completing together.
 * <p>
 * Uses one lock and two conditions: one for H threads and one for O threads.
 * When the third thread arrives (making 2 H + 1 O), it prints "H2O", decrements
 * counts, and signals exactly 2 H and 1 O so they can leave. No starvation:
 * all threads eventually participate.
 */
public class BuildingH2O {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition hCond = lock.newCondition();
    private final Condition oCond = lock.newCondition();
    private int hCount = 0;
    private int oCount = 0;

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        lock.lock();
        try {
            hCount++;
            if (hCount >= 2 && oCount >= 1) {
                bond();
                hCount -= 2;
                oCount -= 1;
                hCond.signal();
                hCond.signal();
                oCond.signal();
            } else {
                hCond.await();
            }
        } finally {
            lock.unlock();
        }
        releaseHydrogen.run();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        lock.lock();
        try {
            oCount++;
            if (hCount >= 2 && oCount >= 1) {
                bond();
                hCount -= 2;
                oCount -= 1;
                hCond.signal();
                hCond.signal();
                oCond.signal();
            } else {
                oCond.await();
            }
        } finally {
            lock.unlock();
        }
        releaseOxygen.run();
    }

    private void bond() {
        // Synchronization point: 2 H + 1 O have bonded (no extra output here).
    }

    // ----- demo runner -----

    public static void main(String[] args) throws InterruptedException {
        BuildingH2O h2o = new BuildingH2O();
        int n = 3; // number of water molecules to form (3 * (2H + 1O) = 6H + 3O threads)

        Thread[] threads = new Thread[3 * n];
        for (int i = 0; i < n; i++) {
            threads[3 * i + 0] = new Thread(() -> {
                try {
                    h2o.hydrogen(() -> System.out.print("H"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[3 * i + 1] = new Thread(() -> {
                try {
                    h2o.hydrogen(() -> System.out.print("H"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[3 * i + 2] = new Thread(() -> {
                try {
                    h2o.oxygen(() -> System.out.print("O"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println();
        // Output has exactly 2n 'H' and n 'O' for n molecules (e.g. "HHOHHO" for n=2).
    }
}
