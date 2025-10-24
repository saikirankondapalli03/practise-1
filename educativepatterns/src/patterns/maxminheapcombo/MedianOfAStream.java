package patterns.maxminheapcombo;

import java.util.PriorityQueue;

/*
 * PROBLEM: Find Median from Data Stream
 * 
 * Design a data structure that supports the following two operations:
 * 1. insertNum(int num) - Add a number to the data structure
 * 2. findMedian() - Return the median of all numbers added so far
 * 
 * The median is the middle value in an ordered integer list.
 * - If the size of the list is even, return the average of the two middle numbers
 * - If the size of the list is odd, return the middle number
 * 
 * Example:
 * insertNum(3) → [3], median = 3
 * insertNum(1) → [1,3], median = (1+3)/2 = 2.0
 * insertNum(5) → [1,3,5], median = 3
 * insertNum(4) → [1,3,4,5], median = (3+4)/2 = 3.5
 * 
 * Follow-up: Can you solve it in O(log n) time for insertNum and O(1) for findMedian?
 */

class MedianOfAStream {

	// TEMPLATE CORRELATION: Two heaps for filter + optimize pattern
	// Filter: Split numbers into smaller/larger halves
	// Optimize: Get max of smaller half, min of larger half
	PriorityQueue<Integer> maxHeap; // Max-heap: smaller half (optimize for largest in smaller half)
	PriorityQueue<Integer> minHeap; // Min-heap: larger half (optimize for smallest in larger half)

	public MedianOfAStream() {
		// Initialize both heaps
		maxHeap = new PriorityQueue<>((a, b) -> b - a); // Max-heap for smaller numbers
		minHeap = new PriorityQueue<>((a, b) -> a - b);  // Min-heap for larger numbers
	}

	public void insertNum(int num) {
		// TEMPLATE STEP 1: Filter - decide which heap based on criteria
		// Criteria: smaller numbers go to maxHeap, larger to minHeap
		if (maxHeap.isEmpty() || maxHeap.peek() >= num)
			maxHeap.add(num);  // Add to smaller half
		else
			minHeap.add(num);  // Add to larger half

		// TEMPLATE STEP 2: Maintain balance (move items between heaps)
		// Constraint: maxHeap can have at most 1 more element than minHeap
		rebalance();
	}

	private void rebalance() {
		// TEMPLATE PATTERN: Move items between heaps to maintain constraints
		// Move from maxHeap to minHeap if maxHeap too large
		if (maxHeap.size() > minHeap.size() + 1)
			minHeap.add(maxHeap.poll());
		// Move from minHeap to maxHeap if minHeap larger
		else if (maxHeap.size() < minHeap.size())
			maxHeap.add(minHeap.poll());
	}

	public double findMedian() {
		// TEMPLATE STEP 3: Extract optimal result from heaps
		// Optimize: Get the middle element(s) efficiently
		if (maxHeap.size() == minHeap.size()) {
			// Even count: average of max(smaller half) + min(larger half)
			return maxHeap.peek() / 2.0 + minHeap.peek() / 2.0;
		}
		// Odd count: max(smaller half) is the median
		return maxHeap.peek();
	}

	public static void main(String[] args) {
		MedianOfAStream medianOfAStream = new MedianOfAStream();
		medianOfAStream.insertNum(4);
		System.out.println("The median is: " + medianOfAStream.findMedian());
		medianOfAStream.insertNum(2);
		medianOfAStream.insertNum(5);
		System.out.println("The median is: " + medianOfAStream.findMedian());
		medianOfAStream.insertNum(8);
		medianOfAStream.insertNum(9);
		
		System.out.println("The median is: " + medianOfAStream.findMedian());
	}
}
