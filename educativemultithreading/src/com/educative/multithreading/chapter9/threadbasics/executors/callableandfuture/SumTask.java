package com.educative.multithreading.chapter9.threadbasics.executors.callableandfuture;

import java.util.concurrent.Callable;

class SumTask implements Callable<Integer> {

	int n;

	public SumTask(int n) {
		this.n = n;
	}

	public Integer call() throws Exception {

		if (n <= 0)
			return 0;

		int sum = 0;
		for (int i = 1; i <= n; i++) {
			sum += i;
		}

		return sum;
	}

	public static void main(String[] args) {
		final int n = 10;
		Callable<Integer> sumTask = new Callable<Integer>() {

			public Integer call() throws Exception {
				int sum = 0;
				for (int i = 1; i <= n; i++)
					sum += i;
				return sum;
			}
		};
	}
}