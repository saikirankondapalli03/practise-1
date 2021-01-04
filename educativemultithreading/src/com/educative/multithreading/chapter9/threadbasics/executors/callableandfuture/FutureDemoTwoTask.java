package com.educative.multithreading.chapter9.threadbasics.executors.callableandfuture;

/*
 * 
 * 
 * Future get method:

The get method is a blocking call. It'll block till the task completes. We can also write a polling version, where we poll periodically to check if the task is complete or not. Future also allows us to cancel tasks. If a task has been submitted but not yet executed, then it'll be cancelled. However, if a task is currently running, then it may or may not be cancellable. We'll discuss cancelling tasks in detail in future lessons.
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class FutureDemoTwoTask {

	static ExecutorService threadPool = Executors.newSingleThreadExecutor();

	public static void main(String args[]) throws Exception {
		System.out.println(pollingStatusAndCancelTask(10));
		threadPool.shutdown();
	}

	static int pollingStatusAndCancelTask(final int n) throws Exception {

		int result = -1;

		Callable<Integer> sumTask1 = new Callable<Integer>() {

			public Integer call() throws Exception {

				// wait for 10 milliseconds
				Thread.sleep(10);

				int sum = 0;
				for (int i = 1; i <= n; i++)
					sum += i;
				return sum;
			}
		};

		Callable<Void> randomTask = new Callable<Void>() {

			public Void call() throws Exception {

				// go to sleep for an hours
				Thread.sleep(3600 * 1000);
				return null;
			}
		};

		Future<Integer> f1 = threadPool.submit(sumTask1);
		Future<Void> f2 = threadPool.submit(randomTask);

		// Poll for completion of first task
		try {

			// Before we poll for completion of second task,
			// cancel the second one
			f2.cancel(true);

			// Polling the future to check the status of the
			// first submitted task
			while (!f1.isDone()) {
				System.out.println("Waiting for first task to complete.");
			}
			result = f1.get();
		} catch (ExecutionException ee) {
			System.out.println("Something went wrong.");
		}

		System.out.println("\nIs second task cancelled : " + f2.isCancelled());

		return result;
	}
}