package com.patterns.slidingwindow.pattern2variablelongest;

import java.util.*;

class LongestSubStringWithOutRepeatingCharacters {

	/*
	 * INTERVIEW DERIVATION (reproduce from first principles)
	 * --------------------------------------------------------
	 * 1. RECOGNIZE: "Longest substring where [constraint]" → variable-length sliding window.
	 *    Constraint here: no repeating characters.
	 *
	 * 2. WINDOW MEANING: [windowStart, windowEnd] = current substring we're considering.
	 *    We expand by moving windowEnd; when window becomes "invalid", we shrink from windowStart.
	 *
	 * 3. WHEN IS WINDOW INVALID? When we ADD the new character at windowEnd, the window gets
	 *    invalid only if that character was ALREADY inside the window (duplicate).
	 *
	 * 4. HOW TO FIX? Shrink from the left until that duplicate is gone. We don't shrink
	 *    one-by-one: we JUMP left to just past the PREVIOUS occurrence of the same character.
	 *    So we need: "Where did I last see this character?" → lastSeenIndex map.
	 *
	 * 5. TEMPLATE ORDER (each step at windowEnd):
	 *    a) New char might make window invalid → if seen before, jump windowStart past last occurrence.
	 *    b) Update lastSeenIndex for current char (so next time we know where to jump).
	 *    c) Now window is valid → update best length.
	 */
	public static int findLongestUniqueSubstring(String input) {
	    // --- Input validation ---
	    if (input == null || input.isEmpty())
	        throw new IllegalArgumentException("Input string must be non-empty");

	    int windowStart = 0;
	    int longestLength = 0;
	    Map<Character, Integer> lastSeenIndex = new HashMap<>();

	    for (int windowEnd = 0; windowEnd < input.length(); windowEnd++) {
	        char rightChar = input.charAt(windowEnd);

	        // (a) Duplicate? Jump left past its last occurrence.
	        if (lastSeenIndex.containsKey(rightChar)) {
	            windowStart = Math.max(windowStart, lastSeenIndex.get(rightChar) + 1);
	        }
	        // (b) Remember where we saw this char (for future jump).
	        lastSeenIndex.put(rightChar, windowEnd);
	        // (c) Window valid → track max length.
	        longestLength = Math.max(longestLength, windowEnd - windowStart + 1);
	    }

	    return longestLength;
	}
	
	public static int findLongestUniqueSubstring1(String input) {
	    // --- Input validation ---
	    if(input == null || input.isEmpty()){
			throw new IllegalArgumentException("Input string must be non-empty");
		}

		int left=0;
		int longestLength=0;
		Map<Character, Integer> lastSeenIndex = new HashMap<>();

		for(int right=0; right<input.length(); right++){
			char rightChar = input.charAt(right);

            lastSeenIndex.put(rightChar,right);
			

		}
		



		return 0; 
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
