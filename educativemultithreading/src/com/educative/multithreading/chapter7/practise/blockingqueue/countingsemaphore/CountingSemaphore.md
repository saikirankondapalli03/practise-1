A CountingSemaphore is initialized with a maximum number of permits to give out. 

A thread is blocked when it attempts to release the semaphore when none of the permits have been given out.

Similarly, a thread blocks when attempting to acquire a semaphore that has all the permits given out.
 
In contrast, Java's implementation of Semaphore can be signaled (released) even if none of the permits, the Java semaphore was initialized with, have been used. Java's semaphore has no upper bound and can be released as many times as desired to increase the number of permits. Before proceeding forward, it is suggested to complete the CountingSemaphore lesson.