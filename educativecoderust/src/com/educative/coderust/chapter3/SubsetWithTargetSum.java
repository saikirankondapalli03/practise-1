package com.educative.coderust.chapter3;

import java.util.ArrayList;

public class SubsetWithTargetSum {
	static void print(ArrayList<Integer> al) {
		for (Integer i : al)
			System.out.println(i);
	}

	static void findSubset(int A[], int i, ArrayList<Integer> ss, int ssSum, int target) {
		if (target == ssSum) {
			print(ss);
			System.exit(0);
		}
		if (i == A.length)
			return;
		ArrayList<Integer> newss = new ArrayList<Integer>(ss);
		newss.add(A[i]);
		findSubset(A, i + 1, newss, ssSum + A[i], target);
		findSubset(A, i + 1, ss, ssSum, target);
	}

	public static void main(String[] args) {
		int A[] = { 1, -2, 1, -7, 9, 3, 12 };
		ArrayList<Integer> ss = new ArrayList<Integer>();
		findSubset(A, 0, ss, 0, 10);
	}
}