package com.educative.multithreading.chapter5.monitor.consumerproducer;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerExampleWithWaitAndNotify {
	public static void main(String[] args) {
		List<Integer> taskQueue = new ArrayList<Integer>();
		int MAX_CAPACITY = 5;
		Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer");
		Thread tConsumer = new Thread(new Consumer(taskQueue), "Consumer");
		tProducer.start();
		tConsumer.start();
	}
}

class Consumer implements Runnable {
	private final List<Integer> taskQueue;

	public Consumer(List<Integer> sharedQueue) {
		this.taskQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void consume() throws InterruptedException {
		System.out.println(Thread.currentThread().getName());
		synchronized (taskQueue)// acquire mutex
		{
			while (taskQueue.isEmpty()) {
				System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: "+ taskQueue.size());
				taskQueue.wait();// release mutex
			}
			Thread.sleep(1000);
			int i = (Integer) taskQueue.remove(0);// do something useful
			System.out.println("Consumed: " + i);
			taskQueue.notify();// release mutex
		}
	}
}

class Producer implements Runnable {
	private final List<Integer> taskQueue;
	private final int MAX_CAPACITY;

	public Producer(List<Integer> sharedQueue, int size) {
		this.taskQueue = sharedQueue;
		this.MAX_CAPACITY = size;
	}

	@Override
	public void run() {
		int counter = 0;
		while (true) {
			try {
				produce(counter++);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void produce(int i) throws InterruptedException {
		System.out.println(Thread.currentThread().getName());

		synchronized (taskQueue) // acquire mutex
		{
			while (taskQueue.size() == MAX_CAPACITY) {
				System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: "
						+ taskQueue.size());
				taskQueue.wait(); // release mutex
			}

			Thread.sleep(1000);
			taskQueue.add(i); // do something useful
			System.out.println("Produced: " + i);
			taskQueue.notify(); // release mutex
		}
	}
}