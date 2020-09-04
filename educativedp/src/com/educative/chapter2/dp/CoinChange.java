package com.educative.chapter2.dp;

public class CoinChange {
	public int countChange(int[] denominations, int total) {
		int n = denominations.length;
		int[][] dp = new int[n][total + 1];

		// populate the total=0 columns, as we will always have an empty set for zero
		// toal
		for (int i = 0; i < n; i++)
			dp[i][0] = 1;

		// process all sub-arrays for all capacities
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= total; j++) {
				if (i > 0)
					dp[i][j] = dp[i - 1][j];
				if (j >= denominations[i])
					dp[i][j] += dp[i][j - denominations[i]];
			}
		}

		// total combinations will be at the bottom-right corner.
		return dp[n - 1][total];
	}

	public static void main(String[] args) {
		CoinChange cc = new CoinChange();
		int[] denominations = { 1, 2, 5 };
		System.out.println(cc.countChange(denominations, 7));
	}
}
