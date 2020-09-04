package com.educative.interviewrefersher.chapter7;

import java.util.Arrays;

class Heap {

	private void maxHeapify(int[] heapArray, int index, int heapSize) {
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
			if (largest != index) {
				// swap parent with largest child
				int temp = heapArray[index];
				heapArray[index] = heapArray[largest];
				heapArray[largest] = temp;
				index = largest;
			} else
				break; // if heap property is satisfied
		} // end of while
	}

	public void buildMaxHeap(int[] heapArray, int heapSize) {
		// swap largest child to parent node
		for (int i = (heapSize - 1) / 2; i >= 0; i--) {
			maxHeapify(heapArray, i, heapSize);
		}
	}

	private void minHeapify(int[] heapArray, int index, int heapSize) {
		int smallest = index;

		while (smallest < heapSize / 2) { // check parent nodes only
			int left = (2 * index) + 1; // left child
			int right = (2 * index) + 2; // right child
			if (left < heapSize && heapArray[left] < heapArray[index]) {
				smallest = left;
			}

			if (right < heapSize && heapArray[right] < heapArray[smallest]) {
				smallest = right;
			}

			if (smallest != index) { // swap parent with smallest child
				int temp = heapArray[index];
				heapArray[index] = heapArray[smallest];
				heapArray[smallest] = temp;
				index = smallest;
			} else {
				break; // if heap property is satisfied
			}

		} // end of while
	}

	public void buildMinHeap(int[] heapArray, int heapSize) {

		// swap smallest child to parent node
		for (int i = (heapSize - 1) / 2; i >= 0; i--) {
			 minHeapify(heapArray, i, heapSize);
		}
	}

	public static void main(String[] args) {
		int[] heapArray = { 1, 4, 7, 12, 15, 14, 9, 2, 3, 16 };

		System.out.println("Before heapify: " + Arrays.toString(heapArray));
		new Heap().buildMaxHeap(heapArray, heapArray.length);
		System.out.println("After heapify: " + Arrays.toString(heapArray));
	}
}