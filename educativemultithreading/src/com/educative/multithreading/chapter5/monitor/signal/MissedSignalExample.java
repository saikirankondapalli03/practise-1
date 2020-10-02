package com.educative.multithreading.chapter5.monitor.signal;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MissedSignalExample {
	public static void example() throws InterruptedException {
		final ReentrantLock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		Thread signaller = new Thread(new Runnable() {
			public void run() {
				lock.lock();
				condition.signal();
				System.out.println("Sent signal");
				lock.unlock();
			}
		});
		Thread waiter = new Thread(new Runnable() {
			public void run() {
				lock.lock();
				try {
					condition.await();
					System.out.println("Received signal");
				} catch (InterruptedException ie) {
					// handle interruption
				}
				lock.unlock();
			}
		});
		signaller.start();
		signaller.join();
		waiter.start();
		waiter.join();
		System.out.println("Program Exiting.");
	}
	
	private volatile boolean usedData = true;//mutex for data
	private final Lock lock = new ReentrantLock();
	private final Condition isEmpty = lock.newCondition();
	private final Condition isFull = lock.newCondition();
	public int data=0;
	public void setData(int data) throws InterruptedException {
	    lock.lock();
	    try {
	        while(!usedData) {//wait for data to be used
	            isEmpty.await();
	        }
	        this.data = data;
	        isFull.signal();//broadcast that the data is now full.
	        usedData = false;//tell others I created new data.          
	    }finally {
	        lock.unlock();//interrupt or not, release lock
	    }       
	}
	
	//https://medium.com/@tarunjain07/notes-mutex-vs-semaphore-ce65767a9115

	public void getData() throws InterruptedException{
	    lock.lock();
	    try {
	        while(usedData) {//usedData is lingo for empty
	            isFull.await();
	        }
	        isEmpty.signal();//tell the producers to produce some more.
	        usedData = true;//tell others I have used the data.
	    }finally {//interrupted or not, always release lock
	        lock.unlock();
	    }       
	}

	// https://stackoverflow.com/questions/10395571/condition-vs-wait-notify-mechanism
	public static void main(String args[]) throws InterruptedException {
		MissedSignalExample.example();
	}
}
