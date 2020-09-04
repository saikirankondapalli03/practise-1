package com.educative.chapter5.dp;

class EditDistance {

	public int findMinOperations(String s1, String s2) {
		int[][] dp = new int[s1.length() + 1][s2.length() + 1];

		// if s2 is empty, we can remove all the characters of s1 to make it empty too
		for (int i1 = 0; i1 <= s1.length(); i1++)
			dp[i1][0] = i1;

		// if s1 is empty, we have to insert all the characters of s2
		for (int i2 = 0; i2 <= s2.length(); i2++)
			dp[0][i2] = i2;

		for (int i1 = 1; i1 <= s1.length(); i1++) {
			for (int i2 = 1; i2 <= s2.length(); i2++) {
				// If the strings have a matching character, we can recursively match for the
				// remaining lengths
				if (s1.charAt(i1 - 1) == s2.charAt(i2 - 1))
					dp[i1][i2] = dp[i1 - 1][i2 - 1];
				else
					dp[i1][i2] = 1 + Math.min(dp[i1 - 1][i2], // delete
							Math.min(dp[i1][i2 - 1], // insert
									dp[i1 - 1][i2 - 1])); // replace
			}
		}

		return dp[s1.length()][s2.length()];
	}

	public static void main(String[] args) {
		EditDistance editDisatnce = new EditDistance();
		System.out.println(editDisatnce.findMinOperations("bat", "but"));
		System.out.println(editDisatnce.findMinOperations("abdca", "cbda"));
		System.out.println(editDisatnce.findMinOperations("passpot", "ppsspqrt"));
	}
}
