package com.educative.multithreading.chapter7.practise.blockingqueue.mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FaultyBlockingQueueWith2ThreadMutex<T> {
	T[] array;
	Lock lock = new ReentrantLock();
	int size = 0;
	int capacity;
	int head = 0;
	int tail = 0;

	@SuppressWarnings("unchecked")
	public FaultyBlockingQueueWith2ThreadMutex(int capacity) {
		// The casting results in a warning
		array = (T[]) new Object[capacity];
		this.capacity = capacity;
	}

	public T dequeue() {

		T item = null;

		while (size == 0) {
		}

		lock.lock();
		if (head == capacity) {
			head = 0;
		}

		item = array[head];
		array[head] = null;
		head++;
		size--;

		lock.unlock();
		return item;
	}

	public void enqueue(T item) {

		while (size == capacity) {
		}

		lock.lock();
		if (tail == capacity) {
			tail = 0;
		}

		array[tail] = item;
		size++;
		tail++;
		lock.unlock();
	}

	static final FaultyBlockingQueueWith2ThreadMutex<Integer> queue = new FaultyBlockingQueueWith2ThreadMutex<Integer>(
			5);

	static void producerThread(int start, int id) {
		while (true) {
			try {
				queue.enqueue(start);
				System.out.println("Producer thread " + id + " enqueued " + start);
				start++;
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				// swallow exception
			}
		}
	}

	static void consumerThread(int id) {
		while (true) {
			try {
				System.out.println("Consumer thread " + id + " dequeued " + queue.dequeue());
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				// swallow exception
			}
		}
	}

	public static void main(String args[]) throws InterruptedException {

	

		Thread producer2 = new Thread(new Runnable() {
			public void run() {
				producerThread(5000, 2);
			}
		});

	
	
		Thread consumer2 = new Thread(new Runnable() {
			public void run() {
				consumerThread(2);
			}
		});

		producer2.setDaemon(true);
		consumer2.setDaemon(true);
	
		producer2.start();
	
		consumer2.start();
	
		Thread.sleep(20000);
	}
}