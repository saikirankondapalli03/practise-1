package com.educative.recursive.refresher.theory.chapter4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 
 * 
The Choice: Defining Our Decision Space                             2:40 - 5:50
The Constraints: Directing Our Recursion                             5:50 - 7:42
Our Goal: When Recursion Bottoms Out
 */
public class Permutation {

	public static void permutations(char[] array, int length) {
		if (length == 1) {
			System.out.println(array);
			return;
		} else {
			for (int i = 0; i < length; i++) {
				// System.out.println("how many times ??");
				swap(array, i, length - 1);
				permutations(array, length - 1);
				// System.out.println(i);
				swap(array, i, length - 1);
			}
		}
	}

	public static List<String> result = new ArrayList<String>();

	static void permuteString(char[] input, int currentIndex) {

		if (currentIndex == input.length - 1) {
			String addInput = new String(input);
			result.add(addInput);
			return;
		}

		for (int i = currentIndex; i <= input.length - 1; i++) {
			swap(input, currentIndex, i);
			permuteString(input, currentIndex + 1);
			swap(input, currentIndex, i);
		}
	}

	private static void permutation(String ouput, String input) {
		if (input.isEmpty()) {
			System.err.println(ouput + input);
		} else {
			for (int i = 0; i < input.length(); i++) {
				String temp = ouput + input.charAt(i);
				String remainingInput = input.substring(0, i) + input.substring(i + 1, input.length());
				permutation(temp, remainingInput);
			}
		}
	}

	public static Set<String> generatePerm(String input) {
		Set<String> set = new HashSet<String>();
		if (input == "")
			return set;

		Character a = input.charAt(0);

		if (input.length() > 1) {
			input = input.substring(1);

			Set<String> permSet = generatePerm(input);

			for (String x : permSet) {
				for (int i = 0; i <= x.length(); i++) {
					set.add(x.substring(0, i) + a + x.substring(i));
				}
			}
		} else {
			set.add(a + "");
		}
		return set;
	}

	public static void permutations(String s) {
		// create an empty ArrayList to store (partial) permutations
		List<String> partial = new ArrayList<>();

		// initialize the list with the first character of the string
		partial.add(String.valueOf(s.charAt(0)));

		// do for every character of the specified string
		for (int i = 1; i < s.length(); i++) {
			// consider previously constructed partial permutation one by one

			// (iterate backwards to avoid ConcurrentModificationException)
			for (int j = partial.size() - 1; j >= 0; j--) {
				// remove current partial permutation from the ArrayList
				String str = partial.remove(j);

				// Insert next character of the specified string in all
				// possible positions of current partial permutation. Then
				// insert each of these newly constructed string in the list

				for (int k = 0; k <= str.length(); k++) {
					// Advice: use StringBuilder for concatenation
					partial.add(str.substring(0, k) + s.charAt(i) + str.substring(k));
				}
			}
		}

		System.out.println(partial);
	}

	public static void swap(char[] array, int i, int j) {
		// System.out.println("Swapppdsd");
		char c;
		c = array[i];
		array[i] = array[j];
		array[j] = c;
	}

	private void permute(String str, int l, int r) {
		if (l == r)
			System.out.println(str);
		else {
			for (int i = l; i <= r; i++) {
				str = swap(str, l, i);
				permute(str, l + 1, r);
				str = swap(str, l, i);
			}
		}
	}

	public String swap(String a, int i, int j) {
		char temp;
		char[] charArray = a.toCharArray();
		temp = charArray[i];
		charArray[i] = charArray[j];
		charArray[j] = temp;
		return String.valueOf(charArray);
	}

	public static void main(String args[]) {
		char[] input = { 'd', '1', '2' };
		permutations(input, input.length);
		permuteString(input,0);
		System.out.println(result);
		permutation("", String.valueOf(input));
		// permutation("",String.valueOf(input));
		// generatePerm(String.valueOf(input));
	}
}
