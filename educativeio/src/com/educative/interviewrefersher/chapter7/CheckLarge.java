package com.educative.interviewrefersher.chapter7;

//Build minHeap of the given array. (We already covered that in previous lesson)
//Extract the minimum element/root and add it to the result
//Reduce the length of array and repeatedly build minHeap till we reach K.
class CheckLarge {
	public static int[] findKLargest(int[] arr, int k) {
		int arraySize = arr.length;
		if (k <= arraySize) {
			int[] result = new int[k]; // build the result array of size = k
			for (int i = 0; i < k; i++) {
				buildMaxHeap(arr, arraySize);
				result[i] = arr[0];
				arr[0] = arr[--arraySize];
			}
			return result;
		}
		System.out.println("Value of k = " + k + "Out of Bounds");
		return arr;
	}

	private static void buildMaxHeap(int[] heapArray, int heapSize) {

		// swap largest child to parent node
		for (int i = (heapSize - 1) / 2; i >= 0; i--) {
			maxHeapify(heapArray, i, heapSize);
		}
	}

	private static void maxHeapify(int[] heapArray, int index, int heapSize) {
		int largest = index;

		while (largest < heapSize / 2) { // check parent nodes only
			int left = (2 * index) + 1; // left child
			int right = (2 * index) + 2; // right child
			if (left < heapSize && heapArray[left] > heapArray[index]) {
				largest = left;
			}

			if (right < heapSize && heapArray[right] > heapArray[largest]) {
				largest = right;
			}

			if (largest != index) { // swap parent with largest child
				int temp = heapArray[index];
				heapArray[index] = heapArray[largest];
				heapArray[largest] = temp;
				index = largest;
			} else {
				break; // if heap property is satisfied
			}

		} // end of while
	}

	public static void main(String args[]) {
		int[] input = { 9, 4, 7, 1, -2, 6, 5 };
		int[] output = findKLargest(input, 2);

		for (int i = 0; i < output.length; i++)
			System.out.println(output[i]);
	}

}
