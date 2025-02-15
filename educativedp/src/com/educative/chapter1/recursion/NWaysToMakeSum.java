package com.educative.chapter1.recursion;

public class NWaysToMakeSum {

	public int findTargetSubsets(int[] num, int s) {
		int totalSum = 0;
		for (int n : num)
			totalSum += n;

		// if 's + totalSum' is odd, we can't find a subset with sum equal to '(s +
		// totalSum) / 2'
		if (totalSum < s || (s + totalSum) % 2 == 1)
			return 0;

		return countSubsets(num, (s + totalSum) / 2);
	}

	// this function is exactly similar to what we have in 'Count of Subset Sum'
	// problem.
	private int countSubsets(int[] num, int sum) {
		int n = num.length;
		int[] dp = new int[sum + 1];
		dp[0] = 1;

		// with only one number, we can form a subset only when the required sum is
		// equal to the number
		for (int s = 1; s <= sum; s++) {
			dp[s] = (num[0] == s ? 1 : 0);
		}

		// process all subsets for all sums
		for (int i = 1; i < num.length; i++) {
			for (int s = sum; s >= 0; s--) {
				if (s >= num[i])
					dp[s] += dp[s - num[i]];
			}
		}

		return dp[sum];
	}

	public static void main(String[] args) {
		NWaysToMakeSum ts = new NWaysToMakeSum();
		int[] num = { 1, 1, 2, 3 };
		System.out.println(ts.findTargetSubsets(num, 1));
		num = new int[] { 1, 2, 7, 1 };
		System.out.println(ts.findTargetSubsets(num, 9));
	}
}
