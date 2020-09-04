package com.educative.chapter5.recursion;

public class SubSequencePatternMatching {
	public int findSPMCount(String str, String pat) {
		return findSPMCountRecursive(str, pat, 0, 0);
	}

	private int findSPMCountRecursive(String str, String pat, int strIndex, int patIndex) {

		// if we have reached the end of the pattern
		if (patIndex == pat.length())
			return 1;

		// if we have reached the end of the string but pattern has still some
		// characters left
		if (strIndex == str.length())
			return 0;

		int c1 = 0;
		if (str.charAt(strIndex) == pat.charAt(patIndex))
			c1 = findSPMCountRecursive(str, pat, strIndex + 1, patIndex + 1);

		int c2 = findSPMCountRecursive(str, pat, strIndex + 1, patIndex);

		return c1 + c2;
	}

	public static void main(String[] args) {
		SubSequencePatternMatching spm = new SubSequencePatternMatching();
		//System.out.println(spm.findSPMCount("baxmx", "ax"));
		System.out.println(spm.findSPMCount("tomorrow", "tor"));
	}
}
