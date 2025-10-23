package com.patterns.slidingwindow.pattern4countsubarrays;

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
		SubarrayProductLessThanK.printSubarrays(new int[] { 8, 2, 6, 5 }, 50);

		
		System.out.println(SubarrayProductLessThanK.findSubarrays(new int[] { 2, 5, 3, 10 }, 30));
	
		System.out.println(SubarrayProductLessThanK.findSubarrays(new int[] { 8, 2, 6, 5 }, 50));
	}
	
	
    public static void printSubarrays(int[] nums, int k) {
        if (k <= 1) return;

        int left = 0;
        long product = 1;

        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];

            // shrink window if invalid
            while (product >= k && left <= right) {
                product /= nums[left++];
            }

            // collect subarrays ending at right
            List<Integer> temp = new ArrayList<>();
            for (int i = right; i >= left; i--) {
                temp.add(0, nums[i]); // prepend to maintain order
            }
            System.out.println(temp);

        }
    }
}
