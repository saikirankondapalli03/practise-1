package com.educative.patterns.chapter5;

class MissingNumber {

	public static int findMissingNumber(int[] nums) {
		int start = 0;
		while (start < nums.length) {
			int numAtStart=nums[start] ;
			if (numAtStart< nums.length && numAtStart != nums[numAtStart])
				swap(nums, start, nums[start]);
			else
				start++;
		}

		// find the first number missing from its index, that will be our required
		// number
		for (start = 0; start < nums.length; start++)
			if (nums[start] != start)
				return start;

		return nums.length;
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public static void main(String[] args) {
		System.out.println(MissingNumber.findMissingNumber(new int[] { 4, 0, 3, 1 ,4}));
		System.out.println(MissingNumber.findMissingNumber(new int[] { 8, 3, 5, 2, 4, 6, 0, 1 }));
	}
}
