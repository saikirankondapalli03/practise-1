package com.educative.patterns.chapter5.cyclicsort;

import java.util.*;

class AllMissingNumbers {

	public static List<Integer> findNumbers(int[] nums) {
		int start = 0;
		while (start < nums.length) {
			int j = nums[start] - 1;
			if (nums[start] != nums[j])
				swap(nums, start, j);
			else
				start++;
		}

		List<Integer> missingNumbers = new ArrayList<>();
		for (start = 0; start < nums.length; start++)
			if (nums[start] != start + 1)
				missingNumbers.add(start + 1);

		return missingNumbers;
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		List<Integer> missing = AllMissingNumbers.findNumbers(new int[] { 2, 3, 1, 7, 2, 3, 5, 1 });
		System.out.println("Missing numbers: " + missing);

		missing = AllMissingNumbers.findNumbers(new int[] { 2, 4, 1, 2 });
		System.out.println("Missing numbers: " + missing);

		missing = AllMissingNumbers.findNumbers(new int[] { 2, 3, 2, 1 });
		System.out.println("Missing numbers: " + missing);
	}
}
