package com.educative.chapter5.memoization;

import java.util.HashMap;
import java.util.Map;

public class StringsInterleaving {
	public Boolean findSI(String m, String n, String p) {
		Map<String, Boolean> dp = new HashMap<>();
		return findSIRecursive(dp, m, n, p, 0, 0, 0);
	}

	private boolean findSIRecursive(Map<String, Boolean> dp, String m, String n, String p, int mIndex, int nIndex,
			int pIndex) {

		// if we have reached the end of the all the strings
		if (mIndex == m.length() && nIndex == n.length() && pIndex == p.length())
			return true;

		// if we have reached the end of 'p' but 'm' or 'n' still has some characters
		// left
		if (pIndex == p.length())
			return false;

		String subProblemKey = mIndex + "-" + nIndex + "-" + pIndex;
		if (!dp.containsKey(subProblemKey)) {
			boolean b1 = false, b2 = false;
			if (mIndex < m.length() && m.charAt(mIndex) == p.charAt(pIndex))
				b1 = findSIRecursive(dp, m, n, p, mIndex + 1, nIndex, pIndex + 1);

			if (nIndex < n.length() && n.charAt(nIndex) == p.charAt(pIndex))
				b2 = findSIRecursive(dp, m, n, p, mIndex, nIndex + 1, pIndex + 1);

			dp.put(subProblemKey, b1 || b2);
		}

		return dp.get(subProblemKey);
	}

	public static void main(String[] args) {
		StringsInterleaving si = new StringsInterleaving();
		// System.out.println(si.findSI("abd", "cef", "abcdef"));
		// System.out.println(si.findSI("abd", "cef", "adcbef"));
		// System.out.println(si.findSI("abc", "def", "abdccf"));
		System.out.println(si.findSI("abc", "mno", "omanbc"));
	}
}
