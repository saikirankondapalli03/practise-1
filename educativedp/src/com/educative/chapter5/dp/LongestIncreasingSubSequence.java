package com.educative.chapter5.dp;

public class LongestIncreasingSubSequence {

	public int findLISLength(int[] nums) {
		int[] dp = new int[nums.length];
		dp[0] = 1;

		int maxLength = 1;
		for (int i = 1; i < nums.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j] && dp[i] <= dp[j]) {
					dp[i] = dp[j] + 1;
					dp[i] = Math.max(maxLength, dp[i]);
				}
			}
		}
		maxLength=dp[nums.length-1];
		return maxLength;
	}

	public static void main(String[] args) {
		LongestIncreasingSubSequence lis = new LongestIncreasingSubSequence();
		int[] nums = { 12, 10, 5,3,2, 1};
		//System.out.println(lis.findLISLength(nums));
		//nums = new int[] { -4, 10, 3, 7, 15 };
		System.out.println(lis.findLISLength(nums));
		//System.out.println(lis.findLBSLength(nums));
	}
	
	
	 private int findLBSLength(int[] nums) {
		 int[] lds = new int[nums.length];
		// find LDS for every index up to the beginning of the array
		    for (int i = 0; i < nums.length; i++) {
		      lds[i] = 1; // every element is an LDS of length 1
		      for (int j = i - 1; j >= 0; j--)
		        if (nums[j] < nums[i]) {
		          lds[i] = Math.max(lds[i], lds[j] + 1);
		        }
		    }
		    return lds[nums.length-1];
		  }
	 
}
