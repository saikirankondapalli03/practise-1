package com.educative.multithreading.chapter10.quiz8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ThreadLocalInsideMethod {

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {

		ExecutorService es = Executors.newFixedThreadPool(1);
		Future<Integer>[] tasks = new Future[100];

		for (int i = 0; i < 100; i++) {
			tasks[i] = es.submit(() -> countTo100());
		}

		for (int i = 0; i < 100; i++)
			System.out.println(tasks[i].get());

		es.shutdown();
	}

	static int countTo100() {

		ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);
		for (int j = 0; j < 100; j++)
			count.set(count.get() + 1);

		return count.get();

	}
}