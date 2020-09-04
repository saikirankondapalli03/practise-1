package com.educative.chapter2.dp;

class UnBoundedKnapSack {

	public int solveKnapsack(int[] profits, int[] weights, int capacity) {
		// base checks
		if (capacity <= 0 || profits.length == 0 || weights.length != profits.length)
			return 0;

		int n = profits.length;
		int[][] dp = new int[n][capacity + 1];

		// populate the capacity=0 columns
		for (int i = 0; i < n; i++)
			dp[i][0] = 0;

		// process all sub-arrays for all capacities
		for (int i = 0; i < n; i++) {
			for (int j = 1; j <= capacity; j++) {
				int profit1 = 0, profit2 = 0;
				if (weights[i] <= j)
					profit1 = profits[i] + dp[i][j - weights[i]];
				if (i > 0)
					profit2 = dp[i - 1][j];
				dp[i][j] = Math.max(profit1, profit2);
			}
		}

		// maximum profit will be in the bottom-right corner.
		return dp[n - 1][capacity];
	}

	public static void main(String[] args) {
		UnBoundedKnapSack ks = new UnBoundedKnapSack();
		int[] profits = { 15, 50, 60, 90 };
		int[] weights = { 1, 3, 4, 5 };
		System.out.println(ks.solveKnapsack(profits, weights, 8));
		System.out.println(ks.solveKnapsack(profits, weights, 6));
	}
}
