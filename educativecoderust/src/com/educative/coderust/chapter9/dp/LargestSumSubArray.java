package com.educative.coderust.chapter9.dp;

class LargestSumSubArray {
	static int findMaxSumSubArray(int[] A) {
		if (A.length < 1) {
			return 0;
		}

		int currMax = A[0];
		int globalMax = A[0];
		for (int i = 1; i < A.length; ++i) {

			if (currMax < 0) {
				currMax = A[i];
			} else {
				currMax += A[i];
			}

			if (globalMax < currMax) {
				globalMax = currMax;
			}
		}

		return globalMax;
	}

	public static void main(String[] args) {
		int[] v = new int[] {-2, -3, 4, -1, -2, 1, 5, -3};
		System.out.println("Sum of largest subarray: " + findMaxSumSubArray(v));
	}
}