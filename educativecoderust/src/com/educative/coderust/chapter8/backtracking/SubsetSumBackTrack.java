package com.educative.coderust.chapter8.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//this code has problem 
class SubsetSumBackTrack {
	static void getKSumSubsetsRec(List<Integer> list, List<Integer> partialList, Integer targetSum,
			List<HashSet<Integer>> result) {
		int sum = 0;
		for (Integer x : partialList) {
			sum += x;
		}

		if (sum == targetSum && partialList.size() > 0) {
			result.add(new HashSet<Integer>(partialList));
		} else if (sum > targetSum) {
			return;
		} else {
			for (int i = 0; i < list.size(); ++i) {
				ArrayList<Integer> newPartialList = new ArrayList<Integer>(partialList);
				newPartialList.add(list.get(i));

				List<Integer> subList = list.subList(i + 1, list.size());

				getKSumSubsetsRec(subList, newPartialList, targetSum, result);
			}
		}
	}

	static List<HashSet<Integer>> getKSumSubsets(List<Integer> list, Integer targetSum) {
		List<Integer> partialList = new ArrayList<Integer>();
		List<HashSet<Integer>> result = new ArrayList<HashSet<Integer>>();
		getKSumSubsetsRec(list, partialList, targetSum, result);

		return result;
	}

	public static void main(String[] args) {
		// initializing vector
		int[] myints = { 2, 5, 4 };
		List<Integer> vec = new ArrayList<Integer>();
		for (Integer i : myints) {
			vec.add(i);
		}

		// computing subsets
		List<HashSet<Integer>> subsets = new ArrayList<HashSet<Integer>>();
		subsets = getKSumSubsets(vec, 7);

		System.out.print("[");
		// printing subsets
		for (int i = 0; i < subsets.size(); ++i) {
			System.out.print("{");
			for (Integer it : subsets.get(i)) {
				System.out.print(it + ", ");
			}
			System.out.print("} ");
		}
		System.out.print("]");
	}

	

}