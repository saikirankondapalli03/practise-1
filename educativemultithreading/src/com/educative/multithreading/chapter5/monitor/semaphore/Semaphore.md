https://www.geeksforgeeks.org/semaphore-in-java/


Semaphore is essentially a counter + a mutex + a wait queue.

Conditional variable is essentially a wait-queue,


Java's semaphore is initialized with an initial number of permits, rather than the maximum possible permits and the developer is expected to take care of always releasing the intended number of maximum permits.


Your task is to implement a semaphore which takes in its constructor the maximum number of permits allowed and is also initialized with the same number of permits.

semaphore.release() and semaphore.acquire() are used in conjunction for interthread communication just like wait notify notifyall

//use of semaphores(for both locking and signalling) Locks 
