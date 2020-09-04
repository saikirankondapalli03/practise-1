package com.educative.chapter4.recursion;

public class LPSubString {
	public int findLPSLength(String st) {
		return findLPSLengthRecursive(st, 0, st.length() - 1);
	}

	private int findLPSLengthRecursive(String st, int startIndex, int endIndex) {
		if (startIndex > endIndex)
			return 0;

		// every string with one character is a palindrome
		if (startIndex == endIndex)
			return 1;

		// case 1: elements at the beginning and the end are the same
		if (st.charAt(startIndex) == st.charAt(endIndex)) {
			int remainingLength = endIndex - startIndex - 1;
			// check if the remaining string is also a palindrome
			int length=findLPSLengthRecursive(st, startIndex + 1, endIndex - 1);
			if (remainingLength == length )
				return remainingLength + 2;
		}

		// case 2: skip one character either from the beginning or the end
		int c1 = findLPSLengthRecursive(st, startIndex + 1, endIndex);
		int c2 = findLPSLengthRecursive(st, startIndex, endIndex - 1);
		return Math.max(c1, c2);
	}

	public static void main(String[] args) {
		LPSubString lps = new LPSubString();
		System.out.println(lps.findLPSLength("a1bbba"));
		System.out.println(lps.findLPSLength("cddpd"));
		System.out.println(lps.findLPSLength("pqr"));
	}

}
