package com.educative.patterns.chapter17;

import java.util.*;

class KthSmallestNumberMaxHeap {

	public static int findKthSmallestNumber(int[] nums, int k) {
		k=k+1;
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((n1, n2) -> n2 - n1);
		// put first k numbers in the max heap
		for (int i = 0; i < k; i++)
			maxHeap.add(nums[i]);

		// go through the remaining numbers of the array, if the number from the array
		// is smaller than the
		// top (biggest) number of the heap, remove the top number from heap and add the
		// number from array
		for (int i = k; i < nums.length; i++) {
			if (nums[i] < maxHeap.peek()) {
				maxHeap.poll();
				maxHeap.add(nums[i]);
			}
		}

		// the root of the heap has the Kth smallest number
		return maxHeap.peek();
	}

	public static void main(String[] args) {
		int result = KthSmallestNumberMaxHeap.findKthSmallestNumber(new int[] { 1,2,3,4,5,6,7 }, 3);
		System.out.println("Kth smallest number is: " + result);

		// since there are two 5s in the input array, our 3rd and 4th smallest numbers
		// should be a '5'
		result = KthSmallestNumberMaxHeap.findKthSmallestNumber(new int[] { 1, 5, 12, 2, 11, 5 }, 4);
		System.out.println("Kth smallest number is: " + result);

		result = KthSmallestNumberMaxHeap.findKthSmallestNumber(new int[] { 5, 12, 11, -1, 12 }, 3);
		System.out.println("Kth smallest number is: " + result);
	}
}
