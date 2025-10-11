package com.educative.patterns.slidingwindow.pattern1.constantwindow;

import java.util.*;


/*
 * 
 * We�ll iterate through the string to add one letter at a time in the window.
 * We�ll also keep track of the count of the maximum repeating letter in any window
 * (let�s call it maxRepeatLetterCount). 
 * So at any time, we know that we can have a window which has one letter repeating maxRepeatLetterCount times, this means we should try to replace the remaining letters.
 * If we have more than �k� remaining letters, we should shrink the window as we are not allowed 
 * to replace more than �k� letters.
 * 
 */

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/longest-and-smallest-window-problems/longest-repeating-character-replacement

//https://www.educative.io/courses/grokking-coding-interview/solution-longest-repeating-character-replacement
//TODO
class CharacterReplacement {
	public static int findLength(String str, int k) {
		int windowStart = 0, maxLength = 0, mostFrequentCharacter = 0;
		Map<Character, Integer> letterFrequencyMap = new HashMap<>();
		// try to extend the range [windowStart, windowEnd]
		for (int windowEnd = 0; windowEnd < str.length(); windowEnd++) {
			char rightChar = str.charAt(windowEnd);
			letterFrequencyMap.put(rightChar, letterFrequencyMap.getOrDefault(rightChar, 0) + 1);
			mostFrequentCharacter = Math.max(mostFrequentCharacter, letterFrequencyMap.get(rightChar));

			// current window size is from windowStart to windowEnd, overall we have a
			// letter which is
			// repeating 'maxRepeatLetterCount' times, this means we can have a window which
			// has one letter
			// repeating 'maxRepeatLetterCount' times and the remaining letters we should
			// replace.
			// if the remaining letters are more than 'k', it is the time to shrink the
			// window as we
			// are not allowed to replace more than 'k' letters
			// if the length of string > (size of window + total replaceable characters)
			if (windowEnd - windowStart + 1 >  mostFrequentCharacter +k) {
				char leftChar = str.charAt(windowStart);
				letterFrequencyMap.put(leftChar, letterFrequencyMap.get(leftChar) - 1);
				windowStart++;
			}

			maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
		}

		return maxLength;
	}

	
	/*
	 * 
	 * 
	 * Suppose we have the string "AABBBCCC" and k = 2 (we can replace 2 characters).

At some point, let's say:

windowStart = 0

windowEnd = 6

mostFrequentCharacter = 3 (either B or C)

k = 2

The window is "AABBBC" (7 characters long).

Now, let's plug these into our condition:
(windowEnd - windowStart + 1) > (mostFrequentCharacter + k)
(6 - 0 + 1) > (3 + 2)
7 > 5

This condition is true, which means:

We have 7 characters in our window

But we can only "keep" 5 of them (3 of the most frequent character + 2 we're allowed to replace)

So we need to shrink the window

The idea is to keep the window as large as possible while ensuring that:
(window size) <= (count of most frequent char) + (chars we can replace)

This way, we're always maintaining a window that could potentially be turned into a valid substring (all same characters) by replacing at most k characters.
	 */
	public static void main(String[] args) {
		//System.out.println(CharacterReplacement.findLength("abcddf", 2));
		//System.out.println(CharacterReplacement.findLength("abbcb", 2));
		System.out.println(CharacterReplacement.findLength("aaadde", 2));
	}
}
