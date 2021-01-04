package com.educative.patterns.chapter17.findkthmax;

import java.util.*;

class KthSmallestNumberSorting {

	public static int findKthSmallestNumber(int[] nums, int k) {
		Arrays.sort(nums);
		return nums[k - 1];
	}

	public static void main(String[] args) {
		int result = KthSmallestNumberSorting.findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 3);
		System.out.println("Kth smallest number is: " + result);

		// since there are two 5s in the input array, our 3rd and 4th smallest numbers
		// should be a '5'
		result = KthSmallestNumberSorting.findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 4);
		System.out.println("Kth smallest number is: " + result);

		result = KthSmallestNumberSorting.findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3);
		System.out.println("Kth smallest number is: " + result);
	}
}
