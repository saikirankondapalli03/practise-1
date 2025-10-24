package com.patterns.backtracking;

/**
 * Max Area of Island Problem - LeetCode 695
 * 
 * Problem: Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's 
 * (representing land) connected 4-directionally (horizontal or vertical). 
 * Find the maximum area of an island in the given 2D array. 
 * If no island exists, return 0.
 * 
 * Algorithm: DFS (Depth First Search)
 * 1. Iterate through each cell in the grid
 * 2. When we find a '1' (unvisited land), calculate its island area using DFS
 * 3. Track the maximum area found so far
 * 4. Mark visited cells as '0' to avoid recounting
 * 
 * Time Complexity: O(M × N) where M = rows, N = columns
 * Space Complexity: O(M × N) in worst case due to recursion stack
 */
public class MaxAreaOfIsland {
    
    // Four directions: right, left, down, up
    private static final int[][] DIRECTIONS = {
        {0, 1},   // right
        {0, -1},  // left  
        {1, 0},   // down
        {-1, 0}   // up
    };
    
    /**
     * Main method to find maximum area of island in the grid
     * @param grid 2D integer array representing the map (0=water, 1=land)
     * @return maximum area of any island found
     */
    public static int maxAreaOfIsland(int[][] grid) {
        // Edge case: empty or null grid
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int maxArea = 0;
        
        // Traverse every cell in the grid
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                
                // If we find unvisited land (1), calculate its area
                if (grid[row][col] == 1) {
                    // Use DFS to calculate area and mark island as visited
                        int currentArea = calculateIslandArea(grid, row, col);
                    
                    // Update maximum area if current is larger
                    maxArea = Math.max(maxArea, currentArea);
                }
            }
        }
        
        return maxArea;
    }
    
    /**
     * DFS helper method to calculate area of connected island
     * Also marks all connected land cells as visited (changes 1 to 0)
     * 
     * @param grid the 2D grid
     * @param row current row position
     * @param col current column position
     * @return area of the island starting from this cell
     */
    private static int calculateIslandArea(int[][] grid, int row, int col) {
        // Base case: check boundaries and if current cell is water or already visited
        if (row < 0 || row >= grid.length ||           // out of row bounds
            col < 0 || col >= grid[0].length ||        // out of column bounds
            grid[row][col] == 0) {                     // water or already visited
            return 0;
        }
        
        // Mark current land cell as visited by changing 1 to 0
        grid[row][col] = 0;
        
        // Current cell contributes 1 to the area
        int area = 1;
        
        // Add areas from all 4 adjacent cells (right, left, down, up)
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            area += calculateIslandArea(grid, newRow, newCol);
        }
        
        return area;
    }
    
    /**
     * Test method with example cases
     */
    public static void main(String[] args) {
        // Test case 1: Expected output = 6
        // Largest island has area 6
        int[][] grid1 = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        
        System.out.println("Test Case 1:");
        printGrid(grid1);
        System.out.println("Max area of island: " + maxAreaOfIsland(grid1));
        System.out.println();
        
        // Test case 2: Expected output = 0
        // No islands (all water)
        int[][] grid2 = {
            {0,0,0,0,0,0,0,0}
        };
        
        System.out.println("Test Case 2:");
        printGrid(grid2);
        System.out.println("Max area of island: " + maxAreaOfIsland(grid2));
        System.out.println();
        
        // Test case 3: Expected output = 4
        // Single island with area 4
        int[][] grid3 = {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,0,0}
        };
        
        System.out.println("Test Case 3:");
        printGrid(grid3);
        System.out.println("Max area of island: " + maxAreaOfIsland(grid3));
    }
    
    /**
     * Helper method to print the grid for visualization
     */
    private static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}