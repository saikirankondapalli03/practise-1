package com.patterns.slidingwindow.pattern2variablelongest;

import java.util.*;

class LongestSubStringWithOutRepeatingCharacters {
	
	public static int findLongestUniqueSubstring(String input) {
	    // --- Input validation ---
	    if (input == null || input.isEmpty())
	        throw new IllegalArgumentException("Input string must be non-empty");

	    int windowStart = 0;               // Left boundary of current window
	    int longestLength = 0;             // Length of longest unique substring found
	    Map<Character, Integer> lastSeenIndex = new HashMap<>(); // Last seen index of each character

	    // --- Expand the window character by character ---
	    for (int windowEnd = 0; windowEnd < input.length(); windowEnd++) {
	        char rightChar = input.charAt(windowEnd); // Character entering the window

	        // If we've seen this character before, move windowStart past its last index
	        if (lastSeenIndex.containsKey(rightChar)) {
	            windowStart = Math.max(windowStart, lastSeenIndex.get(rightChar) + 1);
	        }

	        // Record/Update the last seen index of this character
	        lastSeenIndex.put(rightChar, windowEnd);

	        // Update the longest valid window seen so far
	        int currentWindowLength = windowEnd - windowStart + 1;
	        longestLength = Math.max(longestLength, currentWindowLength);
	    }

	    return longestLength;
	}
	
	
    // Driver code
    public static void main(String[] arg) {
        String[] inputs = {
            "abcabcbb",
            "pwwkew",
            "bbbbb",
            "ababababa",
            "ABCDEFGHI",
            "ABCDEDCBA",
            "AAAABBBBCCCCDDDD"
        };
        for (int i = 0; i < inputs.length; i++) {
            int str = LongestSubStringWithOutRepeatingCharacters.findLongestUniqueSubstring(inputs[i]);
            System.out.print(i + 1);
            System.out.println("\tInput string: " + inputs[i]);
            System.out.println("\n\tLength of longest substring: " + str);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
