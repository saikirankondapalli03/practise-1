package com.educative.multithreading.chapter7.practise.blockingqueue.countingsemaphore;

public class BlockingQueueWithSemaphore<T> {
	T[] array;
	int size = 0;
	int capacity;
	int head = 0;
	int tail = 0;
	CountingSemaphore semLock = new CountingSemaphore(2, 2);
	CountingSemaphore semProducer;
	CountingSemaphore semConsumer;

	@SuppressWarnings("unchecked")
	public BlockingQueueWithSemaphore(int capacity) {
		// The casting results in a warning
		array = (T[]) new Object[capacity];
		this.capacity = capacity;
		this.semProducer = new CountingSemaphore(capacity, capacity);
		this.semConsumer = new CountingSemaphore(capacity, 0); // set the permits (as everything given out) 
	}

	public T dequeue() throws InterruptedException {
		System.out.println("dequeue => "+Thread.currentThread().getName());

		T item = null;

		semConsumer.acquire();
		semLock.acquire();

		if (head == capacity) {
			head = 0;
		}

		item = array[head];
		array[head] = null;
		head++;
		size--;

		semLock.release();
		semProducer.release();

		return item;
	}

	public void enqueue(T item) throws InterruptedException {
		System.out.println("enqueue => "+Thread.currentThread().getName());
		semProducer.acquire();
		semLock.acquire();

		if (tail == capacity) {
			tail = 0;
		}

		array[tail] = item;
		size++;
		tail++;

		semLock.release();
		semConsumer.release();
	}
}
