package com.educative.patterns.chapter11.binarysearch;

class ArrayReader {
	int[] arr;

	ArrayReader(int[] arr) {
		this.arr = arr;
	}

	public int get(int index) {
		if (index >= arr.length)
			return Integer.MAX_VALUE;
		return arr[index];
	}
}

class SearchInfiniteSortedArray {

	public static int search(ArrayReader reader, int key) {
		// find the proper bounds first
		int start = 0, end = 1;
		while (reader.get(end) < key) {
			int newStart = end + 1;
			end += (end - start + 1) * 2; // increase to double the bounds size
			start = newStart;
		}
		return binarySearch(reader, key, start, end);
	}

	private static int binarySearch(ArrayReader reader, int key, int start, int end) {
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (key < reader.get(mid)) {
				end = mid - 1;
			} else if (key > reader.get(mid)) {
				start = mid + 1;
			} else { // found the key
				return mid;
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		ArrayReader reader = new ArrayReader(new int[] { 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30 });
		System.out.println(SearchInfiniteSortedArray.search(reader, 16));
		System.out.println(SearchInfiniteSortedArray.search(reader, 11));
		reader = new ArrayReader(new int[] { 1, 3, 8, 10, 15 });
		System.out.println(SearchInfiniteSortedArray.search(reader, 15));
		System.out.println(SearchInfiniteSortedArray.search(reader, 200));
	}
}