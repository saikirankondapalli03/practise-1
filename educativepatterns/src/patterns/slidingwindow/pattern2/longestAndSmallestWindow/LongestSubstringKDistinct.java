package patterns.slidingwindow.pattern2.longestAndSmallestWindow;

import java.util.*;

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/longest-and-smallest-window-problems/longest-substring-with-at-most-k-distinct-characters
class LongestSubstringKDistinct {
	
	public static int findLongestSubstringWithKDistinct(String input, int distinctLimit) {
		// --- Input validation ---
		if (input == null || input.isEmpty() || distinctLimit <= 0)
			throw new IllegalArgumentException("Input string must be non-empty and k > 0");

		int windowStart = 0; // Left boundary of the sliding window
		int maxSubstringLength = 0; // Longest valid substring found so far
		Map<Character, Integer> charCount = new HashMap<>(); // Tracks frequency of chars inside window

		// --- Expand the window one character at a time ---
		for (int windowEnd = 0; windowEnd < input.length(); windowEnd++) {
			char rightChar = input.charAt(windowEnd); // Character entering the window
			charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);

			// --- Shrink window while invalid (too many distinct characters) ---
			while (charCount.size() > distinctLimit) {
				char leftChar = input.charAt(windowStart); // Character leaving the window
				charCount.put(leftChar, charCount.get(leftChar) - 1);
				if (charCount.get(leftChar) == 0)
					charCount.remove(leftChar); // Fully removed from window
				windowStart++; // Move left boundary right
			}

			// --- Update best result when window is valid ---
			int currentWindowLength = windowEnd - windowStart + 1;
			maxSubstringLength = Math.max(maxSubstringLength, currentWindowLength);
		}

		return maxSubstringLength;
	}

	public int kDistinctChar(String str, int k) {
		// your code goes here

		if (str == null || str.length() == 0 || str.length() < k)
			throw new IllegalArgumentException();

		int windowStart = 0, maxLength = 0;
		Map<Character, Integer> charFrequencyMap = new HashMap<>();
		// in the following loop we'll try to extend the range [windowStart, windowEnd]
		for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
			char rightChar = str.charAt(windowEnd);
			charFrequencyMap.put(rightChar, charFrequencyMap.getOrDefault(rightChar, 0) + 1);
			// shrink the sliding window, until we are left with 'k' distinct characters in
			// the frequency map
			while (charFrequencyMap.size() > k) {
				char leftChar = str.charAt(windowStart);
				charFrequencyMap.put(leftChar, charFrequencyMap.get(leftChar) - 1);
				if (charFrequencyMap.get(leftChar) == 0) {
					charFrequencyMap.remove(leftChar);
				}
				windowStart++; // shrink the window
			}
			maxLength = Math.max(maxLength, windowEnd - windowStart + 1); // remember the maximum length so far
		}
		return maxLength;
	}

	public static void main(String[] args) {
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLongestSubstringWithKDistinct("araacighjk", 2));
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLongestSubstringWithKDistinct("araaci", 1));
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLongestSubstringWithKDistinct("cbbebi", 3));
	}
}
