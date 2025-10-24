package com.patterns.maxminheapcombo;

import java.util.PriorityQueue;

/*
 * PROBLEM: Next Interval
 * 
 * Given an array of intervals, find the next interval for each interval.
 * 
 * The "next interval" of an interval I is the interval J such that:
 * 1. J.start >= I.end (J starts after or when I ends)
 * 2. J.start is the smallest among all such valid intervals
 * 
 * Return an array where result[i] is the index of the next interval for intervals[i].
 * If no next interval exists, return -1.
 * 
 * Example 1:
 * Input: [[2,3], [3,4], [5,6]]
 * Output: [1, 2, -1]
 * Explanation:
 * - Interval [2,3]: Next is [3,4] at index 1 (starts at 3 >= 3)
 * - Interval [3,4]: Next is [5,6] at index 2 (starts at 5 >= 4) 
 * - Interval [5,6]: No next interval, so -1
 * 
 * Example 2:
 * Input: [[3,4], [1,5], [4,6]]
 * Output: [2, -1, -1]
 * Explanation:
 * - Interval [3,4]: Next is [4,6] at index 2 (starts at 4 >= 4)
 * - Interval [1,5]: No interval starts >= 5, so -1
 * - Interval [4,6]: No interval starts >= 6, so -1
 */

class NextInterval {
	// ORIGINAL APPROACH: Two Heaps (Complex)
	// Time Complexity: O(n log n) - each interval processed once, heap operations are O(log n)
	// Space Complexity: O(n) - two heaps storing n elements each
	public static int[] findNextInterval(Interval[] intervals) {
		int n = intervals.length;
		
		// TEMPLATE: Two heaps for filter + optimize pattern
		// filterHeap: Process intervals by end time (largest end first)
		// optimizeHeap: Find candidates with smallest valid start time
		PriorityQueue<Integer> candidatesByStartTime = new PriorityQueue<>(n, 
			(i1, i2) -> intervals[i2].start - intervals[i1].start); // Max-heap by start
		PriorityQueue<Integer> intervalsByEndTime = new PriorityQueue<>(n, 
			(i1, i2) -> intervals[i2].end - intervals[i1].end);   // Max-heap by end
		
		int[] nextIntervalIndices = new int[n];
		
		// TEMPLATE STEP 1: Initialize - put all intervals in both heaps
		for (int i = 0; i < intervals.length; i++) {
			candidatesByStartTime.offer(i);
			intervalsByEndTime.offer(i);
		}

		// TEMPLATE STEP 2: Main processing loop
		for (int i = 0; i < n; i++) {
			// Get interval with largest end time (process in reverse end order)
			int currentIntervalIndex = intervalsByEndTime.poll();
			nextIntervalIndices[currentIntervalIndex] = -1; // Default: no next interval
			
			// TEMPLATE STEP 3: Find eligible candidates (start >= current.end)
			if (!candidatesByStartTime.isEmpty() && 
				intervals[candidatesByStartTime.peek()].start >= intervals[currentIntervalIndex].end) {
				
				// Find the candidate with smallest valid start time
				int bestCandidateIndex = candidatesByStartTime.poll();
				
				// Remove all candidates with larger start times (keep smallest)
				while (!candidatesByStartTime.isEmpty() && 
					   intervals[candidatesByStartTime.peek()].start >= intervals[currentIntervalIndex].end) {
					bestCandidateIndex = candidatesByStartTime.poll();
				}
				
				nextIntervalIndices[currentIntervalIndex] = bestCandidateIndex;
				
				// Put back the best candidate (might be next for other intervals)
				candidatesByStartTime.add(bestCandidateIndex);
			}
		}
		return nextIntervalIndices;
	}

	public static void main(String[] args) {
		Interval[] intervals = new Interval[] { new Interval(2,3), new Interval(3,4), new Interval(5, 6) };
		int[] result = NextInterval.findNextInterval(intervals);
		System.out.print("Next interval indices are: ");
		for (int index : result)
			System.out.print(index + " ");
		System.out.println();

		intervals = new Interval[] { new Interval(3, 4), new Interval(1, 5), new Interval(4, 6) };
		result = NextInterval.findNextInterval(intervals);
		System.out.print("Next interval indices are: ");
		for (int index : result)
			System.out.print(index + " ");
	}
}