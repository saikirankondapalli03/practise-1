package com.educative.multithreading.chapter9.threadbasics.threadlocal;
/*
 * 
 * 
 * If multiple threads call this method, then each executing thread will create a copy of the local variables on its own thread stack. There would be no shared variables amongst the threads and the instance method by itself would be thread-safe.
 * 
 * 
 * void add(int val) {

        int count = 5;
        count += val;
        System.out.println(val);

    }

 if we moved the count variable out of the method and declared it as an instance variable then the same code will not be thread-safe

 */
class ThreadLocalDemoUnsafe {
	public static void main(String args[]) throws Exception {
		UnsafeCounter usc = new UnsafeCounter();
		Thread[] tasks = new Thread[100];
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(() -> {
				for (int j = 0; j < 100; j++)
					usc.increment();
			},"Thread"+i);
			tasks[i] = t;
			t.start();
		}
		for (int i = 0; i < 100; i++) {
			tasks[i].join();
		}
		System.out.println(usc.count);
	}
}

class UnsafeCounter {

	// Instance variable
	int count = 0;

	void increment() {
		System.out.println(Thread.currentThread().getName());
		count = count + 1;
	}
}