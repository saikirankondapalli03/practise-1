 a monitor is made up of a mutex and one or more condition variables.
 wait set
 entry set
 
 
 
Monitor vs Semaphore:
 
However, monitors take care of atomically acquiring the necessary locks whereas, with semaphores, the onus of appropriately acquiring and releasing locks is on the developer, which can be error-prone.

Java monitors enforce correct locking by throwing the IllegalMonitorState exception object when methods on a condition variable are invoked without first acquiring the associated lock. The exception is in a way saying that either the object's lock/mutex was not acquired at all or that an incorrect lock was acquired



A semaphore can allow several threads access to a given resource or critical section, however, only a single thread at any point in time can own the monitor and access associated resource.

Semaphores can be used to address the issue of missed signals, however with monitors additional state, called the predicate, needs to be maintained apart from the condition variable and the mutex which make up the monitor, to solve the issue of missed signals.


CONDITION VARIABLE IN MONITOR



Reentrant lock:

Java's answer to the traditional mutex is the reentrant lock, which comes with additional bells and whistles. It is similar to the implicit monitor lock accessed when using synchronized methods or blocks. With the reentrant lock, you are free to lock and unlock it in different methods but not with different threads. If you attempt to unlock a reentrant lock object by a thread which didn't lock it initially, you'll get an IllegalMonitorStateException. This behavior is similar to when a thread attempts to unlock a pthread mutex.


Condition Variable


We saw how each java object exposes the three methods, wait(),notify() and notifyAll() which can be used to suspend threads till some condition becomes true. You can think of Condition as factoring out these three methods of the object monitor into separate objects so that there can be multiple wait-sets per object. As a reentrant lock replaces synchronized blocks or methods, a condition replaces the object monitor methods. In the same vein, one can't invoke the condition variable's methods without acquiring the associated lock, just like one can't wait on an object's monitor without synchronizing on the object first. In fact, a reentrant lock exposes an API to create new condition variables, like so:

