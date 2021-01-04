CyclicBarrier is used to make threads wait for each other. It is used when different threads process a part of computation and when all threads have completed the execution, the result needs to be combined in the parent thread. In other words, a CyclicBarrier is used when multiple thread carry out different sub tasks and the output of these sub tasks need to be combined to form the final output. After completing its execution, threads call await() method and wait for other threads to reach the barrier. Once all the threads have reached, the barriers then give the way for threads to proceed.


CyclicBarriers are defined in java.util.concurrent package. First a new instance of a CyclicBarriers is created specifying the number of threads that the barriers should wait upon.


CyclicBarrier newBarrier = new CyclicBarrier(numberOfThreads);

Each and every thread does some computation and after completing it’s execution, calls await() methods as shown:

public void run()
{
    // thread does the computation
    newBarrier.await();
}



Important Methods of CyclicBarrier:

getParties: Returns the number of parties required to trip this barrier.
Syntax:
public int getParties()
Returns:
the number of parties required to trip this barrier

reset: Resets the barrier to its initial state.
Syntax:
public void reset()
Returns:
void but resets the barrier to its initial state. If any parties are currently waiting at the barrier, they will return with a BrokenBarrierException.

isBroken: Queries if this barrier is in a broken state.
Syntax:
public boolean isBroken()
Returns:
true if one or more parties broke out of this barrier due to interruption or timeout since construction or the last reset, or a barrier action failed due to an exception; false otherwise.

getNumberWaiting: Returns the number of parties currently waiting at the barrier.
Syntax:
public int getNumberWaiting()
Returns:
the number of parties currently blocked in await()

await: Waits until all parties have invoked await on this barrier.
Syntax:
public int await() throws InterruptedException, BrokenBarrierException
Returns:
the arrival index of the current thread, where index getParties() – 1 indicates the first to arrive and zero indicates the last to arrive.

await: Waits until all parties have invoked await on this barrier, or the specified waiting time elapses.
Syntax:


public int await(long timeout, TimeUnit unit) 
throws InterruptedException,
BrokenBarrierException, TimeoutException
Returns:
the arrival index of the current thread, where index getParties() – 1 indicates the first to arrive and zero indicates the last to arrive

