package com.educative.chapter1.dp;

class Knapsack {

	  public int solveKnapsack(int[] profits, int[] weights, int capacity) {
	    // basic checks
	    if (capacity <= 0 || profits.length == 0 || weights.length != profits.length)
	      return 0;

	    int n = profits.length;
	    int[][] dp = new int[n][capacity + 1];

	    // populate the capacity=0 columns, with '0' capacity we have '0' profit
	    for(int i=0; i < n; i++)
	      dp[i][0] = 0;

	    // if we have only one weight, we will take it if it is not more than the capacity
	    for(int c=0; c <= capacity; c++) {
	      if(weights[0] <= c)
	        dp[0][c] = profits[0];
	    }

	    // process all sub-arrays for all the capacities
	    for(int i=1; i < n; i++) {
	      for(int j=1; j <= capacity; j++) {
	        int profit1= 0, profit2 = 0;
	        // include the item, if it is not more than the capacity
	        if(weights[i] <= j)
	          profit1 = profits[i] + dp[i-1][j-weights[i]];
	        // exclude the item
	        profit2 = dp[i-1][j];
	        // take maximum
	        int result= Math.max(profit1, profit2);
	        dp[i][j] =result;
	      }
	    }

	    // maximum profit will be at the bottom-right corner.
	    return dp[n-1][capacity];
	  }

	  public static void main(String[] args) {
	    Knapsack ks = new Knapsack();
	    int[] profits = {1, 6, 10, 16};
	    int[] weights = {1, 2, 3, 5};
	    int maxProfit = ks.solveKnapsack(profits, weights, 7);
	    System.out.println("Total knapsack profit ---> " + maxProfit);
	    maxProfit = ks.solveKnapsack(profits, weights, 6);
	    System.out.println("Total knapsack profit ---> " + maxProfit);
	  }
	}