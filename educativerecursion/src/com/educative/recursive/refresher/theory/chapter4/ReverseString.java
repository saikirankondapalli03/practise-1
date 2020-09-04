package com.educative.recursive.refresher.theory.chapter4;

public class ReverseString {
	public static String reverseString(String myStr) {
		// Base case
		if (myStr.isEmpty()) {
			return myStr;
		}

		// Recursive case
		else {
			String result=  reverseString(myStr.substring(1)) ; 
			char c = myStr.charAt(0);
			
			String s = result+ c;
			return s;
		}
	}

	public static void main(String args[]) {
		String string1 = "Hello World";
		String string2 = "Reverse";

		System.out.println("The Original String is: ");
		System.out.println(string1);

		String resultStr = reverseString(string1);

		System.out.println("String after reversal: ");
		System.out.println(resultStr);
	}
}
