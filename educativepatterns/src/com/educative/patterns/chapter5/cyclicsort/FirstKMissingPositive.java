package com.educative.patterns.chapter5.cyclicsort;

import java.util.*;

class FirstKMissingPositive {

	public static List<Integer> findNumbers(int[] nums, int k) {
		int i = 0;
		while (i < nums.length) {
			if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[nums[i] - 1])
				swap(nums, i, nums[i] - 1);
			else
				i++;
		}

		List<Integer> missingNumbers = new ArrayList<>();
		Set<Integer> extraNumbers = new HashSet<>();
		for (i = 0; i < nums.length && missingNumbers.size() < k; i++)
			if (nums[i] != i + 1) {
				missingNumbers.add(i + 1);
				extraNumbers.add(nums[i]);
			}

		// add the remaining missing numbers
		for (i = 1; missingNumbers.size() < k; i++) {
			int candidateNumber = i + nums.length;
			// ignore if the array contains the candidate number
			if (!extraNumbers.contains(candidateNumber))
				missingNumbers.add(candidateNumber);
		}

		return missingNumbers;
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		List<Integer> missingNumbers = FirstKMissingPositive.findNumbers(new int[] { 2, 1, 3, 8, 7 }, 6);
		System.out.println("Missing numbers: " + missingNumbers);

		missingNumbers = FirstKMissingPositive.findNumbers(new int[] { 2, 3, 4 }, 3);
		System.out.println("Missing numbers: " + missingNumbers);

		missingNumbers = FirstKMissingPositive.findNumbers(new int[] { -2, -3, 4 }, 2);
		System.out.println("Missing numbers: " + missingNumbers);
	}
}
