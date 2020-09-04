package com.educative.chapter5.recursion;

public class LongestCommonSubString {
	public int findLCSLength(String s1, String s2) {
		return findLCSLengthRecursive(s1, s2, 0, 0, 0);
	}

	private int findLCSLengthRecursive(String s1, String s2, int i1, int i2, int matchedSoFar) {
	    if(i1 == s1.length() || i2 == s2.length())
	      return matchedSoFar;

	    if(s1.charAt(i1) == s2.charAt(i2))
	      matchedSoFar = findLCSLengthRecursive(s1, s2, i1+1, i2+1, matchedSoFar+1);

	    int c1 = findLCSLengthRecursive(s1, s2, i1, i2+1, 0);
	    int c2 = findLCSLengthRecursive(s1, s2, i1+1, i2, 0);
	    
	    int result= Math.max(matchedSoFar, Math.max(c1, c2));
	    return result;
	  }

	public static void main(String[] args) {
		LongestCommonSubString lcs = new LongestCommonSubString();
		System.out.println(lcs.findLCSLength("aabcd", "aac"));
		System.out.println(lcs.findLCSLength("passport", "ppsspt"));
	}
}
