package com.leetcode.arraysandstrings.chapter1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiplyStrings {
	public String multiply(String num1, String num2) {
		int m = num1.length(), n = num2.length();
		int[] pos = new int[m + n];

		for (int i = m - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
				int p1 = i + j, p2 = i + j + 1;
				int sum = mul + pos[p2];

				pos[p1] += sum / 10;
				pos[p2] = (sum) % 10;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int p : pos)
			if (!(sb.length() == 0 && p == 0))
				sb.append(p);
		return sb.length() == 0 ? "0" : sb.toString();
	}

	class Solution {

		private Set<String> validExpressions = new HashSet<String>();
		private int minimumRemoved;

		private void reset() {
			this.validExpressions.clear();
			this.minimumRemoved = Integer.MAX_VALUE;
		}

		private void recurse(String s, int index, int leftCount, int rightCount, StringBuilder expression,
				int removedCount) {

			// If we have reached the end of string.
			if (index == s.length()) {

				// If the current expression is valid.
				if (leftCount == rightCount) {

					// If the current count of removed parentheses is <= the current minimum count
					if (removedCount <= this.minimumRemoved) {

						// Convert StringBuilder to a String. This is an expensive operation.
						// So we only perform this when needed.
						String possibleAnswer = expression.toString();

						// If the current count beats the overall minimum we have till now
						if (removedCount < this.minimumRemoved) {
							this.validExpressions.clear();
							this.minimumRemoved = removedCount;
						}
						this.validExpressions.add(possibleAnswer);
					}
				}
			} else {

				char currentCharacter = s.charAt(index);
				int length = expression.length();

				// If the current character is neither an opening bracket nor a closing one,
				// simply recurse further by adding it to the expression StringBuilder
				if (currentCharacter != '(' && currentCharacter != ')') {
					expression.append(currentCharacter);
					this.recurse(s, index + 1, leftCount, rightCount, expression, removedCount);
					expression.deleteCharAt(length);
				} else {

					// Recursion where we delete the current character and move forward
					this.recurse(s, index + 1, leftCount, rightCount, expression, removedCount + 1);
					expression.append(currentCharacter);

					// If it's an opening parenthesis, consider it and recurse
					if (currentCharacter == '(') {
						this.recurse(s, index + 1, leftCount + 1, rightCount, expression, removedCount);
					} else if (rightCount < leftCount) {
						// For a closing parenthesis, only recurse if right < left
						this.recurse(s, index + 1, leftCount, rightCount + 1, expression, removedCount);
					}

					// Undoing the append operation for other recursions.
					expression.deleteCharAt(length);
				}
			}
		}

		public List<String> removeInvalidParentheses(String s) {

			this.reset();
			this.recurse(s, 0, 0, 0, new StringBuilder(), 0);
			return new ArrayList(this.validExpressions);
		}
	}
}
