package com.patterns.maxminheapcombo;

import java.util.*;



public class NextIntervalSimple {

    // APPROACH 1: Brute Force - First Principles
    // Time Complexity: O(n²) - for each interval, check all others
    // Space Complexity: O(1) - only result array, no extra data structures
    public static int[] findNextIntervalBruteForce(Interval[] intervals) {
        int n = intervals.length;
        int[] result = new int[n];

        // For each interval, find the next one
        for (int i = 0; i < n; i++) {
            result[i] = -1; // Default: no next interval
            int minStart = Integer.MAX_VALUE;

            // Check all other intervals
            for (int j = 0; j < n; j++) {
                // Valid next interval: starts >= current.end AND has smallest start
                if (intervals[j].start >= intervals[i].end && intervals[j].start < minStart) {
                    minStart = intervals[j].start;
                    result[i] = j;
                }
            }
        }
        return result;
    }

    // APPROACH 2: Sort + Binary Search - Optimized First Principles
    // Time Complexity: O(n log n) - O(n log n) for sorting + O(n log n) for n binary searches
    // Space Complexity: O(n) - for sortedByStart array
    public static int[] findNextIntervalOptimized(Interval[] intervals) {
        int n = intervals.length;

        // Create array of (start_time, original_index) and sort by start time
        int[][] sortedByStart = new int[n][2];
        for (int i = 0; i < n; i++) {
            sortedByStart[i] = new int[]{intervals[i].start, i};
        }
        Arrays.sort(sortedByStart, (a, b) -> a[0] - b[0]);

        int[] result = new int[n];

        // For each interval, binary search for next valid start
        for (int i = 0; i < n; i++) {
            int targetStart = intervals[i].end;
            int nextIndex = binarySearchNextStart(sortedByStart, targetStart);

            result[i] = (nextIndex == -1) ? -1 : sortedByStart[nextIndex][1];
        }

        return result;
    }

    // Binary search helper
    // Time Complexity: O(log n) - standard binary search
    // Space Complexity: O(1) - only variables
    private static int binarySearchNextStart(int[][] sortedByStart, int target) {
        int left = 0, right = sortedByStart.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (sortedByStart[mid][0] >= target) {
                result = mid;  // Found valid candidate
                right = mid - 1;  // Look for smaller valid start
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    // APPROACH 3: Single Pass with TreeMap - Clean and Efficient
    // Time Complexity: O(n log n) - O(n log n) for building TreeMap + O(n log n) for n ceilingKey operations
    // Space Complexity: O(n) - for TreeMap storage
    public static int[] findNextIntervalTreeMap(Interval[] intervals) {
        int n = intervals.length;

        // TreeMap: start_time -> list of indices with that start time
        TreeMap<Integer, List<Integer>> startTimeMap = new TreeMap<>();

        // Build the map
        for (int i = 0; i < n; i++) {
            startTimeMap.computeIfAbsent(intervals[i].start, k -> new ArrayList<>()).add(i);
        }

        int[] result = new int[n];

        // For each interval, find next valid start time
        for (int i = 0; i < n; i++) {
            Integer nextStart = startTimeMap.ceilingKey(intervals[i].end);
            result[i] = (nextStart == null) ? -1 : startTimeMap.get(nextStart).get(0);
        }

        return result;
    }

    public static void main(String[] args) {
        Interval[] intervals = {new Interval(2, 3), new Interval(3, 4), new Interval(5, 6)};

        System.out.println("Brute Force: " + Arrays.toString(findNextIntervalBruteForce(intervals)));
        System.out.println("Optimized: " + Arrays.toString(findNextIntervalOptimized(intervals)));
        System.out.println("TreeMap: " + Arrays.toString(findNextIntervalTreeMap(intervals)));

        // All should output: [1, 2, -1]
    }
}