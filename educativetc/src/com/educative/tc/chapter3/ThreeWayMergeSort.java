package com.educative.tc.chapter3;

import java.util.Random;
import java.util.PriorityQueue;

class Demonstration {
	public static void main(String args[]) {
		createTestData();
		long start = System.currentTimeMillis();
		mergeSort(0, input.length - 1, input);
		long end = System.currentTimeMillis();
		System.out.println("Time taken = " + (end - start));
		printArray(input);
	}

	private static int SIZE = 100;
	private static Random random = new Random(System.currentTimeMillis());
	static private int[] input = new int[SIZE];
	static PriorityQueue<Integer> q = new PriorityQueue<>(SIZE);

	private static void printArray(int[] input) {
		System.out.println();
		for (int i = 0; i < input.length; i++)
			System.out.print(" " + input[i] + " ");
		System.out.println();
	}

	private static void createTestData() {

		for (int i = 0; i < SIZE; i++) {
			input[i] = random.nextInt(10000);
		}
	}

	private static void mergeSort(int start, int end, int[] input) {

		if (start >= end) {
			return;
		} else if (start + 1 == end) {
			if (input[start] > input[end]) {
				int temp = input[start];
				input[start] = input[end];
				input[end] = temp;
			}

			return;
		}

		int oneThird = (end - start) / 3;

		// sort first half
		mergeSort(start, start + oneThird, input);

		// sort second half
		mergeSort(start + oneThird + 1, start + 1 + (2 * oneThird), input);

		// sort third half
		mergeSort(start + 2 + (2 * oneThird), end, input);

		// merge the three sorted arrays using a priority queue
		int k;

		for (k = start; k <= end; k++) {
			q.add(input[k]);
		}

		k = start;
		while (!q.isEmpty()) {
			input[k] = q.poll();
			k++;
		}
	}
}