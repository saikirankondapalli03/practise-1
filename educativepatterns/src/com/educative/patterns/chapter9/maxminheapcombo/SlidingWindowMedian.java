package com.educative.patterns.chapter9.maxminheapcombo;

import java.util.*;

class SlidingWindowMedian {
	PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
	PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);

	public double[] findSlidingWindowMedian(int[] nums, int k) {
		double[] result = new double[nums.length - k + 1];
		for (int windowStart = 0; windowStart < nums.length; windowStart++) {
			if (maxHeap.isEmpty() || maxHeap.peek() >= nums[windowStart]) {
				maxHeap.add(nums[windowStart]);
			} else {
				minHeap.add(nums[windowStart]);
			}
			rebalanceHeaps();

			int resultIndex = windowStart - k + 1;
			if (resultIndex >= 0) { // if we have at least 'k' elements in the sliding window
				// add the median to the the result array
				if (maxHeap.size() == minHeap.size()) {
					// we have even number of elements, take the average of middle two elements
					result[resultIndex] = maxHeap.peek() / 2.0 + minHeap.peek() / 2.0;
				} else { // because max-heap will have one more element than the min-heap
					result[resultIndex] = maxHeap.peek();
				}

				// remove the the element going out of the sliding window
				int elementToBeRemoved = nums[resultIndex];
				int maxpeak=maxHeap.peek();
				if (elementToBeRemoved <= maxpeak) {
					maxHeap.remove(elementToBeRemoved);
				} else {
					minHeap.remove(elementToBeRemoved);
				}
				rebalanceHeaps();
			}
		}
		return result;
	}

	private void rebalanceHeaps() {
		// either both the heaps will have equal number of elements or max-heap will
		// have
		// one more element than the min-heap
		if (maxHeap.size() > minHeap.size() + 1)
			minHeap.add(maxHeap.poll());
		else if (maxHeap.size() < minHeap.size())
			maxHeap.add(minHeap.poll());
	}

	public static void main(String[] args) {
		SlidingWindowMedian s = new SlidingWindowMedian();
		//s.maxHeap.add(3);
		//s.maxHeap.add(1);
		//s.maxHeap.add(5);
		double[] result = s.findSlidingWindowMedian(new int[] { 1, 2, -1, 3, 5 },3);//new int[] { 1, 3, 2, 6, -1, 4, 1, 8, 2 }, 5
		System.out.print("Sliding window medians are: ");
		for (double num : result)
			System.out.print(num + " ");
		System.out.println();

		s = new SlidingWindowMedian();
		result = s.findSlidingWindowMedian(new int[] { 1, 2, -1, 3, 5 }, 2);
		System.out.print("Sliding window medians are: ");
		for (double num : result)
			System.out.print(num + " ");
	}

}
