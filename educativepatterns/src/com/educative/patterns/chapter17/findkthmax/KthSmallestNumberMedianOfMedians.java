package com.educative.patterns.chapter17.findkthmax;

import java.util.*;

class KthSmallestNumberMedianOfMedians {

	public static int findKthSmallestNumber(int[] nums, int k) {
		return findKthSmallestNumberRec(nums, k, 0, nums.length - 1);
	}

	public static int findKthSmallestNumberRec(int[] nums, int k, int start, int end) {
		int p = partition(nums, start, end);

		if (p == k - 1)
			return nums[p];

		if (p > k - 1) // search the lower part
		{
			int val = findKthSmallestNumberRec(nums, k, start, p - 1);
			return val;
		}

		// search the higher part
		int val2 = findKthSmallestNumberRec(nums, k, p + 1, end);
		return val2;
	}

	private static int partition(int[] nums, int low, int high) {
		if (low == high)
			return low;

		int median = medianOfMedians(nums, low, high);
		// find the median in the array and swap it with 'nums[high]' which will become
		// our pivot
		for (int i = low; i < high; i++) {
			if (nums[i] == median) {
				swap(nums, i, high);
				break;
			}
		}

		int pivot = nums[high];
		for (int i = low; i < high; i++) {
			// all elements less than 'pivot' will be before the index 'low'
			if (nums[i] < pivot)
				swap(nums, low++, i);
		}
		// put the pivot in its correct place, remember nums[high] is our pivot
		swap(nums, low, high);
		return low;
	}

	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}

	private static int medianOfMedians(int[] nums, int low, int high) {
		int n = high - low + 1;
		// if we have less than 5 elements, ignore the partitioning algorithm
		if (n < 5)
			return nums[low];

		// for simplicity, lets ignore any partition with less than 5 elements
		int numOfPartitions = n / 5; // represents total number of 5 elements partitions
		int[] medians = new int[numOfPartitions];
		for (int i = 0; i < numOfPartitions; i++) {
			int partitionStart = low + i * 5; // starting index of the current partition
			Arrays.sort(nums, partitionStart, partitionStart + 5); // sort the 5 elements array
			medians[i] = nums[partitionStart + 2]; // get the middle element (or the median)
		}
		int median=partition(medians, 0, numOfPartitions - 1);
		return median;
	}

	public static void main(String[] args) {
		int result = KthSmallestNumberMedianOfMedians.findKthSmallestNumber(new int[] { 1, 17, 23, 21, 22, 25, 24, 29, 30, 26, 5, 3, 2, 4, 9, 8, 7, 6, 10,
				12, 11, 13, 14, 15, 18, 16, 19, 28, 27 }, 3);
		System.out.println("Kth smallest number is: " + result);

		// since there are two 5s in the input array, our 3rd and 4th smallest numbers
		// should be a '5'
		result = KthSmallestNumberMedianOfMedians.findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11,6 ,7}, 5);
		System.out.println("Kth smallest number is: " + result);

		result = KthSmallestNumberMedianOfMedians.findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3);
		System.out.println("Kth smallest number is: " + result);
	}
}