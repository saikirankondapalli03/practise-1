package com.educative.patterns.chapter13.findtopk;

import java.util.*;

class TopKFrequentNumbers {

	public static List<Integer> findTopKFrequentNumbers(int[] nums, int k) {
		// find the frequency of each number
		Map<Integer, Integer> numFrequencyMap = new HashMap<>();
		for (int n : nums)
			numFrequencyMap.put(n, numFrequencyMap.getOrDefault(n, 0) + 1);

		PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<Map.Entry<Integer, Integer>>(
				(e1, e2) -> e1.getValue() - e2.getValue());

		// go through all numbers of the numFrequencyMap and push them in the minHeap,
		// which will have
		// top k frequent numbers. If the heap size is more than k, we remove the
		// smallest (top) number
		for (Map.Entry<Integer, Integer> entry : numFrequencyMap.entrySet()) {
			minHeap.add(entry);
			if (minHeap.size() > k) {
				minHeap.poll();
			}
		}

		// create a list of top k numbers
		List<Integer> topNumbers = new ArrayList<>(k);
		while (!minHeap.isEmpty()) {
			topNumbers.add(minHeap.poll().getKey());
		}
		return topNumbers;
	}

	public static void main(String[] args) {
		List<Integer> result = TopKFrequentNumbers.findTopKFrequentNumbers(new int[] { 1, 3, 5, 12, 11, 12, 11 }, 2);
		System.out.println("Here are the K frequent numbers: " + result);

		result = TopKFrequentNumbers.findTopKFrequentNumbers(new int[] { 5, 12, 11, 3, 11 }, 2);
		System.out.println("Here are the K frequent numbers: " + result);
	}
}
