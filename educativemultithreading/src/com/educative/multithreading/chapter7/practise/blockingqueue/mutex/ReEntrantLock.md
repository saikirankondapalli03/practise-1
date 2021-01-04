ALL ABOUT REENTRANT LOCKS

ReentrantLock allow threads to enter into lock on a resource more than once. When the thread first enters into lock, a hold count is set to one. Before unlocking the thread can re-enter into lock again and every time hold count is incremented by one. For every unlock request, hold count is decremented by one and when hold count is 0, the resource is unlocked.


ReentrantLock==>
a) Provides a non-blocking attempt to acquire a lock using tryLock() method that acquires the lock only if it is not held by another thread at the time of invocation.
b) Provides a feature to acquire the lock that can be interrupted using lockInterruptibly() method that acquires the lock unless the current thread is interrupted.
c) Provides a feature to acquire the lock that can timeout using the tryLock(long timeout, TimeUnit unit) method that acquires the lock if it is not held by another thread within the given waiting time and the current thread has not been interrupted.
d) 5- ReentrantLock also provides an option for fairness which is not there with synchronized methods and statements. If using synchronized keyword any of the waiting thread can acquire the lock which may lead to thread starvation.
e) ReentrantLock class has a constructor which takes boolean value as an argument.
		ReentrantLock(boolean fair)
when boolean value is passed as true this lock should use a fair ordering policy. Note that fair locks favors those threads that have been waiting the longest.

A ReentrantLock is unstructured, unlike synchronized constructs -- i.e. you don't need to use a block structure for locking and can even hold a lock across methods. An example:

private ReentrantLock lock;

public void foo() {
  ...
  lock.lock();
  ...
}

public void bar() {
  ...
  lock.unlock();
  ...
}

Such flow is impossible to represent via a single monitor in a synchronized construct.

Synchronized locks does not offer any mechanism of waiting queue in which after the execution of one thread any thread running in parallel can acquire the lock. Due to which the thread which is there in the system and running for a longer period of time never gets chance to access the shared resource thus leading to starvation.

Reentrant locks are very much flexible and has a fairness policy in which if a thread is waiting for a longer time and after the completion of the currently executing thread we can make sure that the longer waiting thread gets the chance of accessing the shared resource hereby decreasing the throughput of the system and making it more time consuming.



Important Points

One can forget to call the unlock() method in the finally block leading to bugs in the program. Ensure that the lock is released before the thread exits.
The fairness parameter used to construct the lock object decreases the throughput of the program.