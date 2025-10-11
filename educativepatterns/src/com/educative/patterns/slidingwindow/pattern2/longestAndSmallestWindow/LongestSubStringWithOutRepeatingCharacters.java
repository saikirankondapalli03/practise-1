package com.educative.patterns.slidingwindow.pattern2.longestAndSmallestWindow;

import java.util.*;

class LongestSubStringWithOutRepeatingCharacters {
	public static int findLongestSubstring(String str) {
	    // If the string is empty, return 0
	    if (str.length() == 0) {
	        return 0;
	    }

	    int n = str.length();
	    int windowStart = 0, longest = 0, windowLength = 0, i = 0;

	    // Hashtable to store the last seen position of each character
	    Hashtable<Character, Integer> lastSeenAt = new Hashtable<Character, Integer>();

	    // Iterate through each character in the string
	    for (i = 0; i < n; i++) {
	        // If the character hasn't been seen before
	        if (!lastSeenAt.containsKey(str.charAt(i))) {
	            // Add it to the hashtable with its current position
	            lastSeenAt.put(str.charAt(i), i);
	        } else {
	            // If the character has been seen before
	            // Check if it's within the current window
	            if (lastSeenAt.get(str.charAt(i)) >= windowStart) {
	                // Calculate the length of the current window
	                windowLength = i - windowStart;
	                // Update the longest substring if necessary
	                if (longest < windowLength) {
	                    longest = windowLength;
	                }
	                // Move the window start to just after the last occurrence of this character
	                windowStart = lastSeenAt.get(str.charAt(i)) + 1;
	            }
	            // Update the last seen position of the character
	            lastSeenAt.replace(str.charAt(i), i);
	        }
	    }

	    // Check if the last window is the longest
	    if (longest < i - windowStart) {
	        longest = i - windowStart;
	    }

	    // Return the length of the longest substring without repeating characters
	    return longest;
	}

    // Driver code
    public static void main(String[] arg) {
        String[] inputs = {
            "abcabcbb",
            "pwwkew",
            "bbbbb",
            "ababababa",
            "",
            "ABCDEFGHI",
            "ABCDEDCBA",
            "AAAABBBBCCCCDDDD"
        };
        for (int i = 0; i < inputs.length; i++) {
            int str = LongestSubStringWithOutRepeatingCharacters.findLongestSubstring(inputs[i]);
            System.out.print(i + 1);
            System.out.println("\tInput string: " + inputs[i]);
            System.out.println("\n\tLength of longest substring: " + str);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}