package com.educative.patterns.chapter11.binarysearch;

class FloorOfANumber {

	public static int searchFloorOfANumber(int[] arr, int key) {
		if (key < arr[0]) // if the 'key' is smaller than the smallest element
			return -1;

		int start = 0, end = arr.length - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (key < arr[mid]) {
				end = mid - 1;
			} else if (key > arr[mid]) {
				start = mid + 1;
			} else { // found the key
				return mid;
			}
		}
		// since the loop is running until 'start <= end', so at the end of the while
		// loop, 'start == end+1'
		// we are not able to find the element in the given array, so the next smaller
		// number will be arr[end]
		return end;
	}

	public static void main(String[] args) {
		System.out.println(FloorOfANumber.searchFloorOfANumber(new int[] { 4, 6, 10 }, 6));
		System.out.println(FloorOfANumber.searchFloorOfANumber(new int[] { 1, 3, 8, 10, 15 }, 12));
		System.out.println(FloorOfANumber.searchFloorOfANumber(new int[] { 4, 6, 10 }, 17));
		System.out.println(FloorOfANumber.searchFloorOfANumber(new int[] { 4, 6, 10 }, -1));
	}
}
