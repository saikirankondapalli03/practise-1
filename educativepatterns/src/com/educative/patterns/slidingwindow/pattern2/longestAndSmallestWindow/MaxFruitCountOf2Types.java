package com.educative.patterns.slidingwindow.pattern2.longestAndSmallestWindow;

import java.util.*;

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/longest-and-smallest-window-problems/fruit-into-baskets?tab=submissions

class MaxFruitCountOf2Types {
	public static int findLength(char[] arr) {
		int windowStart = 0, maxLength = 0;
		Map<Character, Integer> fruitFrequencyMap = new HashMap<>();
		// try to extend the range [windowStart, windowEnd]
		for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
			fruitFrequencyMap.put(arr[windowEnd], fruitFrequencyMap.getOrDefault(arr[windowEnd], 0) + 1);
			// shrink the sliding window, until we are left with '2' fruits in the frequency
			// map
			while (fruitFrequencyMap.size() > 2) {
				fruitFrequencyMap.put(arr[windowStart], fruitFrequencyMap.get(arr[windowStart]) - 1);
				if (fruitFrequencyMap.get(arr[windowStart]) == 0) {
					fruitFrequencyMap.remove(arr[windowStart]);
				}
				windowStart++; // shrink the window
			}
			maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
		}

		return maxLength;
	}
	
	   public int totalFruits(int[] arr) {
	        //your code goes here
		   int windowStart = 0, maxLength = 0;
			Map<Integer, Integer> fruitFrequencyMap = new HashMap<>();
			// try to extend the range [windowStart, windowEnd]
			for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
				fruitFrequencyMap.put(arr[windowEnd], fruitFrequencyMap.getOrDefault(arr[windowEnd], 0) + 1);
				// shrink the sliding window, until we are left with '2' fruits in the frequency
				// map
				while (fruitFrequencyMap.size() > 2) {
					fruitFrequencyMap.put(arr[windowStart], fruitFrequencyMap.get(arr[windowStart]) - 1);
					if (fruitFrequencyMap.get(arr[windowStart]) == 0) {
						fruitFrequencyMap.remove(arr[windowStart]);
					}
					windowStart++; // shrink the window
				}
				maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
			}

			return maxLength;
	    }
	   

	public static void main(String[] args) {
		System.out.println("Maximum number of fruits: "
				+ MaxFruitCountOf2Types.findLength(new char[] {'2','2', '2', '3', '1', '2', '4', '4', '4', '4'}));
		System.out.println("Maximum number of fruits: "
				+ MaxFruitCountOf2Types.findLength(new char[] { 'A', 'B', 'C', 'A', 'C' }));
	
	}
}
