package com.patterns.dfs;

/*
 * Rainwater Trapping using DFS Template
 * 
 * Problem: Given a 2D elevation map, calculate how much water can be trapped after raining.
 * 
 * Key Insights:
 * - Water flows to adjacent cells (4-directional) if they have lower/equal elevation
 * - Water can only be trapped if it cannot flow to the boundary
 * - We use "reverse thinking": start from boundaries and propagate water levels inward
 * 
 * DFS Template Application:
 * - State: Current cell position and water level
 * - Goal: Determine minimum water level at each cell
 * - Transitions: Move to 4 adjacent cells
 * - Base cases: Out of bounds, already processed with better level
 */
public class RainwaterTrapping {
    // DFS Template: Define possible moves (4-directional)
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public static int trapRainWater(int[][] heights) {
        // DFS Template Step 1: Input validation
        if (heights == null || heights.length == 0) return 0;
        
        int rows = heights.length, cols = heights[0].length;
        
        // DFS Template Step 2: Initialize state tracking
        // waterLevel[i][j] = minimum water level that can exist at cell (i,j)
        int[][] waterLevel = new int[rows][cols];
        
        // Initialize to MAX_VALUE (assume infinite water initially)
        // This represents "unprocessed" state in our DFS
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                waterLevel[i][j] = Integer.MAX_VALUE;
            }
        }
        
        // DFS Template Step 3: Define starting points
        // Key insight: Water at boundaries always drains out
        // So boundary cells have water level = their ground height
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    // Start DFS from each boundary cell
                    dfs(heights, waterLevel, i, j, heights[i][j]);
                }
            }
        }
        
        // DFS Template Step 4: Process results
        // Calculate trapped water = water level - ground height
        int totalWater = 0;
        System.out.println("\nFinal water levels:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(waterLevel[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nTrapped water per cell:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int trapped = Math.max(0, waterLevel[i][j] - heights[i][j]);
                System.out.print(trapped + " ");
                totalWater += trapped;
            }
            System.out.println();
        }
        
        return totalWater;
    }
    
    // DFS Template Implementation
    private static void dfs(int[][] heights, int[][] waterLevel, int row, int col, int level) {
        // DFS Base Cases:
        // 1. Out of bounds
        // 2. Already processed with better (lower) water level
        if (row < 0 || row >= heights.length || col < 0 || col >= heights[0].length ||
            waterLevel[row][col] <= level) {
            return;
        }
        
        // DFS State Update:
        // Water level at current cell = max(ground height, incoming water level)
        // This ensures water can't be below ground level
        waterLevel[row][col] = Math.max(heights[row][col], level);
        
        // DFS Recursive Exploration:
        // Propagate water level to all 4 adjacent cells
        // Water flows from current cell to neighbors
        for (int[] dir : DIRECTIONS) {
            dfs(heights, waterLevel, row + dir[0], col + dir[1], waterLevel[row][col]);
        }
    }
    
    public static void main(String[] args) {
        // Test Case 1: Perfect bowl shape
        // Center cell (height 1) surrounded by barriers (height 2)
        // Expected: 1 unit of water trapped at center
        int[][] heights1 = {
            {3, 3, 3, 3, 3},  // Outer boundary
            {3, 2, 2, 2, 3},  // Inner barrier
            {3, 2, 1, 2, 3},  // Center valley (height 1)
            {3, 2, 2, 2, 3},  // Inner barrier
            {3, 3, 3, 3, 3}   // Outer boundary
        };
        
        // Test Case 2: Complex landscape
        // Multiple valleys and peaks
        int[][] heights2 = {
            {1, 4, 3, 1, 3, 2},
            {3, 2, 1, 3, 2, 4},
            {2, 3, 3, 2, 3, 1}
        };
        
        System.out.println("Water trapped in grid 1: " + trapRainWater(heights1));
        System.out.println("Water trapped in grid 2: " + trapRainWater(heights2));
    }
}