package com.educative.chapter3.recursion;

public class StairCaseFee {

	public int findMinFee(int[] fee) {
		return findMinFeeRecursive(fee, 0);
	}

	private int findMinFeeRecursive(int[] fee, int currentIndex) {
		if (currentIndex > fee.length - 1)
			return 0;

		// if we take 1 step, we are left with 'n-1' steps;
		int take1Step = findMinFeeRecursive(fee, currentIndex + 1);
		// similarly, if we took 2 steps, we are left with 'n-2' steps;
		int take2Step = findMinFeeRecursive(fee, currentIndex + 2);
		// if we took 3 steps, we are left with 'n-3' steps;
		int take3Step = findMinFeeRecursive(fee, currentIndex + 3);

		int min = Math.min(Math.min(take1Step, take2Step), take3Step);

		return min + fee[currentIndex];
	}

	public static void main(String[] args) {
		StairCaseFee sc = new StairCaseFee();
		int[] fee = { 1, 2, 5, 2, 1, 2 };
		System.out.println(sc.findMinFee(fee));
		fee = new int[] { 2, 3, 4, 5 };
		System.out.println(sc.findMinFee(fee));
	}
}
