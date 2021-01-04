package com.educative.multithreading.chapter7.practise.puzzles.superman;
/*
 * 
 * 
The below solution seems almost correct. In fact, it'll appear correct unless you understand how the intricacies of Java's memory model and compiler optimizations can affect thread behaviors. The memory model defines what state a thread may see when it reads a memory location modified by other threads.
 
Here is the scenario:

Thread A comes along and gets to the second if check and allocates memory for the superman object but doesn't complete construction of the object and gets switched out. The Java memory model doesn't ensure that the constructor completes before the reference to the new object is assigned to an instance. It is possible that the variable superman is non-null but the object it points to, is still being initialized in the constructor by another thread.
 
 Thread B wants to use the superman object and since the memory is already allocated for the object it fails the first if check and returns a semi-constructed superman object. Attempt to use a partially created object results in a crash or undefined behavior.
 
 To fix the above issue, we mark our superman static object as volatile. The happens-before semantics of volatile guarantee that the faulty scenario of threads A and B never happens.

By now you'll probably appreciate how hard it is to get the singleton pattern right in a multithreaded scenario. Also, note that the discussed solution works with Java 1.5 or above. volatile's behavior in Java 1.4 and earlier is different and the double checked locking pattern is broken when run on those versions of Java.

Last but not the least, double-checked locking (DCL) is an antipattern and its utility has dwindled over time as the JVM startup and uncontended synchronization speeds have improved.
*/

public class SupermanSlightlyBetter {
	private static SupermanSlightlyBetter superman;

	private SupermanSlightlyBetter() {

	}

	public static SupermanSlightlyBetter getInstance() {

		// Check if object is uninitialized
		if (superman == null) {

			// Now synchronize on the class object, so that only
			// 1 thread gets a chance to initialize the superman
			// object. Note that multiple threads can actually find
			// the superman object to be null and fall into the
			// first if clause
			synchronized (SupermanSlightlyBetter.class) {

				// Must check once more if the superman object is still
				// null. It is possible that another thread might have
				// initialized it already as multiple threads could have
				// made past the first if check.
				if (superman == null) {
					superman = new SupermanSlightlyBetter();
				}
			}

		}

		return superman;
	}
}
