package com.educative.multithreading.chapter10.quiz6;

public class MemoryVisibilityVolatile {
	int myvalue = 2;
	volatile boolean done = false;

	void thread1() {
		while (!done)
		{
			
		}
		System.out.println(myvalue);
	}

	void thread2() {

		myvalue = 5;
		done = true;
		this.notify();

	}
}
