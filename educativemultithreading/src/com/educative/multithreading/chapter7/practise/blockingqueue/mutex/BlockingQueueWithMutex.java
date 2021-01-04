package com.educative.multithreading.chapter7.practise.blockingqueue.mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithMutex<T> {
	public static void main(String args[]) throws InterruptedException {
		final BlockingQueueWithMutex<Integer> queue = new BlockingQueueWithMutex<Integer>(5);

		Thread producer1 = new Thread(new Runnable() {
			public void run() {
				try {
					int i = 1;
					while (i < 5) {
						queue.enqueue(i);
						System.out.println("Producer thread 1 enqueued " + i);
						i++;
					}
				} catch (InterruptedException ie) {
				}
			}
		});

		Thread producer2 = new Thread(new Runnable() {
			public void run() {
				try {
					int i = 5;
					while (i < 10) {
						queue.enqueue(i);
						System.out.println("Producer thread 2 enqueued " + i);
						i++;
					}
				} catch (InterruptedException ie) {

				}
			}
		});

		Thread producer3 = new Thread(new Runnable() {
			public void run() {
				try {
					int i = 11;
					while (i < 40) {
						queue.enqueue(i);
						System.out.println("Producer thread 3 enqueued " + i);
						i++;
					}
				} catch (InterruptedException ie) {

				}
			}
		});

		Thread consumer1 = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						System.out.println("Consumer thread 1 dequeued " + queue.dequeue());
					}
				} catch (InterruptedException ie) {

				}
			}
		});

		Thread consumer2 = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						System.out.println("Consumer thread 2 dequeued " + queue.dequeue());
					}
				} catch (InterruptedException ie) {

				}
			}
		});

		Thread consumer3 = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						System.out.println("Consumer thread 3 dequeued " + queue.dequeue());
					}
				} catch (InterruptedException ie) {

				}
			}
		});

		producer1.setDaemon(true);
		producer2.setDaemon(true);
		producer3.setDaemon(true);
		consumer1.setDaemon(true);
		consumer2.setDaemon(true);
		consumer3.setDaemon(true);

		producer1.start();
		producer2.start();
		producer3.start();

		consumer1.start();
		consumer2.start();
		consumer3.start();

		Thread.sleep(1000);
	}

	T[] array;

	Lock lock = new ReentrantLock();
	int size = 0;
	int capacity;
	int head = 0;
	int tail = 0;

	@SuppressWarnings("unchecked")
	public BlockingQueueWithMutex(int capacity) {
		// The casting results in a warning
		array = (T[]) new Object[capacity];
		this.capacity = capacity;
	}

	
	public void enqueue(T item) throws InterruptedException {
		lock.lock();
		while (size == capacity) {// while holding the mutex!
			// Release the mutex to give other threads
			lock.unlock(); // give up the mutex at this point
			// Reacquire the mutex before checking the
			// condition
			lock.lock();
		}

		// holding mutex
		if (tail == capacity) {
			tail = 0;
		}

		array[tail] = item;
		size++;
		tail++;
		//release mutex
		lock.unlock();
	}
	
	public T dequeue() throws InterruptedException {
		T item = null;
		lock.lock();
		while (size == 0) {
			lock.unlock();
			lock.lock();
		}

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

}
