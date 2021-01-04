package com.educative.coderust.chapter4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegularExpressionMatch {

	public boolean isMatch(String text, String pattern) {
		if (pattern.isEmpty())
			return text.isEmpty();
		boolean first_match = (!text.isEmpty()
				&& 
				(pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

		if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
			return (isMatch(text, pattern.substring(2)) || 
					(first_match && isMatch(text.substring(1), pattern)));
		} else {
			return first_match && isMatch(text.substring(1), pattern.substring(1));
		}
	}

	static boolean regxMatchRec(String text, String pattern) {
		System.out.println("text => " + text + " pattern => " + pattern);
		// both text and pattern are empty => fabbbc , .ab*c

		if (text.isEmpty() && pattern.isEmpty()) {
			// if text and pattern is empty
			return true;
		}

		if (!text.isEmpty() && pattern.isEmpty()) {
			// if text is not empty and pattern are empty
			return false;
		}

		if (pattern.length() > 1 && pattern.charAt(1) == '*') {
			// if pattern length is greater than 1 and

			String remainingPattern = pattern.substring(2);
			String remainingText = text;

			for (int i = 0; i < text.length() + 1; ++i) {
				if (regxMatchRec(remainingText, remainingPattern)) {
					return true;
				}

				if (remainingText.isEmpty()) {
					return false;
				}

				if (pattern.charAt(0) != '.' && remainingText.charAt(0) != pattern.charAt(0)) {
					return false;
				}

				remainingText = remainingText.substring(1);
			}
		}

		if (text.isEmpty() || pattern.isEmpty()) {
			return false;
		}

		if (pattern.charAt(0) == '.') {
			String remainingText = "";
			if (text.length() >= 2) {
				remainingText = text.substring(1);
			}

			String remainingPattern = "";
			if (pattern.length() >= 2) {
				remainingPattern = pattern.substring(1);
			}

			return regxMatchRec(remainingText, remainingPattern);
		}

		if (pattern.charAt(0) == text.charAt(0)) {
			String remainingText = "";
			if (text.length() >= 2) {
				remainingText = text.substring(1);
			}

			String remainingPattern = "";
			if (pattern.length() >= 2) {
				remainingPattern = pattern.substring(1);
			}

			return regxMatchRec(remainingText, remainingPattern);
		}
		return false;
	}

	public static boolean regxMatch(String s, String p) {
		return regxMatchRec(s, p);
	}

	public static void main(String[] args) {
		String s = "abbbc";// "ab"; //"fabbbc";
		String p = ".b*c";// ".ab*c";
		// boolean output2 = regxMatchRec(s, p);

		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		boolean output3 = regxMatchRec(s, p);
		if (output3) {
			System.out.print(s + " " + p + " match");
		} else {
			System.out.print(s + " " + p + " did not match.");
		}
	}

	public static boolean regxMatchRec(String text, String pattern, int textLengthPointer, int patternLengthPointer) {
		System.out.println("text => " + text + " pattern => " + pattern);
		System.out.println("textLengthPointer => " + textLengthPointer + " pattern => " + patternLengthPointer);
		if (text.length() == textLengthPointer && pattern.length() == patternLengthPointer) {
			return true;
		}

		if (patternLengthPointer < pattern.length() - 1 && pattern.charAt(patternLengthPointer + 1) == '*') {
			for (int k = textLengthPointer; k <= text.length(); ++k) {
				if (regxMatchRec(text, pattern, k, patternLengthPointer + 2)) {
					return true;
				}

				if (k >= text.length()) {
					return false;
				}

				if (pattern.charAt(patternLengthPointer) != '.'
						&& text.charAt(k) != pattern.charAt(patternLengthPointer)) {
					return false;
				}
			}
		} else if (textLengthPointer < text.length() && patternLengthPointer < pattern.length()
				&& (pattern.charAt(patternLengthPointer) == '.'
						|| pattern.charAt(patternLengthPointer) == text.charAt(textLengthPointer))) {
			return regxMatchRec(text, pattern, textLengthPointer + 1, patternLengthPointer + 1);
		}

		return false;
	}

	public static boolean regxMatchRecursion(String text, String pattern) {
		return regxMatchRec(text, pattern, 0, 0);
	}

}