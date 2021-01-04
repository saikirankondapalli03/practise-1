package com.educative.multithreading.chapter7.practise.puzzles.superman;

public class SupermanCorrectButSlow {
	private static SupermanCorrectButSlow superman;
	private SupermanCorrectButSlow() {
	}
	public static SupermanCorrectButSlow getInstance() {
		synchronized (SupermanCorrectButSlow.class) {
			if (superman == null) {
				superman = new SupermanCorrectButSlow();
			}
		}
		return superman;
	}
	// Object method
	public void fly() {
		System.out.println("I am Superman & I can fly !");
	}
}

DOUBLE CHECK LOCKING:( we synchronize only when initializing the singleton instance and not at other times?)
The con of the above solution is that every invocation of the getInstance() method causes the invoking thread to synchronize, which is prohibitively more expensive in terms of performance than non-synchronized snippets of code. Can we synchronize only when initializing the singleton instance and not at other times? The answer is yes and leads us to an implementation known as "double checked locking".

The idea is that we do two checks for superman == null in a nested fashion. The first check is without synchronization and the second with. Once a singleton instance has been initialized, all future invocations of the getInstance() method don't pass the first null check and return the instance without getting involved in synchronization. Effectively, threads only synchronize when the singleton instance has not yet been initialized.


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
	
problem with above one: 

The above solution seems almost correct. In fact, it'll appear correct unless you understand how the intricacies of Java's memory model and compiler optimizations can affect thread behaviors. The memory model defines what state a thread may see when it reads a memory location modified by other threads. The above solution needs one last missing piece but before we add that consider the below scenario:

Thread A comes along and gets to the second if check and allocates memory for the superman object but doesn't complete construction of the object and gets switched out. The Java memory model doesn't ensure that the constructor completes before the reference to the new object is assigned to an instance. It is possible that the variable superman is non-null but the object it points to, is still being initialized in the constructor by another thread.

Thread B wants to use the superman object and since the memory is already allocated for the object it fails the first if check and returns a semi-constructed superman object. Attempt to use a partially created object results in a crash or undefined behavior.


To fix the above issue, we mark our superman static object as volatile. The happens-before semantics of volatile guarantee that the faulty scenario of threads A and B never happens.

By now you'll probably appreciate how hard it is to get the singleton pattern right in a multithreaded scenario. Also, note that the discussed solution works with Java 1.5 or above. volatile's behavior in Java 1.4 and earlier is different and the double checked locking pattern is broken when run on those versions of Java.

Last but not the least, double-checked locking (DCL) is an antipattern and its utility has dwindled over time as the JVM startup and uncontended synchronization speeds have improved.

The next lesson explains alternate Singleton implementations in Java.

	
	