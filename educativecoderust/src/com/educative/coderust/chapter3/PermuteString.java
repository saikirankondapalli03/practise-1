package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PermuteString {
	public static List<String> result = new ArrayList<String>();

	static void swapChar(char[] input, int i, int j) {
		System.out.println("before ==>" + String.valueOf(input) + "replace" + i +"with" +j);
		char temp = input[i];
		input[i] = input[j];
		input[j] = temp;
		System.out.println("after ==>" + String.valueOf(input));
		
	}

	static void permuteString(char[] input, int currentIndex) {

		if (currentIndex == input.length - 1) {
			String addInput = new String(input);
			result.add(addInput);
			return;
		}

		for (int i = currentIndex; i <= input.length - 1; i++) {
			swapChar(input, currentIndex, i);
			System.out.println("Iteration ==> "+ currentIndex);
			permuteString(input, currentIndex + 1);
			System.out.println("Iteration ==> "+ currentIndex);
			swapChar(input, currentIndex, i);
		}
	}

	static List<String> permuteString(String input) {
		permuteString(input.toCharArray(), 0);
		return result;
	}

	public static void main(String[] args) {
		String input = "bad";
		System.out.println("All permutations of " + input);
		List<String> result = permuteString(input);
		System.out.println(Arrays.toString(result.toArray()));
	}
}
