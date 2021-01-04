Life cycle of Executor:

An executor has the following stages in its life-cycle:

a) Running

b) Shutting Down

c) Terminated

As mentioned earlier, JVM can't exit unless all non-daemon thread have terminated. Executors can be made to shutdown either abruptly or gracefully. 

When doing the former, the executor attempts to cancel all tasks in progress and doesn't work on any enqueued ones, whereas

when doing the latter, the executor gives a chance for tasks already in execution to complete but also completes the enqueued tasks.

If shutdown is initiated then the executor will refuse to accept new tasks and if any are submitted, they can be handled by providing a "RejectedExecutionHandler." (Unknown)
