the use of CountDownLatch; a synchronization utility used to achieve concurrency. It manages multithreading where a certain sequence of operations or tasks is required. Everytime a thread finishes its work, countdown() is invoked, decrementing the counter by 1. Once this count reaches zero, await() is notified and control is given back to the main thread that has been waiting for others to finish.

countdown and await methods are used in conjunction. it keeps the thread to wait until countdown value becomes zero. 

A CountDownLatch object is initialized with the number of tasks/threads it is required to wait for. Multiple threads can block and wait for the CountDownLatch object to reach zero by invoking await()


If the CountDownLatch is initialized with zero, the thread would not wait for any other thread(s) to complete. The count passed is basically the number of times countDown() must be invoked before threads can pass through await(). If the CountDownLatch has reached zero and countDown() is again invoked, the latch will remain released hence making no difference.

The counter in the CountDownLatch cannot be reset making the CountDownLatch object unreusable. A CountDownLatch initialized with a count of 1 serves as an on/off switch where a particular thread is simply waiting for its only partner to complete. Whereas a CountDownLatch object initialized with a count of N indicates a thread waiting for N threads to complete their work. However, a single thread can also invoke countDown() N times to unblock a thread more than once.

A thread blocked on await() can also be interrupted by another thread as long as it is waiting and the counter has not reached zero.