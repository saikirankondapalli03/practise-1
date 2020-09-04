package com.educative.chapter3.dp;

class ArrayJump {

	public int countMinJumps(int arr[]) {

		int[] dp = new int[arr.length];
		dp[0] = 0;
		for (int i = 1; i < arr.length; i++) {
			dp[i] = Integer.MAX_VALUE;
		}

		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				System.out.println(i + "--" + j);
				if (arr[j] + j >= i) {
					if (dp[i] > dp[j] + 1) {
						// result[i] = j;
						dp[i] = dp[j] + 1;
					}
				}
			}
		}

		return dp[dp.length - 1];
	}

	public int countMinJumpsDup(int[] jumps) {
		int[] dp = new int[jumps.length];

		// initialize with infinity, except the first index which should be zero as we
		// start from there
		for (int i = 1; i < jumps.length; i++)
			dp[i] = Integer.MAX_VALUE;

		for (int start = 0; start < jumps.length - 1; start++) {
			for (int end = start + 1; end < jumps.length; end++) {
				if (end <= start + jumps[start]) {
					dp[end] = Math.min(dp[end], dp[start] + 1);
				}
			}
		}
		return dp[jumps.length - 1];
	}


	  
	public static void main(String[] args) {
		ArrayJump aj = new ArrayJump();
		int[] jumps = {1,3,5,8,9,2,6,7,6,8,9 };
		int[] results = new int[jumps.length];
		System.out.println(aj.countMinJumps(jumps));
		jumps = new int[] { 1, 1, 3, 6, 9 };
		results = new int[jumps.length];
		System.out.println(aj.countMinJumps(jumps));
	}
}