package com.educative.patterns.chapter2.twopointers;

import java.util.*;

class SubarrayProductLessThanK {

	public static List<List<Integer>> findSubarrays(int[] arr, int target) {
		List<List<Integer>> result = new ArrayList<>();
		int product = 1, left = 0;
		for (int right = 0; right < arr.length; right++) {
			product *= arr[right];
			while (product >= target && left < arr.length) {
				product /= arr[left];
				++left;
			}
			// since the product of all numbers from left to right is less than the target
			// therefore,
			// all subarrays from left to right will have a product less than the target
			// too; to avoid
			// duplicates, we will start with a subarray containing only arr[right] and then
			// extend it
			List<Integer> tempList = new LinkedList<>();
			for (int i = right; i >= left; i--) {
				tempList.add(0, arr[i]);
				result.add(new ArrayList<>(tempList));
			}
		}
		return result;
	}



	public static void main(String[] args) {
		System.out.println(SubarrayProductLessThanK.findSubarrays(new int[] { 10, 5, 2, 6 }, 100));
		System.out.println(SubarrayProductLessThanK.findSubarrays(new int[] { 8, 2, 6, 5 }, 50));
	}
}
