package com.educative.patterns.chapter10.subsets;

import java.util.*;

class ParenthesesString {
	String str;
	int openCount; // open parentheses count
	int closeCount; // close parentheses count

	public ParenthesesString(String s, int openCount, int closeCount) {
		str = s;
		this.openCount = openCount;
		this.closeCount = closeCount;
	}
}

class AllBrackets {

	public static List<String> generateValidParentheses(int num) {
		List<String> result = new ArrayList<String>();
		Queue<ParenthesesString> queue = new LinkedList<>();
		queue.add(new ParenthesesString("", 0, 0));
		while (!queue.isEmpty()) {
			ParenthesesString ps = queue.poll();
			// if we've reached the maximum number of open and close parentheses, add to the
			// result
			if (ps.openCount == num && ps.closeCount == num) {
				result.add(ps.str);
			} else {
				if (ps.openCount < num) // if we can add an open parentheses, add it
					queue.add(new ParenthesesString(ps.str + "(", ps.openCount + 1, ps.closeCount));

				if (ps.openCount > ps.closeCount) // if we can add a close parentheses, add it
					queue.add(new ParenthesesString(ps.str + ")", ps.openCount, ps.closeCount + 1));
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<String> result = AllBrackets.generateValidParentheses(3);
		System.out.println("All combinations of balanced parentheses are: " + result);

		result = AllBrackets.generateValidParentheses(3);
		System.out.println("All combinations of balanced parentheses are: " + result);
	}

	public static List<String> generateValidParenthesesRec(int num) {
		List<String> result = new ArrayList<String>();
		char[] parenthesesString = new char[2 * num];
		generateValidParenthesesRecursive(num, 0, 0, parenthesesString, 0, result);
		return result;
	}

	private static void generateValidParenthesesRecursive(int num, int openCount, int closeCount,char[] parenthesesString, int index, List<String> result) {
 		// if we've reached the maximum number of open and close parentheses, add to the result
		if (openCount == num && closeCount == num) {
			result.add(new String(parenthesesString));
		} else {
			if (openCount < num) { 
				// if we can add an open parentheses, add it
				parenthesesString[index] = '(';
				generateValidParenthesesRecursive(num, openCount + 1, closeCount, parenthesesString, index + 1, result);
			}

			if (openCount > closeCount) {
				// if we can add a close parentheses, add it
				parenthesesString[index] = ')';
				generateValidParenthesesRecursive(num, openCount, closeCount + 1, parenthesesString, index + 1, result);
			}
		}
	}
}
