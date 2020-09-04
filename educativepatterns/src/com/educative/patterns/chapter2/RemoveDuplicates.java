package com.educative.patterns.chapter2;

class RemoveDuplicates {

	public static int remove(int[] arr) {
		int nextNonDuplicate = 1; // index of the next non-duplicate element
		for (int i = 1; i < arr.length; i++) {
			if (arr[nextNonDuplicate - 1] != arr[i]) {
				arr[nextNonDuplicate] = arr[i];
				nextNonDuplicate++;
			}
		}

		return nextNonDuplicate;
	}

	public static void main(String[] args) {
		int[] arr = new int[] {0,0,1,1,1,2,2,3,3,4};
		System.out.println(RemoveDuplicates.remove(arr));

		arr = new int[] { 2, 2, 2, 11 };
		System.out.println(RemoveDuplicates.remove(arr));
	}
}
