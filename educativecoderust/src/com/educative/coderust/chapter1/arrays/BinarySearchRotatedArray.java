package com.educative.coderust.chapter1.arrays;

public class BinarySearchRotatedArray {
	public static int binarySearchRecursive(int[] arr, int start, int end, int key) {
		// assuming all the keys are unique.

		if (start > end) {
			return -1;
		}

		int mid = start + (end - start) / 2;

		if (arr[mid] == key) {
			return mid;
		}
		// if element at start is less than mid
		// if key is less than mid and it is greater than start
		// then key answer lies in start to mid-1

		if (arr[start] <= arr[mid] && key <= arr[mid] && key >= arr[start]) {
			return binarySearchRecursive(arr, start, mid - 1, key);
		}

		// if element in mid is less than end
		// if key is greater than element at mid
		// if key is less than element at end
		// answer lies in mid+1 to end
		else if (arr[mid] <= arr[end] && key >= arr[mid] && key <= arr[end]) {
			return binarySearchRecursive(arr, mid + 1, end, key);
		}

		else if (arr[end] <= arr[mid]) {
			return binarySearchRecursive(arr, mid + 1, end, key);
		}

		else if (arr[start] >= arr[mid]) {
			return binarySearchRecursive(arr, start, mid - 1, key);
		}

		return -1;
	}

	static int binarySearchRotated(int[] arr, int key) {
		return binarySearchRecursive(arr, 0, arr.length - 1, key);
	}

	static int binarySearchRotatedIterative(int[] arr, int key) {
		int start = 0;
		int mid = 0;
		int end = arr.length - 1;

		if (start > end)
			return -1;

		while (start <= end) {

			mid = start + (end - start) / 2;

			if (arr[mid] == key)
				return mid;
			// if start element is less than mid element,
			// if key you want is less than mid
			// if key you search is greater than start
			if (arr[start] <= arr[mid] && key <= arr[mid] && key >= arr[start])
				end = mid - 1;

			// if element in mid is less than end
			// if key is greater than element at mid
			// if key is less than element at end
			// answer lies in mid+1 to end
			else if (arr[mid] <= arr[end] && key >= arr[mid] && key <= arr[end])
				start = mid + 1;

			else if (arr[start] <= arr[mid] && arr[mid] <= arr[end] && key > arr[end])
				start = mid + 1;
			
			//
			else if (arr[end] <= arr[mid])
				start = mid + 1;

			else if (arr[start] >= arr[mid])
				end = mid - 1;

			else
				return -1;
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] v1 = { 176, 188, 199, 200, 210, 222, 1, 10, 20, 47, 59, 63, 75, 88, 99, 107, 120, 133, 155, 162 };
		System.out.println("Key(3) found at: " + binarySearchRotated(v1, 200));
		// System.out.println("Key(6) found at: " + binarySearchRotated(v1, 6));

		// int[] v2 = { 4, 5, 6, 1, 2, 3 };
		// System.out.println("Key(3) found at: " + binarySearchRotated(v2, 3));
		// System.out.println("Key(6) found at: " + binarySearchRotated(v2, 6));
	}
}
