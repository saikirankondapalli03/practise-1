package com.patterns.slidingwindow.pattern3variableshortest;

import java.util.*;

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/longest-and-smallest-window-problems/minimum-window-substring-?tab=submissions

class MinimumWindowSubstring {
	
/*
 * 
 * 
 * “I’ll expand my window until all pattern chars are covered.
Then I’ll shrink from the left until removing a char would make it invalid.
Each time the window is valid, I record its length and try to minimize it.”
Step	Description	In this problem
1️⃣ Invariant	What’s a valid window?	Contains all chars of pattern
2️⃣ State	What do I track?	need map + matched counter
3️⃣ Apply	When expanding right	Decrease need, maybe matched++
4️⃣ Undo	When shrinking left	If needed char lost, matched--, increase need
5️⃣ Record	When valid	Minimize window length


Phase	What happens	Why
Expand	Add chars until window valid	To get all pattern chars
Shrink	Remove extras until invalid	To minimize
Record	Before losing validity	Because that’s the smallest valid window


 */
	public static String smallestWindow(String text, String pattern) {
		if (text == null || pattern == null || text.length() < pattern.length())
			return "";

		// 1️⃣ Build a frequency map of all characters in the pattern
		Map<Character, Integer> needed = new HashMap<>();
		for (char ch : pattern.toCharArray()) {
			needed.put(ch, needed.getOrDefault(ch, 0) + 1);
		}

		int windowStart = 0;
		int matched = 0; // how many pattern chars matched so far
		int minWindowLen = Integer.MAX_VALUE;
		int minWindowStart = 0;
		//ADOBECODEBANC
		// 2️⃣ Expand the window
		for (int windowEnd = 0; windowEnd < text.length(); windowEnd++) {
			char rightChar = text.charAt(windowEnd);

			// If this char is part of the pattern, reduce its needed count
			if (needed.containsKey(rightChar)) {
				needed.put(rightChar, needed.get(rightChar) - 1);
				if (needed.get(rightChar) >= 0) // means it was actually needed
					matched++;
			}

			// 3️⃣ When we have matched all pattern characters, try shrinking
			while (matched == pattern.length()) {
				int currentWindowLen = windowEnd - windowStart + 1;

				// Update smallest window seen so far
				if (currentWindowLen < minWindowLen) {
					minWindowLen = currentWindowLen;
					minWindowStart = windowStart;
				}

				// Try to remove leftmost character
				char leftChar = text.charAt(windowStart);
				windowStart++;

				if (needed.containsKey(leftChar)) {
					// If removing this makes us lose a needed char, window invalid now
					if (needed.get(leftChar) == 0)
						matched--;
					needed.put(leftChar, needed.get(leftChar) + 1);
				}
			}
		}

		// 4️⃣ Return smallest valid substring or empty string if none
		if (minWindowLen == Integer.MAX_VALUE)
			return "";
		return text.substring(minWindowStart, minWindowStart + minWindowLen);
	}

	public static void main(String[] args) {
		System.out.println(MinimumWindowSubstring.smallestWindow("ADOBECODEBANC", "ABC"));
		System.out.println(MinimumWindowSubstring.smallestWindow("abcddec", "abc"));
		System.out.println(MinimumWindowSubstring.smallestWindow("zwewabdabca", "zabc"));

	}
}
