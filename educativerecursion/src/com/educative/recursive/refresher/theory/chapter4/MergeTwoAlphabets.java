package com.educative.recursive.refresher.theory.chapter4;

public class MergeTwoAlphabets {

	private static String alphabeticMerge(String one, String two) {
		if (one == null || one.equals("")) {
			return two == null ? one : two;
		} else if (two == null || two.equals("")) {
			return one;
		} else if (two.charAt(0) > one.charAt(0)) {
			return one.charAt(0) + alphabeticMerge(one.substring(1, one.length()), two);
		} else {
			return two.charAt(0) + alphabeticMerge(one, two.substring(1, two.length()));
		}
	}

	public static void main(String args[]) {
		String one = "cmp";
		String two = "anz";
		String answer = alphabeticMerge(one, two);
		System.out.println(answer);

	}

}
