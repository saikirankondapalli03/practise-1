package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrintSubsets {
	static void printSubsets(char set[]) {
		int n = set.length;

		// Run a loop for printing all 2^n
		// subsets one by one
		
		// for 3 digit length number 
		// i ranges from 0 to 8
		// j ranges from 0 to 3
		for (int i = 0; i < (1 << n); i++) {
			System.out.print("{ ");

			// Print current subset
			for (int j = 0; j < n; j++)

				// (1<<j) is a number with jth bit 1
				// so when we 'and' them with the
				// subset number we get which numbers
				// are present in the subset and which
				// are not
				if ((i & (1 << j)) > 0) {
				//	System.out.println("i=>"+i+"j=>"+j);
					System.out.print(set[j] + " ");
				}
					

			System.out.println("}");
		}
	}

	public static void main(String[] args) {

		int[] S = { 1, 2, 3 };

		// sort the set
		//Arrays.sort(S);

		//findPowerSet(S);

		char set[] = { '1', '3', '5','9' };
		printSubsets(set);
	}

	// Iterative function to print all distinct subsets of S
	public static void findPowerSet(int[] S) {
		// N stores total number of subsets
		int N = (int) Math.pow(2, S.length);
		Set<List<Integer>> set = new HashSet<>();

		// generate each subset one by one
		for (int i = 0; i < N; i++) {
			List<Integer> subset = new ArrayList<>();

			// check every bit of i
			for (int j = 0; j < S.length; j++) {
				// if j'th bit of i is set, append S[j] to subset
				if ((i & (1 << j)) != 0) {
					subset.add(S[j]);
				}
			}

			// insert the subset into the set
			set.add(subset);
		}

		// print all subsets present in the set
		for (List<Integer> subset : set) {
			System.out.println(subset);
		}
	}
}
