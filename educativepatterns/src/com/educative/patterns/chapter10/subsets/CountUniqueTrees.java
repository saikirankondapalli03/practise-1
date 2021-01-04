package com.educative.patterns.chapter10.subsets;

import java.util.HashMap;
import java.util.Map;

class CountUniqueTrees {
	public int countTrees(int n) {
		if (n <= 1)
			return 1;
		int count = 0;
		for (int i = 1; i <= n; i++) { 
			// making 'i' root of the tree
			int countOfLeftSubtrees = countTrees(i - 1);
			int countOfRightSubtrees = countTrees(n - i);
			count += (countOfLeftSubtrees * countOfRightSubtrees);
		}
		return count;
	}

	Map<Integer, Integer> map = new HashMap<>();

	public int countTreesMem(int n) {
		if (map.containsKey(n))
			return map.get(n);

		if (n <= 1)
			return 1;
		int count = 0;
		for (int i = 1; i <= n; i++) {
			// making 'i' root of the tree
			int countOfLeftSubtrees = countTreesMem(i - 1);
			int countOfRightSubtrees = countTreesMem(n - i);
			count += (countOfLeftSubtrees * countOfRightSubtrees);
		}
		map.put(n, count);
		return count;
	}

	public static void main(String[] args) {
		CountUniqueTrees ct = new CountUniqueTrees();
		int count = ct.countTrees(3);
		System.out.print("Total trees: " + count);
	}
}