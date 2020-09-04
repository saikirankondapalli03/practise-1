package com.educative.chapter5.recursion;

public class LongestAlternatingSubsequence {

	public static int maxAlternatingSubseqLength(int[] array) {

		int prev = -1, current = 0, maxLengthSoFar = 0;

		boolean isAsc = true;
		// 1, 3, 2, 4
		int v1 = maxAlternatingSubseqLength(array, prev, current, maxLengthSoFar, isAsc);
		// 2,1,4, 3
		int v2 = maxAlternatingSubseqLength(array, prev, current, maxLengthSoFar, !isAsc);

		return Math.max(v1, v2);
	}

	public int findLASLength(int[] nums) {
		// we have to start with two recursive calls, one where we will consider that
		// the first element is
		// bigger than the second element and one where the first element is smaller
		// than the second element
		int v1=findLASLengthRecursive(nums, -1, 0, true);
		int v2=findLASLengthRecursive(nums, -1, 0, false);
		return Math.max(v1,v2);
	}//3,2,1,4

	private int findLASLengthRecursive(int[] nums, int previousIndex, int currentIndex, boolean isAsc) {
		if (currentIndex == nums.length)
			return 0;

		int c1 = 0;
		// if ascending, the next element should be bigger
		if (isAsc) {
			if (previousIndex == -1 || nums[previousIndex] < nums[currentIndex])
				c1 = 1 + this.findLASLengthRecursive(nums, currentIndex, currentIndex + 1, !isAsc);
		} else { // if descending, the next element should be smaller
			if (previousIndex == -1 || nums[previousIndex] > nums[currentIndex])
				c1 = 1 + this.findLASLengthRecursive(nums, currentIndex, currentIndex + 1, !isAsc);
		}
		// skip the current element
		int c2 = this.findLASLengthRecursive(nums, previousIndex, currentIndex + 1, isAsc);
		return Math.max(c1, c2);
	}

	private static int maxAlternatingSubseqLength(int[] array, int prev, int current, int maxLengthSoFar,
			boolean isAsc) {

		if (current >= array.length) {
			return maxLengthSoFar;
		}

		if (isAsc) {
			if (prev == -1 || array[prev] < array[current]) {
				return maxAlternatingSubseqLength(array, current, current + 1, maxLengthSoFar + 1, !isAsc);
			}

			return maxAlternatingSubseqLength(array, prev, current + 1, maxLengthSoFar, isAsc);
		}

		else {
			if (prev == -1 || array[prev] > array[current]) {
				return maxAlternatingSubseqLength(array, current, current + 1, maxLengthSoFar + 1, !isAsc);
			}

			return maxAlternatingSubseqLength(array, prev, current + 1, maxLengthSoFar, isAsc);

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LongestAlternatingSubsequence las = new LongestAlternatingSubsequence();
		int[] nums = { 1,3,2, 4 };
		System.out.println(maxAlternatingSubseqLength(nums));
		//nums = new int[] { 3, 2, 1, 4 };
		System.out.println(las.findLASLength(nums));
		nums = new int[] { 1, 3, 2, 4 };
		System.out.println(las.findLASLength(nums));
	}

}
