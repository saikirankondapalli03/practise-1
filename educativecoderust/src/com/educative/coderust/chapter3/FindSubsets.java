package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class FindSubsets {
	static int getBit(int num, int bit) {
		int temp = (1 << bit) & num;
		if (temp == 0) {
			return 0;
		}
		return 1;
	}

	static void findAllSubsets(List<Integer> v, List<HashSet<Integer>> sets) {
		int subsetsCount = (int) (Math.pow((double) 2, (double) v.size()));
		for (int i = 0; i < subsetsCount; ++i) {
			HashSet<Integer> set = new HashSet<Integer>();
			for (int j = 0; j < v.size(); ++j) {
				System.out.println("i="+i+"j="+j);
				if (getBit(i, j) == 1) {
					int x = v.get(j);
					set.add(x);
				}
			}
			sets.add(set);
		}
	}

	public static void main(String[] args) {
		Integer[] myints = new Integer[] { 2, 5, 7 };
		List<Integer> v = new ArrayList<Integer>();
		for (Integer i : myints) {
			v.add(i);
		}
		List<HashSet<Integer>> subsets = new ArrayList<HashSet<Integer>>();

		findAllSubsets(v, subsets);

		for (int i = 0; i < subsets.size(); ++i) {
			System.out.print("{ ");
			for (Integer it : subsets.get(i)) {
				System.out.print(it + " ");
			}
			System.out.println("}");
		}
	}
}
