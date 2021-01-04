package com.educative.multithreading.chapter9.threadbasics;

// Once the dameon thread exits, it kills the thread it spawned
class ExecuteMe implements Runnable {
	public static void main(String args[]) throws InterruptedException {

		ExecuteMe executeMe = new ExecuteMe();
		Thread innerThread = new Thread(executeMe);
		//innerThread.setDaemon(true);
		innerThread.start();
        //innerThread.join();

	}

	public void run() {
		int i=0;
		while (i<5) {
			System.out.println("Say Hello over and over again.");
			try {
				Thread.sleep(500);
			} catch (InterruptedException ie) {
				// swallow interrupted exception
			}
			i++;
		}
	}
}