package com.educative.multithreading.chapter3.deadlock;

public class DeadLockDurga implements Runnable {
	A a = new A();
	B b = new B();

	DeadLockDurga() {
		Thread t = new Thread(this);
		t.start();
		a.foo(b);// main thread
	}

	public void run() {
		b.bar(a);// child thread
	}

	public static void main(String[] args) {
		new DeadLockDurga();// main thread
	}
}

class A {

	public synchronized void foo(B b) {
		System.out.println("Thread1 starts execution of foo() method");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread1 trying to call b.last()");
		b.last();
	}

	public synchronized void last() {
		System.out.println("inside A, this is last()method");
	}
}

class B {
	public synchronized void bar(A a) {
		System.out.println("Thread2 starts execution of bar() method");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread2 trying to call a.last()");
		a.last();
	}

	public synchronized void last() {
		System.out.println("inside B, this is last() method");
	}
}