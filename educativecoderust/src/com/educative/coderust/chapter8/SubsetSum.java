package com.educative.coderust.chapter8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// similiar to FindSubsets.java

class SubsetSum {
	static int getBit(int num, int bit) {
		int temp = (1 << bit);
		temp = temp & num;
		if (temp == 0) {
			return 0;
		}
		return 1;
	}

	static List<HashSet<Integer>> getKSumSubsets(List<Integer> v, Integer targetSum) {

		List<HashSet<Integer>> subsets = new ArrayList<HashSet<Integer>>();

		int subsets_count = (int) (Math.pow((double) 2, (double) v.size()));

		for (int i = 0; i < subsets_count; ++i) {
			HashSet<Integer> set = new HashSet<Integer>();
			int sum = 0;

			for (int j = 0; j < v.size(); ++j) {
				if (getBit(i, j) == 1) {
					sum += v.get(j);
					if (sum > targetSum) {
						break;
					}
					set.add(v.get(j));
				}
			}
			if (sum == targetSum) {
				subsets.add(set);
			}
		}
		return subsets;
	}

	public static void main(String[] args) {
		// initializing vector
		int[] myints = { 8, 13, 3, 22, 17, 39, 87, 45, 36 };
		List<Integer> vec = new ArrayList<Integer>();
		for (Integer i : myints) {
			vec.add(i);
		}

		// computing subsets
		List<HashSet<Integer>> subsets = new ArrayList<HashSet<Integer>>();
		subsets = getKSumSubsets(vec, 125);

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