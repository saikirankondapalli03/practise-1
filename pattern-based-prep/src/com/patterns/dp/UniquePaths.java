package com.patterns.dp;

/*
 * UNIQUE PATHS PROBLEM
 * 
 * Problem: Robot starts at top-left (0,0) of m×n grid
 *          Robot can only move RIGHT or DOWN
 *          Count total unique paths to reach bottom-right (m-1,n-1)
 * 
 * First Principle Thinking:
 * - At each cell, robot has 2 choices: go RIGHT or go DOWN
 * - Total paths = paths going RIGHT + paths going DOWN
 * - This creates a tree of decisions → perfect for recursion
 * - But many subproblems repeat → perfect for DP optimization
 */
public class UniquePaths {
    
    // ========== APPROACH 1: RECURSION (Top-Down) ==========
    /*
     * FIRST PRINCIPLE: Break problem into smaller subproblems
     * - To reach (m-1,n-1) from (0,0), we need to count all possible paths
     * - From any cell (row,col), we can go to (row,col+1) OR (row+1,col)
     * - Total paths = paths from going right + paths from going down
     */
    public static int uniquePathsRecursive(int m, int n) {
        // Start counting paths from top-left corner (0,0)
        return countPaths(0, 0, m, n);
    }
    
    private static int countPaths(int row, int col, int m, int n) {
        // BASE CASE 1: Reached destination successfully
        // If we're at bottom-right corner, we found 1 valid path
        if (row == m - 1 && col == n - 1) return 1;
        
        // BASE CASE 2: Went out of bounds
        // If we went beyond grid boundaries, this path is invalid
        if (row >= m || col >= n) return 0;
        
        // RECURSIVE CASE: Explore both possible moves
        // From current position, we can either:
        // 1. Move RIGHT (col + 1) - stay in same row
        // 2. Move DOWN (row + 1) - stay in same column
        // Total paths = sum of paths from both directions
        return countPaths(row, col + 1, m, n) +  // Move RIGHT
               countPaths(row + 1, col, m, n);      // Move DOWN
    }
    
    // ========== APPROACH 2: DYNAMIC PROGRAMMING (Bottom-Up) ==========
    /*
     * FIRST PRINCIPLE: Build solution from smaller to larger subproblems
     * - Instead of recursing from (0,0) to (m-1,n-1), build from destination back
     * - Key insight: To reach cell (i,j), robot must come from either:
     *   * Cell above: (i-1,j) by moving DOWN
     *   * Cell left: (i,j-1) by moving RIGHT
     * - So: paths[i][j] = paths[i-1][j] + paths[i][j-1]
     */
    public static int uniquePathsDP(int m, int n) {
        // Create DP table: dp[i][j] = number of paths to reach cell (i,j)
        int[][] dp = new int[m][n];
        
        // INITIALIZE BASE CASES:
        // First column (leftmost): Only 1 way to reach - keep going DOWN
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;  // Can only come from cell above
        }
        
        // First row (topmost): Only 1 way to reach - keep going RIGHT  
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;  // Can only come from cell to the left
        }
        
        // FILL DP TABLE using recurrence relation:
        // dp[i][j] = dp[i-1][j] + dp[i][j-1]
        // (paths from above + paths from left)
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // Current cell can be reached from:
                // 1. Cell above (i-1,j) - robot moved DOWN to reach here
                // 2. Cell left (i,j-1) - robot moved RIGHT to reach here
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        // Answer is number of paths to reach bottom-right corner
        return dp[m-1][n-1];
    }
    
    public static void main(String[] args) {
        // Test with 3x3 grid
        System.out.println("3x3 grid paths:");
        System.out.println("Recursive: " + uniquePathsRecursive(3, 3));  // Expected: 6
        System.out.println("DP: " + uniquePathsDP(3, 3));                // Expected: 6
        
        // Demonstrate DP table for 3x3 grid
        System.out.println("\nDP table visualization:");
        visualizeDP(3, 3);
    }
    
    // Helper method to visualize how DP table gets filled
    public static void visualizeDP(int m, int n) {
        int[][] dp = new int[m][n];
        
        // Initialize base cases
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        
        System.out.println("After initializing base cases:");
        printTable(dp);
        
        // Fill DP table step by step
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        System.out.println("\nFinal DP table:");
        printTable(dp);
        System.out.println("\nEach cell shows number of unique paths to reach that position");
    }
    
    private static void printTable(int[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
 * TIME & SPACE COMPLEXITY ANALYSIS:
 * 
 * RECURSION:
 * - Time: O(2^(m+n)) - Each cell has 2 choices, creating exponential tree
 * - Space: O(m+n) - Maximum recursion depth is m+n-2 moves
 * - Problem: Recalculates same subproblems multiple times
 * 
 * DYNAMIC PROGRAMMING:
 * - Time: O(m×n) - Fill each cell once
 * - Space: O(m×n) - Store DP table
 * - Advantage: Each subproblem calculated exactly once
 * 
 * INTERVIEW STRATEGY:
 * 1. "I'll start with recursion to show my understanding"
 * 2. "I notice overlapping subproblems - perfect for DP"
 * 3. "DP builds solution bottom-up using recurrence relation"
 * 4. "Each cell depends on cell above and cell to the left"
 */