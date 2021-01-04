A spurious wakeup happens when a thread wakes up from waiting on a condition variable that's been signaled, only to discover that the condition it was waiting for isn't satisfied. It's called spurious because the thread has seemingly been awakened for no reason. But spurious wakeups don't happen for no reason, they usually happen because in between the time when the condition variable was signaled and when the waiting thread finally ran, another thread ran and changed the condition. There was a race condition between the threads, with the typical result that sometimes, the thread waking up on the condition variable runs first, winning the race, and sometimes it runs second, losing the race.



Spurious mean fake or false. A spurious wakeup means a thread is woken up even though no signal has been received. Spurious wakeups are a reality and are one of the reasons why the pattern for waiting on a condition variable happens in a while loop as discussed in earlier chapters. There are technical reasons beyond our current scope as to why spurious wakeups happen, but for the curious on POSIX based operating systems when a process is signaled, all its waiting threads are woken up.


* A thread can also wake up without being notified, interrupted, or
* timing out, a so-called <i>spurious wakeup</i>.  While this will rarely
* occur in practice, applications must guard against it by testing for
* the condition that should have caused the thread to be awakened and
* continuing to wait if the condition is not satisfied.  In other words,
* waits should always occur in loops, like this one:
* 
*     synchronized (obj) {
*         while (condition does not hold)
*             obj.wait(timeout);
*         ... // Perform action appropriate to condition
*     }
*