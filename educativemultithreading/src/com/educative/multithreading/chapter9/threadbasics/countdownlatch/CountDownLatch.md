CountDownLatch is a synchronization primitive that comes with the java.util.concurrent package. It can be used to block a single or multiple threads while other threads complete their operations.


A CountDownLatch object is initialized with the number of tasks/threads it is required to wait for


Multiple threads can block and wait for the CountDownLatch object to reach zero by invoking await()


the use of CountDownLatch; a synchronization utility used to achieve concurrency. It manages multithreading where a certain sequence of operations or tasks is required. Everytime a thread finishes its work, countdown() is invoked, decrementing the counter by 1. Once this count reaches zero, await() is notified and control is given back to the main thread that has been waiting for others to finish.

countdown and await methods are used in conjunction. it keeps the thread to wait until countdown value becomes zero. 

if i declare countdown latch with initial count as 2 . then i expect 2 threads to call latch.countdown. those 2 threads can be any .it can be of main thread or it can be of child thread or group  of similiar child threads