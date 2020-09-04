package com.educative.tc.chapter3;

public class BinarySearch {
	public static void main(String args[]) {
		int[] input = new int[] { 1, 3, 4, 6, 7, 101, 1009 };
		System.out.println(binarySearch(0, input.length - 1, 1009, input));
		System.out.println(binarySearch(0, input.length - 1, -1009, input));
		System.out.println(binarySearch(0, input.length - 1, 5, input));
		System.out.println(binarySearch(0, input.length - 1, 6, input));
	}

	/**
	 * start and end are inclusive indices
	 *
	 * @param start
	 * @param end
	 * @param target
	 * @param input
	 * @return
	 */
	public static boolean binarySearch(int start, int end, int target, int[] input) {

		if (start > end) {
			return false;
		}

		int mid = (start + end) / 2;

		if (input[mid] == target) {
			return true;
		} else if (input[mid] > target) {
			return binarySearch(start, mid - 1, target, input);
		} else {
			return binarySearch(mid + 1, end, target, input);
		}
	}

}
