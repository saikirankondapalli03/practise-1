package com.educative.recursive.refresher.theory.chapter5;

public class TotalOccurences {

	private static int noOfOccurrences(int[] a, int key, int currentIndex) {
		if (a.length == currentIndex) {
			return 0;
		} else if (a[currentIndex] == key) {
			return 1 + noOfOccurrences(a, key, currentIndex + 1);
		} else {
			return noOfOccurrences(a, key, currentIndex + 1);
		}
	}

	public static void main(String[] args) {
		System.out.print("{");

		int[] array = { 2, 3, 4, 1, 7, 8, 3 };
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}

		System.out.println("}");

		int key = 3;
		int output = noOfOccurrences(array, key, 0);
		System.out.println("Number of occurrences of " + key + ": " + output);
	}

}