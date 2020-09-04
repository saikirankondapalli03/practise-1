package com.educative.chapter5.dp;

public class LongestAlternatingSubsequence {
//3,2,1,4
	public int findLASLength(int[] nums) {
		if (nums.length == 0)
			return 0;
		// dp[i][0] = stores the LAS ending at 'i' such that the last two elements are
		// in ascending order
		// dp[i][1] = stores the LAS ending at 'i' such that the last two elements are
		// in descending order
		int[][] dp = new int[nums.length][2];
		int maxLength = 1;
		for (int i = 0; i < nums.length; i++) {
			// every single element can be considered as LAS of length 1
			dp[i][0] = dp[i][1] = 1;
			for (int j = 0; j < i; j++) {
				System.out.println(i+"--"+j);
				if (nums[i] > nums[j]) {
					// if nums[i] is BIGGER than nums[j] then we will consider the LAS ending at 'j'
					// where the
					// last two elements were in DESCENDING order
					dp[i][0] = Math.max(dp[i][0], 1 + dp[j][1]);
					maxLength = Math.max(maxLength, dp[i][0]);
				} else if (nums[i] != nums[j]) { // if the numbers are equal don't do anything
					// if nums[i] is SMALLER than nums[j] then we will consider the LAS ending at
					// 'j' where the
					// last two elements were in ASCENDING order
					dp[i][1] = Math.max(dp[i][1], 1 + dp[j][0]);
					maxLength = Math.max(maxLength, dp[i][1]);
				}
			}
		}
		return maxLength;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LongestAlternatingSubsequence las = new LongestAlternatingSubsequence();
		//int[] nums = { 1, 3, 2, 4 };
		// System.out.println(maxAlternatingSubseqLength(nums));
		int[] nums = new int[] {4,2,3,6,10,1,12};
		System.out.println(las.findLASLength(nums));
		//nums = new int[] { 1, 3, 2, 4 };
		//System.out.println(las.findLASLength(nums));
	}

}
