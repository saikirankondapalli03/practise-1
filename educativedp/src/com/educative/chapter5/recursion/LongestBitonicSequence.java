package com.educative.chapter5.recursion;

public class LongestBitonicSequence {
	private int findLBSLength(int[] nums) {
		int maxLength = 0;
		for (int i = 0; i < nums.length; i++) {
			int c1 = findLDSLength(nums, i, -1);
			int c2 = findLDSLengthRev(nums, i, -1);
			maxLength = Math.max(maxLength, c1 + c2 - 1);
		}
		return maxLength;
	}

	// find the longest decreasing subsequence from currentIndex till the end of the
	// array
	private int findLDSLength(int[] nums, int currentIndex, int previousIndex) {
		if (currentIndex == nums.length)
			return 0;

		// include nums[currentIndex] if it is smaller than the previous number
		int c1 = 0;
		if (previousIndex == -1 || nums[currentIndex] < nums[previousIndex])
			c1 = 1 + findLDSLength(nums, currentIndex + 1, currentIndex);

		// excluding the number at currentIndex
		int c2 = findLDSLength(nums, currentIndex + 1, previousIndex);

		return Math.max(c1, c2);
	}

	// find the longest decreasing subsequence from currentIndex till the beginning
	// of the array
	private int findLDSLengthRev(int[] nums, int currentIndex, int previousIndex) {
		if (currentIndex < 0)
			return 0;

		// include nums[currentIndex] if it is smaller than the previous number
		int c1 = 0;
		if (previousIndex == -1 || nums[currentIndex] < nums[previousIndex])
			c1 = 1 + findLDSLengthRev(nums, currentIndex - 1, currentIndex);

		// excluding the number at currentIndex
		int c2 = findLDSLengthRev(nums, currentIndex - 1, previousIndex);

		return Math.max(c1, c2);
	}

	public static void main(String[] args) {
		LongestBitonicSequence lbs = new LongestBitonicSequence();
		int[] nums = { 4, 2, 3, 6, 10, 1, 12 };
		//LDS => 3,  1, 1, 1, 1, 1, 1 
		//LDS => 1,  1, 2, 3, 4, 1, 5
		        //3,1,2,3,4,1,5
		System.out.println(lbs.findLBSLength(nums));
		nums = new int[] { 4, 2, 5, 9, 7, 6, 10, 3, 1 };
		System.out.println(lbs.findLBSLength(nums));
	}
}
