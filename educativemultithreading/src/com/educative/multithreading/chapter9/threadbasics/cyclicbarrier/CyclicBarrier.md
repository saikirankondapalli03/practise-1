CyclicBarrier is a synchronization mechanism introduced in JDK 5 in the java.util.concurrent package. It allows multiple threads to wait for each other at a common point (barrier) before continuing execution. The threads wait for each other by calling the await() method on the CyclicBarrier. All threads that wait for each other to reach barrier are called parties.

CyclicBarrier is initialized with an integer that denotes the number of threads that need to call the await() method on the barrier. Second argument in CyclicBarrier’s constructor is a Runnable instance that includes the action to be executed once the last thread arrives.


The most useful property of CyclicBarrier is that it can be reset to its initial state by calling the reset() method. It can be reused after all the threads have been released.


