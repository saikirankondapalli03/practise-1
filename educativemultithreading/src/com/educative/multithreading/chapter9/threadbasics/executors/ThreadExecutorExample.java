package com.educative.multithreading.chapter9.threadbasics.executors;

import java.util.concurrent.Executor;

class ThreadExecutorExample {

	public static void main(String args[]) {
		DumbExecutor myExecutor = new DumbExecutor();
		MyTask myTask = new MyTask();
		myExecutor.execute(myTask);
	}

	/*
	 * 
	 * The Executor requires implementing classes to define a method execute(Runnable runnable) which takes in an object of interface Runnable
	 */
	static class DumbExecutor implements Executor {
		// Takes in a runnable interface object
		public void execute(Runnable runnable) {
			Thread newThread = new Thread(runnable);
			newThread.start();
		}
	}

	static class MyTask implements Runnable {
		public void run() {
			System.out.println("Mytask is running now ...");
		}
	}

}