package com.patterns.backtracking.pattern6wordproblems;

import java.util.ArrayList;
import java.util.List;

/*
 * PROBLEM: Letter Combinations of a Phone Number
 * 
 * Given a string containing digits from 2-9, return all possible letter combinations
 * that the number could represent (like on old phone keypads).
 * 
 * Mapping:
 * 2: abc, 3: def, 4: ghi, 5: jkl, 6: mno, 7: pqrs, 8: tuv, 9: wxyz
 * 
 * Example:
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 */

class PhoneNumber {
	
	// STANDARD BACKTRACKING TEMPLATE
	// Time Complexity: O(4^n) where n is length of digits
	// Space Complexity: O(4^n) for storing all combinations
	public static List<String> letterCombinations(String digits) {
		List<String> result = new ArrayList<>();
		if (digits.isEmpty()) return result;
		
		// Digit to letters mapping
		String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		
		backtrack(digits, 0, new StringBuilder(), result, mapping);
		return result;
	}
	
	/*
	 * BACKTRACKING TEMPLATE:
	 * 1. Base case: when to stop and save result
	 * 2. Get choices: what options do I have at current state
	 * 3. Try each choice: choose → explore → unchoose
	 */
	private static void backtrack(String digits, int index, StringBuilder current, 
			                     List<String> result, String[] mapping) {
		
		// BASE CASE: processed all digits
		if (index == digits.length()) {
			result.add(current.toString());
			return;
		}
		
		// GET CHOICES: letters for current digit
		String letters = mapping[digits.charAt(index) - '0'];
		
		// TRY EACH CHOICE
		for (char letter : letters.toCharArray()) {
			// CHOOSE: add letter to current path
			current.append(letter);
			
			// EXPLORE: move to next digit
			backtrack(digits, index + 1, current, result, mapping);
			
			// UNCHOOSE: remove letter (backtrack)
			current.deleteCharAt(current.length() - 1);
		}
	}

	public static void main(String[] args) {
		String input = "23";
		System.out.println("Input: " + input);
		
		List<String> result = letterCombinations(input);
		System.out.println("Output: " + result);
		// Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]
	}
}