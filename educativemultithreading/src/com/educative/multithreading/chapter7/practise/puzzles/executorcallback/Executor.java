package com.educative.multithreading.chapter7.practise.puzzles.executorcallback;

class Executor {

	public void asynchronousExecution(Callback callback) throws Exception {

		Thread t = new Thread(() -> {
			// Do some useful work
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}
			callback.done();
		});
		t.start();
	}

	public static void main(String args[]) throws Exception {
		final boolean[] isDone = new boolean[1];
		isDone[0] = true;
		isDone[0] = false;
		SynchronousExecutor executor = new SynchronousExecutor();
		executor.asynchronousExecution(() -> {
			System.out.println("I am done");
		});

		System.out.println("main thread exiting...");
	}
}

class SynchronousExecutor extends Executor {

	public void asynchronousExecutionDummy(Callback callback) throws Exception {

		Object signal = new Object();
		Callback cb = new Callback() {
			@Override
			public void done() {
				callback.done();
				synchronized (signal) {
					signal.notify();
				}
			}
		};
		// Call the asynchronous executor
		super.asynchronousExecution(cb);
		synchronized (signal) {
			// spurious wakeups
			signal.wait();
		}
	}

	public void asynchronousExecution(Callback callback) throws Exception {

		Object signal = new Object();
		final boolean[] isDone = new boolean[1];

		Callback cb = new Callback() {

			@Override
			public void done() {
				callback.done();
				synchronized (signal) {
					signal.notify();
					isDone[0] = true;
				}
			}
		};

		// Call the asynchronous executor
		super.asynchronousExecution(cb);

		synchronized (signal) {
			while (!isDone[0]) {
				signal.wait();
			}
		}

	}
}

interface Callback {

	public void done();
}
