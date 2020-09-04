package com.educative.chapter3.recursion;

class ArrayJump {

	public int countMinJumps(int[] jumps) {
		return this.countMinJumpsRecursive(jumps, 0);
	}

	static int count = 0;

	public static int minJumpsNeededToReachArrayEnd(int[] array) {

		int jumpsSoFar = 0, index = 0;

		return minJumpsNeededToReachArrayEnd(array, index, jumpsSoFar);
	}

	//how many jumps he made so far ? 
	private static int minJumpsNeededToReachArrayEnd(int[] array, int index, int jumpsSoFar) {

		if (index >= array.length - 1) {
			return jumpsSoFar;
		}

		int maxReachIndex = array[index];

		if (maxReachIndex == 0) {
			return Integer.MAX_VALUE;
		}

		int minJumps = Integer.MAX_VALUE;

		for (int jump = 1; jump <= maxReachIndex; jump++) {
			minJumps = Math.min(minJumps, minJumpsNeededToReachArrayEnd(array, index + jump, jumpsSoFar + 1));
		}

		return minJumps;
	}
	
	
	//how many jumps can he make from given index ??
	private int countMinJumpsRecursive(int[] jumps, int currentIndex) {
		// if we have reached the last index, we don't need any more jumps
		if (currentIndex == jumps.length - 1)
			return 0;

		if (jumps[currentIndex] == 0)
			return Integer.MAX_VALUE;

		int totalJumps = Integer.MAX_VALUE;
		int start = currentIndex + 1;
		int end = currentIndex + jumps[currentIndex];
		while (start < jumps.length && start <= end) {
			// jump one step and recurse for the remaining array
			int minJumps = countMinJumpsRecursive(jumps, start);
			if (minJumps != Integer.MAX_VALUE)
				totalJumps = Math.min(totalJumps, minJumps + 1);
			start= start+1;
		}
		
		return totalJumps;
	}

	public int jump(int[] nums) {
		int n = nums.length;
		if (n < 2)
			return 0;

		// max position one could reach
		// starting from index <= i
		int maxPos = nums[0];
		// max number of steps one could do
		// inside this jump
		int maxSteps = nums[0];

		int jumps = 1;
		for (int i = 1; i < n; ++i) {
			// if to reach this point
			// one needs one more jump
			if (maxSteps < i) {
				++jumps;
				maxSteps = maxPos;
			}
			maxPos = Math.max(maxPos, nums[i] + i);
		}
		return jumps;
	}
	
	public static boolean canJump(int[] nums) {
	    int n = nums.length;

	    // max position one could reach 
	    // starting from index <= i
	    int maxPos = nums[0];

	    for (int i = 1; i < n; ++i) {
	      // if one could't reach this point
	      if (maxPos < i) {
	        return false;
	      }
	      maxPos = Math.max(maxPos, nums[i] + i);
	    }
	    return true;
	  }




	public static void main(String[] args) {
		ArrayJump aj = new ArrayJump();
		System.out.println(minJumpsNeededToReachArrayEnd(new int[] {2, 1, 1, 1, 4}));
		int[] jumps = { 2, 1, 0, 1, 4 };
		 jumps = new int[] { 1, 1, 3, 6, 9, 3, 0, 1, 3 };
		System.out.println(minJumpsNeededToReachArrayEnd(jumps));
		//System.out.println(minJumpsNeededToReachArrayEndDPBT(jumps));
	}
}