package com.educative.multithreading.chapter10.quiz3;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CallableDemo {
	public static void main(String args[]) throws Exception {
		usingExecutorService();
		usingThread();

	}

	static void usingExecutorService() {
		// Anoymous class
		Callable<Void> task = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				System.out.println("Using callable with executor service.");
				return null;
			}
		};

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.submit(task);
		executorService.shutdown();
	}

	static void usingThread() throws Exception {
		// Anoymous class
		Callable<Void> task = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				System.out.println("Using callable indirectly with instance of thread class");
				return null;
			}
		};

		// creating future task
		FutureTask<Void> ft = new FutureTask<>(task);
		Thread t = new Thread(ft);
		t.start();
		t.join();

	}

}