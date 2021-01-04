ThreadPool Definitions:

Thread pools in Java are implementations of the Executor interface or any of its sub-interfaces.

A thread pool consists of homogenous worker threads that are assigned to execute tasks. Once a worker thread finishes a task, it is returned to the pool. Usually, thread pools are bound to a queue from which tasks are dequeued for execution by worker threads.

Advantages of Executors over threads:
1. There's no latency when a request is received and processed by a thread because no time is lost in creating a thread.

2. The system will not go out of memory because threads are not created without any limits

3. Fine tuning the thread pool will allow us to control the throughput of the system. We can have enough threads to keep all processors busy but not so many as to overwhelm the system.

4. The application will degrade gracefully if the system is under load.


Another definition:
Thread pool represents a group of worker threads which execute tasks, each thread can be reused many times. If a new task is submitted when all threads are active, they will wait in the queue until a thread is available. Thread pool implementation internally uses LinkedBlockingQueue for adding and removing tasks to the queue.


Best read : http://geeksforgeeks.org/thread-pools-java/

Best advantages of using Thread Pool:

Using thread pools we are able to 
a) control the order in which a task is executed
b) the thread in which a task is executed
c) the maximum number of tasks that can be executed concurrently
d) maximum number of tasks that can be queued for execution
e) the selection criteria for rejecting tasks when the system is overloaded and finally actions to take before or after execution of tasks.