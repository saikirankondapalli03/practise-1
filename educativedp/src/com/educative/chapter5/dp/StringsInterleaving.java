package com.educative.chapter5.dp;

public class StringsInterleaving {
	 public boolean isInterleave(String s1, String s2, String s3) {
	        if (s3.length() != s1.length() + s2.length()) {
	            return false;
	        }
	        boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];
	        for (int i = 0; i <= s1.length(); i++) {
	            for (int j = 0; j <= s2.length(); j++) {
	                if (i == 0 && j == 0) {
	                    dp[i][j] = true;
	                } else if (i == 0) {
	                    dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
	                } else if (j == 0) {
	                    dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
	                } else {
	                    dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
	                }
	            }
	        }
	        return dp[s1.length()][s2.length()];
	    }
	 
	 public Boolean findSI(String m, String n, String p) {
			// dp[mIndex][nIndex] will be storing the result of string interleaving
			// up to p[0..mIndex+nIndex-1]
			boolean[][] dp = new boolean[m.length() + 1][n.length() + 1];

			// for the empty pattern, we have one matching
			if (m.length() + n.length() != p.length())
				return false;

			for (int i = 0; i <= m.length(); i++) {
				for (int j = 0; j <= n.length(); j++) {
					// if 'm' and 'n' are empty, then 'p' must have been empty too.
					System.out.println(i+"=="+j);
					if (i == 0 && j == 0)
						dp[i][j] = true;
					// if 'm' is empty, we need to check the interleaving with 'n' only
					else if (i == 0 && n.charAt(j - 1) == p.charAt(i + j - 1))
						dp[i][j] = dp[i][j - 1];
					// if 'n' is empty, we need to check the interleaving with 'm' only
					else if (j == 0 && m.charAt(i - 1) == p.charAt(i + j - 1))
						dp[i][j] = dp[i - 1][j];
					else {
						// if the letter of 'm' and 'p' match, we take whatever is matched till mIndex-1
						if (i > 0 && m.charAt(i - 1) == p.charAt(i + j - 1))
							dp[i][j] = dp[i - 1][j];
						// if the letter of 'n' and 'p' match, we take whatever is matched till nIndex-1
						// too
						// note the '|=', this is required when we have common letters
						if (j > 0 && n.charAt(j - 1) == p.charAt(i + j - 1))
							dp[i][j] |= dp[i][j - 1];
					}
				}
			}
			return dp[m.length()][n.length()];
		}
	public static void main(String[] args) {
		StringsInterleaving si = new StringsInterleaving();
		// System.out.println(si.findSI("aabcc", "dbbca", "aadbbcbcac"));
		 System.out.println(si.findSI("abcdef", "mnop", "mnaobcdepf"));
		// System.out.println(si.findSI("abc", "def", "abdccf"));
		//System.out.println(si.findSI("abc", "mno", "omanbc"));
	}
}
