package com.educative.coderust.chapter9.dp;

import java.util.Arrays;

class GameScoring {
	public static int scoringOptionsRec(int n, int[] result) {
		if (n < 0) {
			return 0;
		}

		if (result[n] > 0) {
			return result[n];
		}

		// Memoize
		int result1 = scoringOptionsRec(n - 1, result);
		int result2 = scoringOptionsRec(n - 2, result);
		int result4 = scoringOptionsRec(n - 4, result);
		result[n] = result1 + result2 + result4;

		return result[n];
	}

	public static int scoringOptions(int n) {
		if (n <= 0) {
			return 0;
		}

		int[] result = new int[n + 1];

		for (int i = 0; i < n + 1; i++) {
			result[i] = -1;
		}
		result[0] = 1;

		scoringOptionsRec(n, result);

		return result[n];
	}

	// Scoring options are 1, 2, and 4
	public static int scoringOptionsDp(int n) {
		if (n <= 0)
			return 0;

		// Max score is 4. Hence we need to save
		// last 4 ways to calculate the number of ways
		// for a given n
		int[] result = new int[4];

		// save the base case on last index of the vector
		result[3] = 1;

		for (int i = 1; i <= n; i++) {
			int sum = result[3] + result[2] + result[0];

			// slide left all the results in each iteration
			// result for current i will be saved at last index
			result[0] = result[1];
			result[1] = result[2];
			result[2] = result[3];
			result[3] = sum;
		}

		return result[3];
	}

	public static int countWaysToScoreRunsDPBT(int T) {

		int[] dp = new int[T + 1];

		for (int i = 0; i < dp.length; i++) {

			if (i == 0) {
				dp[i] = 0;
			}

			else if (i == 1) {
				dp[i] = i; // 0->1
			}

			else if (i == 2) {
				dp[i] = 2; // 0->1->2, 0->2
			}

			else if (i == 3) {
				dp[i] = 3;// 0->1->2->3, 0->2->3, 0->1->3
			}

			else if (i == 4) {
				dp[i] = 6; // 0->1->2->3->4, 0->2->4, 0->4, 0->1->2->4, 0->2->3->4, 0->1->3->4
			}

			else {
				dp[i] = dp[i - 4] + dp[i - 2] + dp[i - 1];
			}
		}

		return dp[T];

	}

	// Returns number of ways to reach score n
	static int count(int n) {
		// table[i] will store count of solutions for
		// value i.
		int table[] = new int[n + 1], i;

		// Initialize all table values as 0
		Arrays.fill(table, 0);

		// Base case (If given value is 0)
		table[0] = 1;

		// One by one consider given 3
		// moves and update the table[]
		// values after the index greater
		// than or equal to the value of
		// the picked move
		for (i = 1; i <= n; i++)
			table[i] += table[i - 1];
		for (i = 2; i <= n; i++)
			table[i] += table[i - 2];
		for (i = 4; i <= n; i++)
			table[i] += table[i - 4];

		return table[n];
	}

	// Returns number of ways to reach score n
	static int countIn3510(int n) {
		// table[i] will store count of solutions for
		// value i.
		int table[] = new int[n + 1], i;

		// Initialize all table values as 0
		Arrays.fill(table, 0);

		// Base case (If given value is 0)
		table[0] = 1;

		// One by one consider given 3
		// moves and update the table[]
		// values after the index greater
		// than or equal to the value of
		// the picked move
		for (i = 3; i <= n; i++)
			table[i] += table[i - 3];
		for (i = 5; i <= n; i++)
			table[i] += table[i - 5];
		for (i = 10; i <= n; i++)
			table[i] += table[i - 10];

		return table[n];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Number of ways score 5 can be reached = " + scoringOptionsDp(5));
		System.out.println(count(5));
	}
}