package com.educative.chapter3.dp;

public class HouseThief {

	  public int findMaxSteal(int[] wealth) {
	    if(wealth.length == 0) return 0;
	    int dp[] = new int[wealth.length+1]; // '+1' to handle the zero house
	    dp[0] = 0; // if there are no houses, the thief can't steal anything
	    dp[1] = wealth[0]; // only one house, so the thief have to steal from it

	    // please note that dp[] has one extra element to handle zero house
	    for(int i=1; i < wealth.length; i++) {
			int a = wealth[i] + dp[i-1];
			int b = dp[i];
			dp[i+1] = Math.max(a, b);
		}

	    return dp[wealth.length];
	  }

	  public static void main(String[] args) {
	    HouseThief ht = new HouseThief();
	    int[] wealth = {2,4,10,15};
	    System.out.println(ht.findMaxSteal(wealth));
	    wealth = new int[]{2, 10, 14, 8, 1};
	    System.out.println(ht.findMaxSteal(wealth));
	  }
}
