package com.educative.coderust.chapter4;

class PalindromeSubStrings {
	public static boolean isPalindrome(String input, int i, int j) {
		while (j > i) {
			if (input.charAt(i) != input.charAt(j))
				return false;
			i++;
			j--;
		}
		return true;
	}

	public static int findAllPalindromeSubstrings(String input) {
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			for (int j = i + 1; j < input.length(); j++) {
				if (isPalindrome(input, i, j)) {
					System.out.println(input.substring(i, j + 1));
					count++;
				}
			}
		}

		return count;
	}

	public static void main(String[] args) {
		String str = "aabbbaa";
		int count = findAllPalindromeSubstringsBackTracking(str);
		System.out.println("Total palindrome substrings: " + count);
	}

	public static int findPalindromesInSubString(String input, int j, int k) {
		System.out.println("j=>"+j+"k=>"+k);
		int count = 0;
		for (; j >= 0 && k < input.length(); --j, ++k) {
			if (input.charAt(j) != input.charAt(k)) {
				break;
			}
			System.out.println(input.substring(j, k + 1));
			count++;
		}
		return count;
	}

	public static int findAllPalindromeSubstringsBackTracking(String input) {
		int count = 0;
		for (int i = 0; i < input.length(); ++i) {
			count += findPalindromesInSubString(input, i - 1, i + 1);//odd length
			count += findPalindromesInSubString(input, i, i + 1);//even length
		}

		return count;
	}
}