package com.educative.chapter2.dp;

class RodCutting {

	  public int solveRodCutting(int[] lengths, int[] prices, int n) {
	    // base checks
	    if (n <= 0 || prices.length == 0 || prices.length != lengths.length)
	      return 0;

	    int lengthCount = lengths.length;
	    int[][] dp = new int[lengthCount][n + 1];

	    // process all rod lengths for all prices
	    for(int i=0; i < lengthCount; i++) {
	      for(int j=1; j <= n; j++) {
	        int p1=0, p2=0;
	        if(lengths[i] <= j)
	          p1 = prices[i] + dp[i][j-lengths[i]];
	        if( i > 0 )
	          p2 = dp[i-1][j];
	        dp[i][j] = Math.max(p1, p2);
	      }
	    }

	    // maximum price will be at the bottom-right corner.
	    return dp[lengthCount-1][n];
	  }

	  public static void main(String[] args) {
	    RodCutting rc = new RodCutting();
	    int[] lengths = {1, 2, 3, 4, 5};
	    int[] prices = {2, 6, 7, 10, 13};
	    int maxProfit = rc.solveRodCutting(lengths, prices, 5);
	    System.out.println(maxProfit);
	  }
	}

