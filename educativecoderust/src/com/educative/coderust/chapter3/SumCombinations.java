package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * 


The Choice: Defining Our Decision Space                            
The Constraints: Directing Our Recursion                           
Our Goal: When Recursion Bottoms Out
 */
class SumCombinations {
	static String print(ArrayList<ArrayList<Integer>> arr) {
		String result = "";
		for (int i = 0; i < arr.size(); i++) {
			result += "[";
			for (int j = 0; j < arr.get(i).size(); j++) {
				result += Integer.toString(arr.get(i).get(j)) + ",";
			}
			result = result.replaceAll(",$", "");
			result += "]";
		}
		return result;
	}

	static void printAllSumRec(int target, int currentSum, int start, ArrayList<ArrayList<Integer>> output,
			ArrayList<Integer> result) {

		if (target == currentSum) {
			output.add((ArrayList) result.clone());
		}

		for (int i = start; i < target; ++i) {
			int tempSum = currentSum + i;
			if (tempSum <= target) {

				result.add(i);
				printAllSumRec(target, tempSum, i, output, result);
				result.remove(result.size() - 1);
			} else {
				return;
			}
		}
	}

	public static List<List<Integer>> allCombs(int N) {

		int currentSum = 0;

		List<List<Integer>> result = new ArrayList<>();

		List<Integer> current = new ArrayList<>();

		return allCombs(currentSum, N, result, current);
	}

	private static List<List<Integer>> allCombs(int currentSum, int N, List<List<Integer>> result,
			List<Integer> current) {

		if (currentSum > N) {
			return result;
		}

		if (currentSum == N) {
			result.add(current);
			return result;
		}
		// 4

		// 1, 1, 1

		for (int i = 1; i < N; i++) {

			List<Integer> newCurrent = new ArrayList<>();

			newCurrent.addAll(current); // 1, 1, 1

			newCurrent.add(i);// 1,1, 1, 1

			result = allCombs(currentSum + i, N, result, newCurrent);
		}

		return result;
	}

	static ArrayList<ArrayList<Integer>> printAllSum(int target) {
		ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> result = new ArrayList<Integer>();
		printAllSumRec(target, 0, 1, output, result);
		return output;
	}

	public static void main(String[] args) {
		int n = 4;
		ArrayList<ArrayList<Integer>> result = printAllSum(n);
		// System.out.println("All sum combinations of " + n);
		System.out.println(print(result));

		System.out.println(allCombs(4));
	}
}