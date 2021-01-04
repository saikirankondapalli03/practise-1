package com.educative.multithreading.chapter4.synchronization;

public class WaitNotifyTest {
	private boolean running = true;
	private Thread thread;

	public void start() {
		print("Inside start() method");
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				print("Inside run() method");
				synchronized (WaitNotifyTest.this) {
					running = false;
					WaitNotifyTest.this.notify();
				}
			}
		});
		thread.start();
	}

	public void join() throws InterruptedException {
		print("Inside join() method");
		synchronized (WaitNotifyTest.this) {
			while (running) {
				print("Waiting for the peer thread to finish.");
				WaitNotifyTest.this.wait(); // waiting, not running
			}
			print("Peer thread finished.");
		}
	}

	private void print(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) throws InterruptedException {
		WaitNotifyTest test = new WaitNotifyTest();
		test.start();
		test.join();
	}
}