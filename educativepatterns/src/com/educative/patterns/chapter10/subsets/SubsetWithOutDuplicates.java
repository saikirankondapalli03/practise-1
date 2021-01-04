package com.educative.patterns.chapter10.subsets;

import java.util.*;

class SubsetWithOutDuplicates {

	public static List<List<Integer>> findSubsets(int[] nums) {
		// sort the numbers to handle duplicates
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		result.add(new ArrayList<>());
		int startIndex = 0, endIndex = 0;
		for (int i = 0; i < nums.length; i++) {
			startIndex = 0;
			// if current and the previous elements are same, create new subsets only from
			// the subsets and  added in the previous step
			if (i > 0 && nums[i] == nums[i - 1])
				startIndex = endIndex + 1;
			endIndex = result.size() - 1;
			for (int j = startIndex; j <= endIndex; j++) {
				// create a new subset from the existing subset and add the current element to
				// it
				List<Integer> set = new ArrayList<>(result.get(j));
				set.add(nums[i]);
				result.add(set);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<List<Integer>> result = SubsetWithOutDuplicates.findSubsets(new int[] { 1, 5, 3, 3 });
		System.out.println("Here is the list of subsets: " + result);

		result = SubsetWithOutDuplicates.findSubsets(new int[] { 1, 5, 3, 3 });
		System.out.println("Here is the list of subsets: " + result);
	}
}
