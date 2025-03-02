package com.educative.patterns.chapter1.window.pattern2.longestAndSmallestWindow;

import java.util.*;

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/longest-and-smallest-window-problems/longest-substring-with-at-most-k-distinct-characters
class LongestSubstringKDistinct {
	
	public static int findLength(String str, int k) {
	    // Input validation: Check if the string is null, empty, or shorter than k
	    if (str == null || str.length() == 0 || str.length() < k)
	        throw new IllegalArgumentException();

	    int windowStart = 0;  // Start index of the sliding window
	    int maxLength = 0;    // Maximum length of substring with k distinct characters
	    Map<Character, Integer> charFrequencyMap = new HashMap<>();  // Map to store character frequencies

	    // Iterate through the string using the sliding window technique
	    for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
	        // Get the character at the end of the current window
	        char rightChar = str.charAt(windowEnd);
	        
	        // Add the character to the frequency map or increment its count
	        charFrequencyMap.put(rightChar, charFrequencyMap.getOrDefault(rightChar, 0) + 1);

	        // Shrink the window if we have more than k distinct characters
	        while (charFrequencyMap.size() > k) {
	            // Get the character at the start of the window
	            char leftChar = str.charAt(windowStart);
	            
	            // Decrease the frequency of the left character
	            charFrequencyMap.put(leftChar, charFrequencyMap.get(leftChar) - 1);
	            
	            // If the frequency becomes zero, remove the character from the map
	            if (charFrequencyMap.get(leftChar) == 0) {
	                charFrequencyMap.remove(leftChar);
	            }
	            
	            // Move the start of the window to the right
	            windowStart++;
	        }

	        // Update the maximum length if the current window is longer
	        maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
	    }

	    // Return the length of the longest substring with k distinct characters
	    return maxLength;
	}


	public static void main(String[] args) {
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLength("araacighjk", 2));
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLength("araaci", 1));
		System.out.println("Length of the longest substring: " + LongestSubstringKDistinct.findLength("cbbebi", 3));
	}
}
