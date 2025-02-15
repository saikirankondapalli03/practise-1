package com.educative.chapter5.memoization;

public class LongestIncreasingSubSequence {
	public int findLISLength(int[] nums) {
		Integer[][] dp = new Integer[nums.length][nums.length + 1];
		return findLISLengthRecursive(dp, nums, 0, -1);
	}

	private int findLISLengthRecursive(Integer[][] dp, int[] nums, int currentIndex, int previousIndex) {
		if (currentIndex == nums.length)
			return 0;

		if (dp[currentIndex][previousIndex + 1] == null) {
			// include nums[currentIndex] if it is larger than the last included number
			int c1 = 0;
			if (previousIndex == -1 || nums[currentIndex] > nums[previousIndex])
				c1 = 1 + findLISLengthRecursive(dp, nums, currentIndex + 1, currentIndex);

			int c2 = findLISLengthRecursive(dp, nums, currentIndex + 1, previousIndex);
			dp[currentIndex][previousIndex + 1] = Math.max(c1, c2);
		}

		return dp[currentIndex][previousIndex + 1];
	}

	public static void main(String[] args) {
		LongestIncreasingSubSequence lis = new LongestIncreasingSubSequence();
		int[] nums = { 4, 2, 3, 6, 10, 1, 12 };
		System.out.println(lis.findLISLength(nums));
		nums = new int[] { -4, 10, 3, 7, 15 };
		System.out.println(lis.findLISLength(nums));
	}
}
