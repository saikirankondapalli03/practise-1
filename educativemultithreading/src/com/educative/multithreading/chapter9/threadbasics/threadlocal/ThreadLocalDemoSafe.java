package com.educative.multithreading.chapter9.threadbasics.threadlocal;
/*
 * 
 * 
have a copy of an instance (or a class) variable for each thread that accesses it by declaring the instance variable ThreadLocal.
 */
class ThreadLocalDemoSafe {
	public static void main(String args[]) throws Exception {
		SafeCounter usc = new SafeCounter();
		Thread[] tasks = new Thread[100];
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(() -> {
				for (int j = 0; j < 100; j++)
					usc.increment();
				 System.out.println(usc.counter.get());
			});
			tasks[i] = t;
			t.start();
		}
		for (int i = 0; i < 100; i++) {
			tasks[i].join();
		}
		System.out.println(usc.counter.get());
	}
}

class SafeCounter {
	/*
	 * 
	 * ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
	 * The above code creates a separate and completely independent copy of the variable counter for every thread that calls the increment() method. Conceptually, you can think of a ThreadLocal<T> variable as a map that contains mapping for each thread and its copy of the threadlocal variable or equivalently a Map<Thread, T>. Though this is not how it is actually implemented.
	 * 
	 * 
	 * Furthermore, the thread specific values are stored in the thread object itself and are eligible for garbage collection once a thread terminates (if no other references exist to the threadlocal value).
	 * ThreadLocal variables get tricky when used with the executor service (threadpools) since threads don't terminate and are returned to the threadpool. So any threadlocal variables aren't garbage collected. 
	 */
	
	ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
	
	
	void increment() {
		counter.set(counter.get() + 1);
	}
}