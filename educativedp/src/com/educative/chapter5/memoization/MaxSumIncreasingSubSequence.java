package com.educative.chapter5.memoization;

import java.util.HashMap;
import java.util.Map;

public class MaxSumIncreasingSubSequence {
	public int findMSIS(int[] nums) {
		Map<String, Integer> dp = new HashMap<>();
		return findMSISRecursive(dp, nums, 0, -1, 0);
	}

	private int findMSISRecursive(Map<String, Integer> dp, int[] nums, int currentIndex, int previousIndex, int sum) {
		if (currentIndex == nums.length)
			return sum;

		String subProblemKey = currentIndex + "-" + previousIndex + "-" + sum;
		if (!dp.containsKey(subProblemKey)) {
			// include nums[currentIndex] if it is larger than the last included number
			int s1 = sum;
			if (previousIndex == -1 || nums[currentIndex] > nums[previousIndex])
				s1 = findMSISRecursive(dp, nums, currentIndex + 1, currentIndex, sum + nums[currentIndex]);

			// excluding the number at currentIndex
			int s2 = findMSISRecursive(dp, nums, currentIndex + 1, previousIndex, sum);
			dp.put(subProblemKey, Math.max(s1, s2));
		}

		return dp.get(subProblemKey);
	}

	public static void main(String[] args) {
		MaxSumIncreasingSubSequence msis = new MaxSumIncreasingSubSequence();
		int[] nums = { 4, 1, 2, 6, 10, 1, 12 };
		System.out.println(msis.findMSIS(nums));
		nums = new int[] { -4, 10, 3, 7, 15 };
		System.out.println(msis.findMSIS(nums));
	}
}
