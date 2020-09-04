package com.educative.coderust.chapter3;

import java.util.Arrays;
import java.util.List;

class FindMissing {
	static int findMissing(List<Integer> input) {
		// calculate sum of all elements
		// in input list
		int sumOfElements = 0;
		for (Integer x : input) {
			sumOfElements += x;
		}

		// There is exactly 1 number missing
		int n = input.size() + 1;
		int actualSum = (n * (n + 1)) / 2;
		return actualSum - sumOfElements;
	}

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(3, 7, 1, 2, 8, 4, 5);
		System.out.println("Original = " + list);
		int missingNumber = findMissing(list);
		System.out.println("The missing number is " + missingNumber);
	}
}