package com.educative.patterns.chapter13.findtopk;

import java.util.*;

class KthLargestNumberInStream {
	PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
	final int size;

	public KthLargestNumberInStream(int[] nums, int size) {
		this.size = size;
		// add the numbers in the min heap
		for (int i = 0; i < nums.length; i++)
			add(nums[i]);
	}

	public int add(int num) {
		// add the new number in the min heap
		minHeap.add(num);

		// if heap has more than 'k' numbers, remove one number
		if (minHeap.size() > this.size)
			minHeap.poll();

		// return the 'Kth largest number
		return minHeap.peek();
	}

	public static void main(String[] args) {
		int[] input = new int[] { 3, 1, 5, 12, 2, 11 };
		KthLargestNumberInStream kthLargestNumber = new KthLargestNumberInStream(input, 4);
		System.out.println("4th largest number is: " + kthLargestNumber.add(6));
		System.out.println("4th largest number is: " + kthLargestNumber.add(13));
		System.out.println("4th largest number is: " + kthLargestNumber.add(4));
	}
}