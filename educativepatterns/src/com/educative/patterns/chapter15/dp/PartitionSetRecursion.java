package com.educative.patterns.chapter15.dp;

class PartitionSetRecursion {

	public boolean canPartition(int[] num) {
		int sum = 0;
		for (int i = 0; i < num.length; i++)
			sum += num[i];

		// if 'sum' is a an odd number, we can't have two subsets with equal sum
		if (sum % 2 != 0)
			return false;

		return this.canPartitionRecursive(num, sum / 2, 0);
	}

	private boolean canPartitionRecursive(int[] num, int sum, int currentIndex) {
		// base check
		if (sum == 0)
			return true;

		if (num.length == 0 || currentIndex >= num.length)
			return false;

		// recursive call after choosing the number at the currentIndex
		// if the number at currentIndex exceeds the sum, we shouldn't process this
		if (num[currentIndex] <= sum) {
			if (canPartitionRecursive(num, sum - num[currentIndex], currentIndex + 1))
				return true;
		}

		// recursive call after excluding the number at the currentIndex
		return canPartitionRecursive(num, sum, currentIndex + 1);
	}

	public static void main(String[] args) {
		PartitionSetRecursion ps = new PartitionSetRecursion();
		int[] num = { 1, 2, 3, 4 };
		System.out.println(ps.canPartition(num));
		num = new int[] { 1, 1, 3, 4, 7 };
		System.out.println(ps.canPartition(num));
		num = new int[] { 2, 3, 4, 6 };
		System.out.println(ps.canPartition(num));
	}
}
