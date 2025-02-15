package com.educative.chapter3.dp;

class Staircase {

	  public int CountWays(int n) {
	    int dp[] = new int[n+1];
	    dp[0] = 1;
	    dp[1] = 1;
	    dp[2] = 2;

	    for(int i=3; i<=n; i++)
	      dp[i] = dp[i-1] + dp[i-2] + dp[i-3];

	    return dp[n];
	  }

	  public static void main(String[] args) {
	    Staircase sc = new Staircase();
	    System.out.println(sc.CountWays(3));
	    System.out.println(sc.CountWays(4));
	    System.out.println(sc.CountWays(5));
	  }
	}

