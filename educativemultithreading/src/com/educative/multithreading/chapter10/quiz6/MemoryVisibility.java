package com.educative.multithreading.chapter10.quiz6;

public class MemoryVisibility {

    int myvalue = 2;
    boolean done = false;

    void thread1() throws InterruptedException {

        synchronized (this) {
            while (!done)
                this.wait();
            System.out.println(myvalue);
        }
    }

    void thread2() {
    		//if you remove the synchronized block below, then 
    	
    	/*
    	This is a prime example of insufficient synchronization. The reader thread may still see stale values of the shared variables as they may be updated by writer but only in a register or cache.
*/

        synchronized (this) {
            myvalue = 5;
            done = true;
            this.notify();
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
		MemoryVisibility mv = new MemoryVisibility();

		Thread thread1 = new Thread(() -> {
			try {
				mv.thread1();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Thread.sleep(5000);
		Thread thread2 = new Thread(() -> {
			mv.thread2();
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
	}
}