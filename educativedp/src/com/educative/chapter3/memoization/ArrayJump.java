package com.educative.chapter3.memoization;

class ArrayJump {

	  public int countMinJumps(int[] jumps) {
	    int dp[] = new int[jumps.length];
	    return this.countMinJumpsRecursive(dp, jumps, 0);
	  }

	  private int countMinJumpsRecursive(int[] dp, int[] jumps, int currentIndex) {
	    // if we have reached the last index, we don't need any more jumps
	    if( currentIndex == jumps.length - 1)
	      return 0;

	    // If an element is 0, then we cannot move through that element
	    if (jumps[currentIndex] == 0)
	      return Integer.MAX_VALUE;

	    // if we have already solved this problem, return the result
	    if(dp[currentIndex] != 0)
	      return dp[currentIndex];

	    int totalJumps = Integer.MAX_VALUE;
	    int start = currentIndex + 1;
	    int end = currentIndex + jumps[currentIndex];
	    while(start < jumps.length && start <= end) {
	      // jump one step and recurse for the remaining array
	      int minJumps = countMinJumpsRecursive(dp, jumps, start++);
	      if(minJumps != Integer.MAX_VALUE)
	        totalJumps = Math.min(totalJumps, minJumps + 1);
	    }
	    dp[currentIndex] = totalJumps;
	    return dp[currentIndex];
	  }

	  public static void main(String[] args) {
	    ArrayJump aj = new ArrayJump();
	    int[] jumps = {2, 1, 1, 1, 4};
	    System.out.println(aj.countMinJumps(jumps));
	    jumps = new int[]{1, 1, 3, 6, 9, 3, 0, 1, 3};
	    System.out.println(aj.countMinJumps(jumps));
	  }
	}
