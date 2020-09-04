package com.educative.chapter5.recursion;

public class LongestIncreasingSubSequence {
	public int findLISLength(int[] nums) {
		return findLISLengthRecursive(nums, 0, -1);
	}

	private int findLISLengthRecursive(int[] nums, int currentIndex, int previousIndex) {
		if (currentIndex == nums.length)
			return 0;

		// include nums[currentIndex] if it is larger than the last included number
		int c1 = 0;
		if (previousIndex == -1 || nums[currentIndex] > nums[previousIndex])
			c1 = 1 + findLISLengthRecursive(nums, currentIndex + 1, currentIndex);

		// excluding the number at currentIndex
		int c2 = findLISLengthRecursive(nums, currentIndex + 1, previousIndex);

		return Math.max(c1, c2);
	}

	public static void main(String[] args) {
		LongestIncreasingSubSequence lis = new LongestIncreasingSubSequence();
		int[] nums = { 4, 2, 3, 6, 10, 1, 12 };
		System.out.println(lis.findLISLength(nums));
		nums = new int[] { -4, 10, 3, 7, 15 };
		System.out.println(lis.findLISLength(nums));
	}
}
