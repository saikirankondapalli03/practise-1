package com.educative.chapter4.dp;

class MPP {

	public int findMPPCuts(String s) {
		// isPalindrome[i][j] will be 'true' if the string from index 'i' to index 'j'
		// is a palindrome
		boolean[][] isPalindrome = new boolean[s.length()][s.length()];

		// every string with one character is a palindrome
		for (int i = 0; i < s.length(); i++)
			isPalindrome[i][i] = true;

		// populate isPalindrome table
		for (int startIndex = s.length() - 1; startIndex >= 0; startIndex--) {
			for (int endIndex = startIndex + 1; endIndex < s.length(); endIndex++) {
				if (s.charAt(startIndex) == s.charAt(endIndex)) {
					// if it's a two character string or if the remaining string is a palindrome too
					if (endIndex - startIndex == 1 || isPalindrome[startIndex + 1][endIndex - 1]) {
						isPalindrome[startIndex][endIndex] = true;
					}
				}
			}
		}

		// now lets populate the second table, every index in 'cuts' stores the minimum
		// cuts needed
		// for the substring from that index till the end
		int[] cuts = new int[s.length()];
		for (int startIndex = s.length() - 1; startIndex >= 0; startIndex--) {
			int minCuts = s.length() - 1; // maximum cuts
			for (int endIndex = s.length() - 1; endIndex >= startIndex; endIndex--) {

				System.out.println(startIndex + "--" + endIndex);
				if (isPalindrome[startIndex][endIndex]) {
					// we can cut here as we got a palindrome
					// also we dont need any cut if the whole substring is a palindrome
					if (endIndex == s.length() - 1) {
						minCuts = 0;
					} else {
						minCuts = Math.min(minCuts, 1 + cuts[endIndex + 1]);
					}

				}
			}
			System.out.println("cuts is==> " + minCuts);
			cuts[startIndex] = minCuts;
		}

		return cuts[0];
	}

	public static void main(String[] args) {
		MPP mpp = new MPP();
		// System.out.println(mpp.findMPPCuts("abdbca"));
		System.out.println(mpp.findMPPCuts("cddpd"));
		// System.out.println(mpp.findMPPCuts("pqr"));
		// System.out.println(mpp.findMPPCuts("pp"));
		// System.out.println(mpp.findMPPCuts("madam"));
	}
}
