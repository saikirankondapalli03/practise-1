package com.educative.patterns.chapter13.findtopk;

import java.util.PriorityQueue;

class SumOfElements {

	public static int findSumOfElements(int[] nums, int k1, int k2) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
		// insert all numbers to the min heap
		for (int i = 0; i < nums.length; i++)
			minHeap.add(nums[i]);

		// remove k1 small numbers from the min heap
		for (int i = 0; i < k1; i++)
			minHeap.poll();

		int elementSum = 0;
		// sum next k2-k1-1 numbers
		for (int i = 0; i < k2 - k1 - 1; i++)
			elementSum += minHeap.poll();

		return elementSum;
	}

	public static void main(String[] args) {
		int result = SumOfElements.findSumOfElementsApproach2(new int[] { 3, 5, 8, 7,2 }, 1, 4);
		System.out.println("Sum of all numbers between k1 and k2 smallest numbers: " + result);

		result = SumOfElements.findSumOfElementsApproach2(new int[] { 3, 5, 8, 11,1 ,13 }, 1, 3);
		System.out.println("Sum of all numbers between k1 and k2 smallest numbers: " + result);
	}
	
	public static int findSumOfElementsApproach2(int[] nums, int k1, int k2) {
	    PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((n1, n2) -> n2 - n1);
	    // keep smallest k2 numbers in the max heap
	    for (int i = 0; i < nums.length; i++) {
	      if (i < k2 - 1) {
	        maxHeap.add(nums[i]);
	      } else if (nums[i] < maxHeap.peek()) {
	        maxHeap.poll(); // as we are interested only in the smallest k2 numbers
	        maxHeap.add(nums[i]);
	      }
	    }

	    // get the sum of numbers between k1 and k2 indices
	    // these numbers will be at the top of the max heap
	    int elementSum = 0;
	    for (int i = 0; i < k2 - k1 - 1; i++)
	      elementSum += maxHeap.poll();

	    return elementSum;
	  }

}
