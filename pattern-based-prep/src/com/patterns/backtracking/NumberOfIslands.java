package com.patterns.backtracking;

/**
 * Number of Islands Problem - LeetCode 200
 * 
 * Problem: Given a 2D binary grid which represents a map of '1's (land) and '0's (water),
 * return the number of islands.
 * 
 * An island is surrounded by water and is formed by connecting adjacent lands 
 * horizontally or vertically. You may assume all four edges of the grid are surrounded by water.
 * 
 * Algorithm: DFS (Depth First Search)
 * 1. Iterate through each cell in the grid
 * 2. When we find a '1' (unvisited land), we've discovered a new island
 * 3. Use DFS to mark all connected land cells as visited (change '1' to '0')
 * 4. Increment island counter
 * 
 * Time Complexity: O(M × N) where M = rows, N = columns
 * Space Complexity: O(M × N) in worst case due to recursion stack
 */
public class NumberOfIslands {
    
    // Four directions: right, left, down, up
    private static final int[][] DIRECTIONS = {
        {0, 1},   // right
        {0, -1},  // left  
        {1, 0},   // down
        {-1, 0}   // up
    };
    
    /**
     * Main method to count number of islands in the grid
     * @param grid 2D character array representing the map
     * @return number of islands found
     */
    public static int numIslands(char[][] grid) {
        // Edge case: empty or null grid
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int islandCount = 0;
        
        // Traverse every cell in the grid
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                
                // If we find unvisited land ('1'), we've found a new island
                if (grid[row][col] == '1') {
                    // Use DFS to mark all connected land as visited
                    markIslandAsVisited(grid, row, col);
                    
                    // Increment island counter
                    islandCount++;
                }
            }
        }
        
        return islandCount;
    }
    
    /**
     * DFS helper method to mark all connected land cells as visited
     * This prevents counting the same island multiple times
     * 
     * @param grid the 2D grid
     * @param row current row position
     * @param col current column position
     */
    private static void markIslandAsVisited(char[][] grid, int row, int col) {
        // Base case: check boundaries and if current cell is water or already visited
        if (row < 0 || row >= grid.length ||           // out of row bounds
            col < 0 || col >= grid[0].length ||        // out of column bounds
            grid[row][col] == '0') {                   // water or already visited
            return;
        }
        
        // Mark current land cell as visited by changing '1' to '0'
        grid[row][col] = '0';
        
        // Recursively visit all 4 adjacent cells (right, left, down, up)
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            markIslandAsVisited(grid, newRow, newCol);
        }
    }
    
    /**
     * Test method with example cases
     */
    public static void main(String[] args) {
        // Test case 1: Expected output = 1
        // This grid has one large island
        char[][] grid1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        
        System.out.println("Test Case 1:");
        printGrid(grid1);
        System.out.println("Number of islands: " + numIslands(grid1));
        System.out.println();
        
        // Test case 2: Expected output = 3
        // This grid has three separate islands
        char[][] grid2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        
        System.out.println("Test Case 2:");
        printGrid(grid2);
        System.out.println("Number of islands: " + numIslands(grid2));
    }
    
    /**
     * Helper method to print the grid for visualization
     */
    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}