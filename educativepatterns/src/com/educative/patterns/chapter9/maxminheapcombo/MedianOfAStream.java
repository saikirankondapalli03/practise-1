package com.educative.patterns.chapter9.maxminheapcombo;

import java.util.PriorityQueue;

class MedianOfAStream {

	PriorityQueue<Integer> maxHeap; // containing first half of numbers
	PriorityQueue<Integer> minHeap; // containing second half of numbers

	public MedianOfAStream() {
		maxHeap = new PriorityQueue<>((a, b) -> b - a);
		minHeap = new PriorityQueue<>((a, b) -> a - b);
	}

	public void insertNum(int num) {
		if (maxHeap.isEmpty() || maxHeap.peek() >= num)
			maxHeap.add(num);
		else
			minHeap.add(num);

		// either both the heaps will have equal number of elements or max-heap will
		// have one
		// more element than the min-heap
		rebalance();
	}

	private void rebalance() {
		if (maxHeap.size() > minHeap.size() + 1)
			minHeap.add(maxHeap.poll());
		else if (maxHeap.size() < minHeap.size())
			maxHeap.add(minHeap.poll());
	}

	public double findMedian() {
		if (maxHeap.size() == minHeap.size()) {
			// we have even number of elements, take the average of middle two elements
			return maxHeap.peek() / 2.0 + minHeap.peek() / 2.0;
		}
		// because max-heap will have one more element than the min-heap
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
