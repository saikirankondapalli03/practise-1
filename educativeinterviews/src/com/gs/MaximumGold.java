package com.gs;


class MaximumGold {
    public int getMaximumGold(int[][] grid) {
        if(grid == null || grid.length == 0)
            return 0;
        
        int m = grid.length, n = grid[0].length;
        int [] max = new int [1];
        for(int i = 0 ; i < m ; i ++) {
            for(int j = 0 ; j < n ; j ++) {
                if(grid[i][j] != 0) {
                   dfs(grid,i,j,0,max);
                }
            }
        }
        return max[0];
    }
    
    private void dfs(int [][] grid, int i , int j, int currSum, int [] max) {
        if(i<0 || j<0 || i>= grid.length ||j>= grid[0].length || grid[i][j] == 0) {
            max[0] = Math.max(max[0], currSum);
            return;
        } 
        int temp = grid[i][j];
        grid[i][j] = 0;
        dfs(grid,i-1,j, currSum + temp,max);
        dfs(grid,i+1,j, currSum + temp,max);
        dfs(grid,i,j-1, currSum + temp,max); 
        dfs(grid,i,j+1, currSum + temp,max);
        grid[i][j] = temp;
    }
}