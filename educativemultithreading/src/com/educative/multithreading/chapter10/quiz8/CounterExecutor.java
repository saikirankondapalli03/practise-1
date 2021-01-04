package com.educative.multithreading.chapter10.quiz8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CounterExecutor {
	ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
	public CounterExecutor() {
		counter.set(10);
	}
	void increment() {
		System.out.println(counter.get() + Thread.currentThread().getName());
		counter.set(counter.get() + 1);
	}

	public static void main(String[] args) throws Exception {
		CounterExecutor counter = new CounterExecutor();
		ExecutorService es = Executors.newFixedThreadPool(20);
		Future<Integer>[] tasks = new Future[100];

		for (int i = 0; i < 100; i++) {
			tasks[i] = es.submit(() -> {
				for (int j = 0; j < 100; j++)
					counter.increment();

				return counter.counter.get();
			});
		}

		// What is the output of the below line?
		System.out.println(tasks[99].get());

		es.shutdown();
	}

}
