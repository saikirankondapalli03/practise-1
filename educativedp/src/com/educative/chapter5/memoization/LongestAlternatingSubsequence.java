package com.educative.chapter5.memoization;

public class LongestAlternatingSubsequence {

	public int findLASLength(int[] nums) {
		Integer[][][] dp = new Integer[nums.length][nums.length][2];
		return Math.max(findLASLengthRecursive(dp, nums, -1, 0, true), findLASLengthRecursive(dp, nums, -1, 0, false));
	}

	private int findLASLengthRecursive(Integer[][][] dp, int[] nums, int previousIndex, int currentIndex,
			boolean isAsc) {

		if (currentIndex == nums.length)
			return 0;

		if (dp[previousIndex + 1][currentIndex][isAsc ? 1 : 0] == null) {
			int c1 = 0;
			// if ascending, the next element should be bigger
			if (isAsc) {
				if (previousIndex == -1 || nums[previousIndex] < nums[currentIndex])
					c1 = 1 + this.findLASLengthRecursive(dp, nums, currentIndex, currentIndex + 1, !isAsc);
			} else { // if descending, the next element should be smaller
				if (previousIndex == -1 || nums[previousIndex] > nums[currentIndex])
					c1 = 1 + this.findLASLengthRecursive(dp, nums, currentIndex, currentIndex + 1, !isAsc);
			}
			// skip the current element
			int c2 = this.findLASLengthRecursive(dp, nums, previousIndex, currentIndex + 1, isAsc);
			dp[previousIndex + 1][currentIndex][isAsc ? 1 : 0] = Math.max(c1, c2);
		}

		return dp[previousIndex + 1][currentIndex][isAsc ? 1 : 0];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LongestAlternatingSubsequence las = new LongestAlternatingSubsequence();
		int[] nums = { 1, 3, 2, 4 };
		//System.out.println(maxAlternatingSubseqLength(nums));
		// nums = new int[] { 3, 2, 1, 4 };
		System.out.println(las.findLASLength(nums));
		nums = new int[] { 1, 3, 2, 4 };
		System.out.println(las.findLASLength(nums));
	}

}
