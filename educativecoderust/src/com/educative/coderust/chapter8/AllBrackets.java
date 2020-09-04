package com.educative.coderust.chapter8;

import java.util.ArrayList;

class AllBrackets {
	static void print(ArrayList<ArrayList<Character>> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i).toString());
		}
	}

	static void printAllBacesRec(int n, int leftCount, int rightCount, ArrayList<Character> output,
			ArrayList<ArrayList<Character>> result) {

		if (leftCount >= n && rightCount >= n) {
			result.add((ArrayList) output.clone());
		}

		if (leftCount < n) {
			output.add('{');
			printAllBacesRec(n, leftCount + 1, rightCount, output, result);
			output.remove(output.size() - 1);
		}

		if (rightCount < leftCount) {
			output.add('}');
			printAllBacesRec(n, leftCount, rightCount + 1, output, result);
			output.remove(output.size() - 1);
		}
	}

	static ArrayList<ArrayList<Character>> printAllBraces(int n) {
		ArrayList<ArrayList<Character>> result = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> output = new ArrayList<Character>();
		printAllBacesRec(n, 0, 0, output, result);
		return result;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<Character>> result = new ArrayList<ArrayList<Character>>();
		result = printAllBraces(3);
		print(result);
	}
}