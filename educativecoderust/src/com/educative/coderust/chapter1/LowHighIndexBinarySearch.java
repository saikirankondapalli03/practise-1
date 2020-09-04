package com.educative.coderust.chapter1;

import java.util.Arrays;
import java.util.List;

public class LowHighIndexBinarySearch {
	static int findLowIndex(List<Integer> arr, int key) {
		int low = 0;
		int high = arr.size() - 1;
		int mid = high / 2;

		while (low <= high) {

			int midElem = arr.get(mid);

			if (midElem < key) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}

			mid = low + (high - low) / 2;
		}

		if (low < arr.size() && arr.get(low) == key) {
			return low;
		}

		return -1;
	}

	static int findHighIndex(List<Integer> arr, int key) {
		int low = 0;
		int high = arr.size() - 1;
		int mid = high / 2;

		while (low <= high) {

			int midElem = arr.get(mid);

			if (midElem <= key) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}

			mid = low + (high - low) / 2;
		}

		if (high == -1) {
			return high;
		}

		if (high < arr.size() && arr.get(high) == key) {
			return high;
		}

		return -1;
	}

	public static void main(String[] args) {
		List<Integer> array = Arrays.asList(1,2,5,5,5,5,5,5,5,5,20);
		int key = 5;
		int low = findLowIndex(array, key);
		int high = findHighIndex(array, key);
		System.out.println("Low Index of " + key + ": " + low);
		System.out.println("High Index of " + key + ": " + high);

		key = -2;
		low = findLowIndex(array, key);
		high = findHighIndex(array, key);
		System.out.println("Low Index of " + key + ": " + low);
		System.out.println("High Index of " + key + ": " + high);
	}
}
