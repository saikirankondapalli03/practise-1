package com.educative.chapter5.dp;

public class MinimumDeletionsForSorted {
	public int findMinimumDeletions(int[] nums) {
		// subtracting the length of LIS from the length of the input array to get
		// minimum number of deletions
		return nums.length - findLISLength(nums);
	}

	private int findLISLength(int[] nums) {
		int[] dp = new int[nums.length];
		dp[0] = 1;

		int maxLength = 1;
		for (int i = 1; i < nums.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++)
				if (nums[i] > nums[j] && dp[i] <= dp[j]) {
					dp[i] = dp[j] + 1;
					maxLength = Math.max(maxLength, dp[i]);
				}
		}
		return maxLength;
	}

	public static void main(String[] args) {
		MinimumDeletionsForSorted mdss = new MinimumDeletionsForSorted();
		int[] nums = { 4, 2, 3, 6, 10, 1, 12 };
		System.out.println(mdss.findMinimumDeletions(nums));
		nums = new int[] { -4, 10, 3, 7, 15 };
		System.out.println(mdss.findMinimumDeletions(nums));
		nums = new int[] { 3, 2, 1, 0 };
		System.out.println(mdss.findMinimumDeletions(nums));
	}
}
