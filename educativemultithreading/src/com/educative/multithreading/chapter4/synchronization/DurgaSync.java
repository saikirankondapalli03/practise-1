package com.educative.multithreading.chapter4.synchronization;

class DurgaSync {
	public static void main(String[] args) throws InterruptedException {
		ThreadB b = new ThreadB();
		b.start();
		synchronized (b) {
			System.out.println("main Thread calling wait() method");// step-1
			b.wait();
			System.out.println("main Thread got notification call");// step-4
			System.out.println(b.total);
		}
	}
}

class ThreadB extends Thread {
	int total = 0;

	public void run() {
		synchronized (this) {
			System.out.println("child thread starts calcuation " + Thread.currentThread().getName());// step-2
			for (int i = 0; i <= 100; i++) {
				total = total + i;
			}
			System.out.println("child thread giving notification call");// step-3

			this.notify();
		}
	}
}