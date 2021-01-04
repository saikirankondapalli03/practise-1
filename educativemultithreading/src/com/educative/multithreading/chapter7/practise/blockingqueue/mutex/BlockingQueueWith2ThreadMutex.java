package com.educative.multithreading.chapter7.practise.blockingqueue.mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockingQueueWith2ThreadMutex<T> {
	T[] array;
	Lock lock = new ReentrantLock();
	int size = 0;
	int capacity;
	int head = 0;
	int tail = 0;

	@SuppressWarnings("unchecked")
	public BlockingQueueWith2ThreadMutex(int capacity) {
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
			//System.out.println(Thread.currentThread().getName() + " ===> enqueue");

		}
		// holding mutex
		if (tail == capacity) {
			tail = 0;
		}
		array[tail] = item;
		size++;
		tail++;
		// release mutex
		lock.unlock();
	}

	public T dequeue() throws InterruptedException {
		T item = null;
		lock.lock();
		while (size == 0) {
			lock.unlock();
			lock.lock();
			//System.out.println(Thread.currentThread().getName() + " ===> dequeue");
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
	
	public static void main(String args[]) throws InterruptedException {
		final BlockingQueueWith2ThreadMutex<Integer> queue = new BlockingQueueWith2ThreadMutex<Integer>(5);
		Thread producer1 = new Thread(new Runnable() {
			public void run() {
				try {
					int i = 1;
					while (i < 6) {
						queue.enqueue(i);
						System.out.println("Producer thread 1 enqueued " + i);
						i++;
					}
				} catch (InterruptedException ie) {
				}
			}
		});
		Thread consumer1 = new Thread(new Runnable() {
			public void run() {
				try {
					int i = 1;
					while (i < 6) {
						System.out.println("Consumer thread 1 dequeued " + queue.dequeue());
					}
				} catch (InterruptedException ie) {

				}
			}
		});
		producer1.setDaemon(true);
		consumer1.setDaemon(true);
		producer1.start();
		consumer1.start();
		Thread.sleep(1000);
	}
}
