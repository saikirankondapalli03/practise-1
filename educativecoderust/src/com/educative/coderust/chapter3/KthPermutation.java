package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.List;

class KthPermutation {
	static int factorial(int n) {
		if (n == 0 || n == 1)
			return 1;
		return n * factorial(n - 1);
	}

	static void findKthPermutation(List<Character> v, int k, StringBuilder result) {

		if (v.isEmpty()) {
			return;
		}

		int n = v.size();
		// count is number of permutations starting with first digit
		int count = factorial(n - 1);
		int selected = (k - 1) / count;

		result.append(v.get(selected));
		v.remove(selected);

		k = k - (count * selected);
		findKthPermutation(v, k, result);
	}

	static String getPermutation(int n, int k) {
		List<Character> v = new ArrayList<Character>();
		v.add('a');
		v.add('b');
		v.add('c');
		v.add('d');
		
		StringBuilder result = new StringBuilder();
		findKthPermutation(v, k, result);
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println("8th permutation of 1234 = " + getPermutation(4, 8));
	}
}