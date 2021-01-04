package com.educative.multithreading.chapter8.practise.puzzles.numberseries;

import java.util.concurrent.*;

class PrintNumberSeries {
	private int n;
	private Semaphore zeroSem, oddSem, evenSem;

	public PrintNumberSeries(int n) {
		this.n = n;
		zeroSem = new Semaphore(1);
		oddSem = new Semaphore(0);
		evenSem = new Semaphore(0);
	}

	public void PrintZero() {
		System.out.println("printZero => " + Thread.currentThread().getName());
		for (int i = 0; i < n; ++i) {
			try {
				zeroSem.acquire();
			} catch (Exception e) {
			}
			System.out.println("printZero1 => " + Thread.currentThread().getName());
			System.out.print("0");
			// release oddSem if i is even or else release evenSem if i is odd
			if(i %2==0) {
				System.out.println("odd sem released");
				oddSem.release();
			}
			else {
				System.out.println("even sem released");
		 		evenSem.release();
			}
		}
	}

	public void PrintEven() {
		System.out.println("PrintEven => " + Thread.currentThread().getName());

		for (int i = 2; i <= n; i += 2) {
			try {
				System.out.println("even sem acquired");
				evenSem.acquire();
			} catch (Exception e) {
			}
			System.out.println("PrintEven1 => " + Thread.currentThread().getName());
			System.out.print(i);
			zeroSem.release();
		}
	}

	public void PrintOdd() {
		System.out.println("PrintOdd => " + Thread.currentThread().getName());
		for (int i = 1; i <= n; i += 2) {
			try {
				System.out.println("odd sem acquired");
				oddSem.acquire();
			} catch (Exception e) {
			}
			System.out.println("PrintOdd1 => " + Thread.currentThread().getName());
			System.out.print(i);
			zeroSem.release();
		}
	}
}

class PrintNumberSeriesThread extends Thread {

	PrintNumberSeries series;
	String method;

	public PrintNumberSeriesThread(PrintNumberSeries series, String method) {
		this.series = series;
		this.method = method;
	}

	public void run() {

		if ("zero".equals(method)) {
			try {
				System.out.println("run0 => " + Thread.currentThread().getName());
				series.PrintZero();
			} catch (Exception e) {
			}
		} else if ("even".equals(method)) {
			try {
				System.out.println("run1 => " + Thread.currentThread().getName());
				series.PrintEven();
			} catch (Exception e) {
			}
		} else if ("odd".equals(method)) {
			try {
				System.out.println("run2 => " + Thread.currentThread().getName());
				series.PrintOdd();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {
		PrintNumberSeries series = new PrintNumberSeries(5);
		Thread t1 = new PrintNumberSeriesThread(series, "zero");
		Thread t2 = new PrintNumberSeriesThread(series, "even");
		Thread t3 = new PrintNumberSeriesThread(series, "odd");
		t1.start();
		t2.start();
		t3.start();

	}
}