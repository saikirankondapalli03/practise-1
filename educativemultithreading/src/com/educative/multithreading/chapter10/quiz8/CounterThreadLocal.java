package com.educative.multithreading.chapter10.quiz8;

public class CounterThreadLocal {

	ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 6);

	public CounterThreadLocal() {
		counter.set(10);
	}

	void increment() {
		System.out.println(counter.get() + Thread.currentThread().getName());
		counter.set(5);
		System.out.println("After updating value => " + counter.get());
	}

	public static void main(String[] args) throws Exception {

		CounterThreadLocal counter = new CounterThreadLocal();
		Thread[] tasks = new Thread[100];

		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(() -> {
				for (int j = 0; j < 100; j++)
					counter.increment();
			});
			tasks[i] = t;
			t.start();
		}

		for (int i = 0; i < 100; i++) {
			tasks[i].join();
		}

		// What is the output of the the below line?
		System.out.println(counter.counter.get());
	}

}
