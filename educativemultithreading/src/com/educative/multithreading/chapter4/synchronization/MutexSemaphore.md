Mutex as the name hints implies mutual exclusion. A mutex is used to guard shared data such as a linked-list, an array or any primitive type. A mutex allows only a single thread to access a resource or critical section.

Once a thread acquires a mutex, all other threads attempting to acquire the same mutex are blocked until the first thread releases the mutex. Once released, most implementations arbitrarily chose one of the waiting threads to acquire the mutex and make progress.

Semaphore, on the other hand, is used for limiting access to a collection of resources. Think of semaphore as having a limited number of permits to give out. If a semaphore has given out all the permits it has, then any new thread that comes along requesting for a permit will be blocked, till an earlier thread with a permit returns it to the semaphore. A typical example would be a pool of database connections that can be handed out to requesting threads. Say there are ten available connections but 50 requesting threads. In such a scenario, a semaphore can only give out ten permits or connections at any given point in time.

A semaphore with a single permit is called a binary semaphore and is often thought of as an equivalent of a mutex, which isn't completely correct as we'll shortly explain. Semaphores can also be used for signaling among threads. This is an important distinction as it allows threads to cooperatively work towards completing a task. A mutex, on the other hand, is strictly limited to serializing access to shared state among competing threads.




MUTEX VS SEMAPHORE MAIN DIFFERENCE

 A mutex is owned by the thread acquiring it till the point the owning-thread releases it, whereas for a semaphore there's no notion of ownership.
 
 
 
 
This is a classic gotcha moment for a newbie to Java concurrency. Remember in the absence of synchronization, the compiler, processor or the runtime are free to take liberty in reordering operations. There's no guarantee that the values written to myvalue and done by thread2 are visible to thread1 in the same order or visible at all. The updated values may reside in the processor cache and not be immediately propagated to main memory from where thread1 reads. It's also possible that thread1 sees updated value for one of the variables and not for another. Synchronization is not only about mutual exclusion but also about memory visibility.