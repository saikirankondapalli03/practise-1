package com.educative.coderust.chapter6;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class ExpressionEvaluation {

	static boolean isOperator(char temp) {
		return temp == '+' || temp == '-' || temp == '*' || temp == '/';
	}

	// returns true if op1 has higher/equal precedence
	// compared to op2
	static boolean preced(char op1, char op2) {
		// bodmas
		if (op1 == '*' || op1 == '/') {
			return true;
		}

		if (op1 == '+' || op1 == '-') {
			if (op2 == '+' || op2 == '-') {
				return true;
			}
		}

		return false;
	}

	static boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static Pair<Double, Integer> strToDouble(String s, int index) {
		int len = s.length();
		if (index >= len) {
			return null;
		}

		StringBuilder temp = new StringBuilder();
		while (index < len && (s.charAt(index) == ' ' || s.charAt(index) == '\t')) {
			++index;
		}

		if (index >= len) {
			return null;
		}

		if (s.charAt(index) == '-') {
			temp.append('-');
			++index;
		}

		for (; index < len; ++index) {
			char ch = s.charAt(index);
			if (ch != '.' && !isDigit(ch)) {// 2.5
				// its an operator then break
				break;
			}

			temp.append(ch);
		}

		String result=temp.toString();
		return new Pair<Double, Integer>(Double.parseDouble(result), index);

	}

	static List<Token> convertToPostfix(String expr) {
		List<Token> postFix = new ArrayList<Token>();

		Stack<Character> operators = new Stack<Character>();
		int len = expr.length();
		for (int i = 0; i < len;) {
			char ch = expr.charAt(i);
			if (ch == ' ' || ch == '\t') {
				++i;
				continue;
			}

			if (isOperator(ch)) {// +
				/// *
				while (!operators.empty() && preced(operators.peek(), ch)) {
					postFix.add(new TokenOperator(operators.peek()));
					operators.pop();
				}
				operators.push(ch);
				++i;
			} else {
				// 3.27 is an operand
				//
				Pair<Double, Integer> p = strToDouble(expr, i);
				i = p.second;
				postFix.add(new TokenOperand(p.first));
			}
		}

		while (!operators.empty()) {
			postFix.add(new TokenOperator(operators.peek()));
			operators.pop();
		}

		return postFix;
	}

	public static double evaluate(List<Token> postFix) {
		Stack<Double> operands = new Stack<Double>();
		for (Token x : postFix) {
			if (x.isOperator()) {
				double val2 = operands.peek();
				operands.pop();
				double val1 = operands.peek();
				operands.pop();

				char op = ((TokenOperator) x).getValue();
				if (op == '+') {
					operands.push(val1 + val2);
				} else if (op == '-') {
					operands.push(val1 - val2);
				} else if (op == '*') {
					operands.push(val1 * val2);
				} else if (op == '/') {
					operands.push(val1 / val2);
				}

			} else {
				double val = ((TokenOperand) x).getValue();
				operands.push(val);
			}
		}

		if (operands.empty()) {
			return 0;
		}

		return operands.peek();
	}

	public static double evaluate(String expr) {
		List<Token> postFixExpression = convertToPostfix(expr);
		return evaluate(postFixExpression);
	}

	public static void main(String[] args) {
		double result = ExpressionEvaluation.evaluate("3+6*5-1/2.5");
		System.out.println("evaluate(3+6*5-1/2.5) = " + result);
	}
}