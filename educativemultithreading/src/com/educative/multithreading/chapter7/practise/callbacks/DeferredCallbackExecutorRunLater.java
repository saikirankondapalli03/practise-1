package com.educative.multithreading.chapter7.practise.callbacks;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class DeferredCallbackExecutorRunLater {
	public static void main(String args[]) throws InterruptedException {
		DeferredCallbackExecutorRunLater.runLateThenEarlyCallback();
	}

	PriorityQueue<CallBack> queue = new PriorityQueue<CallBack>(new Comparator<CallBack>() {
		public int compare(CallBack o1, CallBack o2) {
			return (int) (o1.executeAt - o2.executeAt);
		}
	});
	ReentrantLock lock = new ReentrantLock();
	Condition newCallbackArrived = lock.newCondition();

	private long findSleepDuration() {
		long currentTime = System.currentTimeMillis();
		return queue.peek().executeAt - currentTime;
	}

	public void start() throws InterruptedException {
		long sleepFor = 0;
		while (true) {
			lock.lock();
			while (queue.size() == 0) {
				newCallbackArrived.await();
			}
			while (queue.size() != 0) {
				sleepFor = findSleepDuration();
				if (sleepFor <= 0)
					break;
				newCallbackArrived.await(sleepFor, TimeUnit.MILLISECONDS);
			}
			CallBack cb = queue.poll();
			System.out.println("Executed at " + System.currentTimeMillis() / 1000 + " required at "
					+ cb.executeAt / 1000 + ": message:" + cb.message);
			lock.unlock();
		}
	}

	public void registerCallback(CallBack callBack) {
		lock.lock();
		queue.add(callBack);
		newCallbackArrived.signal();
		lock.unlock();
	}

	static class CallBack {
		long executeAt;
		String message;

		public CallBack(long executeAfter, String message) {
			this.executeAt = System.currentTimeMillis() + executeAfter * 1000;
			this.message = message;
		}
	}

	public static void runLateThenEarlyCallback() throws InterruptedException {
		final DeferredCallbackExecutorRunLater deferredCallbackExecutor = new DeferredCallbackExecutorRunLater();

		Thread service = new Thread(new Runnable() {
			public void run() {
				try {
					deferredCallbackExecutor.start();
				} catch (InterruptedException ie) {
				}
			}
		});

		service.start();

		Thread lateThread = new Thread(new Runnable() {
			public void run() {
				CallBack cb = new CallBack(8, "Hello this is the callback submitted first");
				deferredCallbackExecutor.registerCallback(cb);
			}
		});
		lateThread.start();

		Thread.sleep(3000);

		Thread earlyThread = new Thread(new Runnable() {
			public void run() {
				CallBack cb = new CallBack(1, "Hello this is callback sumbitted second");
				deferredCallbackExecutor.registerCallback(cb);
			}
		});
		earlyThread.start();

		lateThread.join();
		earlyThread.join();
	}
}
