package com.educative.chapter4.dp;

public class LPSubString {

	public int findLPSLength(String st) {
		// dp[i][j] will be 'true' if the string from index 'i' to index 'j' is a
		// palindrome
		boolean[][] dp = new boolean[st.length()][st.length()];

		// every string with one character is a palindrome
		for (int i = 0; i < st.length(); i++)
			dp[i][i] = true;

		int maxLength = 1;
		for (int i = st.length() - 1; i >= 0; i--) {
			for (int j = i + 1; j < st.length(); j++) {
				System.out.println(i+"--"+j);
				
				if (st.charAt(i) == st.charAt(j)) {
					// if it's a two character string or if the remaining string is a palindrome too
					if (j - i == 1 || dp[i + 1][j - 1]) {
						dp[i][j] = true;
  						maxLength = Math.max(maxLength, j - i + 1);
					}
				}
			}
		}

		return maxLength;
	}

	public static void main(String[] args) {
		LPSubString lps = new LPSubString();
		//System.out.println(lps.findLPSLength("abdbca"));
		System.out.println(lps.findLPSLength("cddpd"));
		//System.out.println(lps.findLPSLength("pqr"));
	}
}
