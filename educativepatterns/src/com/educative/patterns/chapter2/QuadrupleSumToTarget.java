package com.educative.patterns.chapter2;

import java.util.*;

// same as TripletSumToZero
class QuadrupleSumToTarget {

	public static List<List<Integer>> searchQuadruplets(int[] arr, int target) {
		Arrays.sort(arr);
		List<List<Integer>> quadruplets = new ArrayList<>();
		int outerIterations=arr.length - 3;
		for (int first = 0; first < outerIterations; first++) {
			if (first > 0 && arr[first] == arr[first - 1]) // skip same element to avoid duplicate quadruplets
				continue;
			int innerIterations=arr.length - 2;
			
			for (int second = first + 1; second < innerIterations; second++) {
				if (second > first + 1 && arr[second] == arr[second - 1]) // skip same element to avoid duplicate quadruplets
					continue;
				System.out.println(first+"+"+second+"+");
				
				searchPairs(arr, target, first, second, quadruplets);
			}
		}
		return quadruplets;
	}
//{ -3, -1, 1, 1, 2, 4 }
	private static void searchPairs(int[] arr, int targetSum, int first, int second, List<List<Integer>> quadruplets) {
		int left = second + 1;
		int right = arr.length - 1;
		while (left < right) {
			System.out.println(first+"-"+second+"-"+left+"-"+right);
			int currentSum = arr[first] + arr[second] + arr[left] + arr[right];
			if (currentSum == targetSum) { // found the quadruplet
				quadruplets.add(Arrays.asList(arr[first], arr[second], arr[left], arr[right]));
				left++;
				right--;
				while (left < right && arr[left] == arr[left - 1])
					left++; // skip same element to avoid duplicate quadruplets
				while (left < right && arr[right] == arr[right + 1])
					right--; // skip same element to avoid duplicate quadruplets
			} else if (currentSum < targetSum)
				left++; // we need a pair with a bigger sum
			else
				right--; // we need a pair with a smaller sum
		}
	}

	public static void main(String[] args) {
		System.out.println(QuadrupleSumToTarget.searchQuadruplets(new int[] { -3, -1, 1, 1, 2, 4 }, 1));
		//System.out.println(QuadrupleSumToTarget.searchQuadruplets(new int[] { 2, 0, -1, 1, -2, 2 }, 2));
	}
}