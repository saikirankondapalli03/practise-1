package com.educative.multithreading.chapter10.quiz3;

class Task<T extends Number> extends Thread {
	public static void main(String args[]) throws Exception {

		Thread[] tasks = new Thread[10];
		for (int i = 0; i < 10; i++) {
			tasks[i] = new Task(i);
			tasks[i].start();
		}

		for (int i = 0; i < 10; i++) {
			tasks[i].join();
		}
	}

	T item;

	public Task(T item) {
		this.item = item;
	}

	public void run() {
		System.out.println("square root is: " + Thread.currentThread().getName()+ "====" + Math.sqrt(item.doubleValue()));
	}
}