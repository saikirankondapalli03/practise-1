package com.educative.patterns.chapter15.dp;

public class SubsetCount {
	public int countSubsets(int[] num, int sum) {
		int n = num.length;
		int[][] dp = new int[n][sum + 1];

		// populate the sum=0 columns, as we will always have an empty set for zero sum
		for (int i = 0; i < n; i++)
			dp[i][0] = 1;

		// with only one number, we can form a subset only when the required sum is
		// equal to its value
		for (int j = 1; j <= sum; j++) {
			dp[0][j] = (num[0] == j ? 1 : 0);
		}

		// process all subsets for all sums
		for (int i = 1; i < num.length; i++) {
			for (int j = 1; j <= sum; j++) {
				// exclude the number
				dp[i][j] = dp[i - 1][j];
				// include the number, if it does not exceed the sum
				if (j >= num[i])
					dp[i][j] += dp[i - 1][j - num[i]];
			}
		}

		// the bottom-right corner will have our answer.
		return dp[num.length - 1][sum];
	}

	public static void main(String[] args) {
		SubsetCount ss = new SubsetCount();
		int[] num = { 1, 1, 2, 3 };
		System.out.println(ss.countSubsets(num, 4));
		//num = new int[] { 1, 2, 7, 1, 5 };
		//System.out.println(ss.countSubsets(num, 9));
	}
}
