package com.gs;

public class MinPathSum {
	/*
	 * 
	 * [ [1,3,1], [1,5,1], [4,2,1] ]
	 */
	public static int minPathSum(int[][] grid) {
		int[] dp = new int[grid[0].length];
		for (int i = grid.length - 1; i >= 0; i--) {
			for (int j = grid[0].length - 1; j >= 0; j--) {
				if (i == grid.length - 1 && j != grid[0].length - 1)
					dp[j] = grid[i][j] + dp[j + 1];
				else if (i != grid.length - 1 && j == grid[0].length - 1)
					dp[j] = grid[i][j] + dp[j];
				else if (i != grid.length - 1 && j != grid[0].length - 1)
					dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);
				else
					dp[j] = grid[i][j];
			}
		}
		return dp[0];
	}

	
	public int minPathSums(int[][] grid) {
		int[] dp = new int[grid[0].length];
		for (int i = grid.length - 1; i >= 0; i--) {
			for (int j = grid[0].length - 1; j >= 0; j--) {
				if (i == grid.length - 1 && j != grid[0].length - 1)
					dp[j] = grid[i][j] + dp[j + 1];
				else if (j == grid[0].length - 1 && i != grid.length - 1)
					dp[j] = grid[i][j] + dp[j];
				else if (j != grid[0].length - 1 && i != grid.length - 1)
					dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);
				else
					dp[j] = grid[i][j];
			}
		}
		return dp[0];
	}

	
	
	public int calculate(int[][] grid, int i, int j) {
		if (i == grid.length || j == grid[0].length)
			return Integer.MAX_VALUE;
		if (i == grid.length - 1 && j == grid[0].length - 1)
			return grid[i][j];
		int x = calculate(grid, i + 1, j);
		int y = calculate(grid, i, j + 1);
		return grid[i][j] + Math.min(x, y);
	}

	public int minPathSumRec(int[][] grid) {
		return calculate(grid, 0, 0);
	}

	public static void main(String[] args) {
		System.out.println(MinPathSum.minPathSum(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }));
	}
}
