package com.educative.recursive.refresher.theory.chapter4;

public class PalindromeRecursion {
	public static boolean isPalindrome(String text) {
		if (text.length() == 0) {
			return false;
		} else if (text.length() == 1) {
			return true;
		} else {
			if (text.charAt(0) == text.charAt(text.length() - 1)) {
				return isPalindrome(text.substring(1, text.length() - 1));
			}
		}
		return false;
	}

	public static void main(String args[]) {
		String input1 = "hello";
		String input2 = "dadad";
		boolean answer1 = isPalindrome(input1);
		boolean answer2 = isPalindrome(input2);
		System.out.println("Is " + input1 + " a Palindrome? = " + answer1);
		System.out.println("Is " + input2 + " a Palindrome? = " + answer2);
	}
}
