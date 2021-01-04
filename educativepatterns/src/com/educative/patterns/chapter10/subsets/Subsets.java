package com.educative.patterns.chapter10.subsets;

import java.util.*;

class Subsets {

	public static List<List<Integer>> findSubsets(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		// start by adding the empty subset
		result.add(new ArrayList<>());
		for (int currentNumber : nums) {
			// we will take all existing subsets and insert the current number in them to
			// create new subsets
			int n = result.size();
			for (int i = 0; i < n; i++) {
				// create a new subset from the existing subset and insert the current element
				// to it
				List<Integer> set = new ArrayList<>(result.get(i));
				set.add(currentNumber);
				result.add(set);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<List<Integer>> result = Subsets.findSubsets(new int[] { 1, 3,3 });
		System.out.println("Here is the list of subsets: " + result);

		result = Subsets.findSubsets(new int[] { 1, 5, 3 ,9});
		System.out.println("Here is the list of subsets: " + result);
	}
}

