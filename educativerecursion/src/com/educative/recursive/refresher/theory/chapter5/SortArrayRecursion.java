package com.educative.recursive.refresher.theory.chapter5;

public class SortArrayRecursion {
	public static void sortArray(int[] array, int n) {
		if (n == 1) {
			return;
		}
		for (int i = 0; i < n - 1; i++) {
			if (array[i] > array[i + 1]) {
				int temp = array[i];
				array[i] = array[i + 1];
				array[i + 1] = temp;
			}
		}
		sortArray(array, n - 1);
	}

	public static void main(String[] args) {
		System.out.println("Unsorted Array: ");

		int[] array = { 40, 24, 60, 15, 10, 45, 93 };
		// int[] array = {1,2,2,2,1};

		System.out.print("{ ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("} ");

		System.out.println("Sorted Array: ");

		sortArray(array, array.length);

		System.out.print("{ ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("} ");
	}
}
