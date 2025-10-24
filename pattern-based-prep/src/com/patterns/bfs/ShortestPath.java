package com.patterns.bfs;

import java.util.*;

/*
 * BFS SHORTEST PATH PROBLEM
 * 
 * Problem: Given a 2D grid where 1 = walkable, 0 = obstacle
 *          Find shortest path from start to end position
 * 
 * Why BFS?
 * - BFS explores level by level (distance 0, then 1, then 2...)
 * - First time we reach destination = shortest path guaranteed
 * - DFS would explore deep paths first, not necessarily shortest
 * 
 * Key Insight: BFS guarantees shortest path in unweighted graphs
 */
public class ShortestPath {
    
    // BFS Template for shortest path in 2D grid
    public static int shortestPath(int[][] grid, int[] start, int[] end) {
        // Edge case: invalid input
        if (grid == null || grid.length == 0 || start == null || end == null) {
            return -1;
        }
        
        int rows = grid.length, cols = grid[0].length;
        
        // Check if start/end are valid and walkable
        if (start[0] < 0 || start[0] >= rows || start[1] < 0 || start[1] >= cols ||
            end[0] < 0 || end[0] >= rows || end[1] < 0 || end[1] >= cols ||
            grid[start[0]][start[1]] == 0 || grid[end[0]][end[1]] == 0) {
            return -1;
        }
        
        // BFS Setup: Queue and visited tracking
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        
        // Start BFS: Add starting position with distance 0
        queue.offer(new int[]{start[0], start[1], 0}); // {row, col, distance}
        visited[start[0]][start[1]] = true;
        
        // 4-directional movement: right, left, down, up
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        // BFS Main Loop
        while (!queue.isEmpty()) {
            // Process current cell
            int[] current = queue.poll();
            int row = current[0], col = current[1], distance = current[2];
            
            // Check if we reached the destination
            if (row == end[0] && col == end[1]) {
                return distance; // Found shortest path!
            }
            
            // Explore all 4 neighbors
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check if neighbor is valid and unvisited
                if (newRow >= 0 && newRow < rows && 
                    newCol >= 0 && newCol < cols &&
                    !visited[newRow][newCol] && 
                    grid[newRow][newCol] == 1) {
                    
                    // Add neighbor to queue with incremented distance
                    queue.offer(new int[]{newRow, newCol, distance + 1});
                    visited[newRow][newCol] = true; // Mark as visited immediately
                }
            }
        }
        
        // No path found
        return -1;
    }
    
    // Helper method to visualize the path
    public static void demonstrateBFS() {
        // Example grid: 1 = walkable, 0 = obstacle
        int[][] grid = {
            {1, 1, 0, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 1, 1, 0, 1},
            {0, 0, 1, 1, 1},
            {1, 1, 1, 0, 1}
        };
        
        int[] start = {0, 0}; // Top-left
        int[] end = {4, 4};   // Bottom-right
        
        System.out.println("Grid (1=walkable, 0=obstacle):");
        printGrid(grid);
        
        System.out.println("\nStart: (" + start[0] + "," + start[1] + ")");
        System.out.println("End: (" + end[0] + "," + end[1] + ")");
        
        int result = shortestPath(grid, start, end);
        System.out.println("\nShortest path distance: " + result);
        
        if (result != -1) {
            System.out.println("\nBFS explores level by level:");
            System.out.println("Distance 0: (0,0) - starting point");
            System.out.println("Distance 1: (0,1), (1,0) - neighbors of start");
            System.out.println("Distance 2: (0,2), (1,1), (2,0) - next level");
            System.out.println("... and so on until destination is reached");
        }
    }
    
    // Detailed BFS with step-by-step visualization
    public static int shortestPathWithVisualization(int[][] grid, int[] start, int[] end) {
        if (grid == null || grid.length == 0) return -1;
        
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        
        queue.offer(new int[]{start[0], start[1], 0});
        visited[start[0]][start[1]] = true;
        
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int level = 0;
        
        System.out.println("\nBFS Step-by-step:");
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.println("Level " + level + ":");
            
            // Process all nodes at current level
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0], col = current[1], distance = current[2];
                
                System.out.println("  Processing (" + row + "," + col + ") at distance " + distance);
                
                if (row == end[0] && col == end[1]) {
                    System.out.println("  *** DESTINATION REACHED! ***");
                    return distance;
                }
                
                // Add neighbors for next level
                for (int[] dir : directions) {
                    int newRow = row + dir[0], newCol = col + dir[1];
                    
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        !visited[newRow][newCol] && grid[newRow][newCol] == 1) {
                        
                        queue.offer(new int[]{newRow, newCol, distance + 1});
                        visited[newRow][newCol] = true;
                        System.out.println("    Added neighbor (" + newRow + "," + newCol + ") to queue");
                    }
                }
            }
            level++;
        }
        
        return -1;
    }
    
    private static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    // DFS APPROACH - DOES NOT GUARANTEE SHORTEST PATH!
    private static int minDistance;
    
    public static int shortestPathDFS(int[][] grid, int[] start, int[] end) {
        if (grid == null || grid.length == 0) return -1;
        
        minDistance = Integer.MAX_VALUE;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        
        dfs(grid, visited, start[0], start[1], end[0], end[1], 0);
        
        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }
    
    private static void dfs(int[][] grid, boolean[][] visited, int row, int col, 
                           int endRow, int endCol, int distance) {
        // Base cases
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
            visited[row][col] || grid[row][col] == 0) {
            return;
        }
        
        // Found destination
        if (row == endRow && col == endCol) {
            minDistance = Math.min(minDistance, distance);
            return;
        }
        
        // Pruning: if current distance already >= best found, stop exploring
        if (distance >= minDistance) return;
        
        visited[row][col] = true;
        
        // Explore all 4 directions
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] dir : directions) {
            dfs(grid, visited, row + dir[0], col + dir[1], endRow, endCol, distance + 1);
        }
        
        visited[row][col] = false; // Backtrack
    }
    
    public static void main(String[] args) {
        System.out.println("=== BFS vs DFS COMPARISON ===");
        
        int[][] grid = {
            {1, 1, 0},
            {1, 0, 1},
            {1, 1, 1}
        };
        
        System.out.println("Grid:");
        printGrid(grid);
        System.out.println("Start: (0,0), End: (2,2)\n");
        
        // BFS Result
        int bfsResult = shortestPath(grid, new int[]{0, 0}, new int[]{2, 2});
        System.out.println("BFS Result: " + bfsResult + " (GUARANTEED shortest)");
        
        // DFS Result
        int dfsResult = shortestPathDFS(grid, new int[]{0, 0}, new int[]{2, 2});
        System.out.println("DFS Result: " + dfsResult + " (may not be shortest)\n");
        
        System.out.println("=== WHY BFS IS BETTER FOR SHORTEST PATH ===");
        System.out.println("BFS: Explores level by level → first path found = shortest");
        System.out.println("DFS: Explores deep first → may find long path before short one");
        System.out.println("DFS: Needs to explore ALL paths to guarantee shortest (exponential time!)");
        
        // Detailed demonstration
        System.out.println("\n=== DETAILED BFS TRACE ===");
        int result = shortestPathWithVisualization(grid, new int[]{0, 0}, new int[]{2, 2});
        System.out.println("\nFinal BFS result: " + result);
    }
}

/*
 * BFS vs DFS FOR SHORTEST PATH:
 * 
 * CAN DFS SOLVE SHORTEST PATH? 
 * - YES, but inefficiently!
 * - DFS must explore ALL possible paths to guarantee shortest
 * - Time complexity becomes exponential: O(4^(m*n))
 * - BFS finds shortest path in O(m*n) time
 * 
 * EXAMPLE: Why DFS is bad for shortest path
 * Grid:    DFS might explore:
 * 1 1 1    Path 1: (0,0)→(0,1)→(0,2)→(1,2)→(2,2) = 4 steps
 * 1 0 1    Path 2: (0,0)→(1,0)→(2,0)→(2,1)→(2,2) = 4 steps  
 * 1 1 1    
 *          DFS explores deep first, might find longer paths first!
 *          BFS explores level by level, finds shortest immediately!
 * 
 * WHEN TO USE EACH:
 * - BFS: Shortest path, level-order traversal, minimum steps
 * - DFS: Path existence, connected components, topological sort
 * 
 * BFS ALGORITHM EXPLANATION:
 * 
 * 1. INITIALIZATION:
 *    - Create queue to store {row, col, distance}
 *    - Create visited array to avoid cycles
 *    - Add starting position to queue with distance 0
 * 
 * 2. BFS MAIN LOOP:
 *    - While queue is not empty:
 *      a) Poll current position from queue
 *      b) Check if it's the destination → return distance
 *      c) Explore all 4 neighbors
 *      d) Add valid unvisited neighbors to queue with distance+1
 * 
 * 3. WHY BFS GUARANTEES SHORTEST PATH:
 *    - BFS explores nodes level by level
 *    - Level 0: starting node
 *    - Level 1: all nodes 1 step away
 *    - Level 2: all nodes 2 steps away
 *    - First time we reach destination = minimum steps
 * 
 * EXAMPLE TRACE:
 * Grid:     Start: (0,0), End: (2,2)
 * 1 1 0
 * 1 0 1     Level 0: (0,0)
 * 1 1 1     Level 1: (0,1), (1,0)
 *           Level 2: (1,0) → (2,0)
 *           Level 3: (2,0) → (2,1)
 *           Level 4: (2,1) → (2,2) ✓ Answer: 4
 * 
 * TIME COMPLEXITY: O(m*n) - visit each cell at most once
 * SPACE COMPLEXITY: O(m*n) - queue and visited array
 * 
 * INTERVIEW TIPS:
 * - "I'll use BFS because it guarantees shortest path in unweighted graphs"
 * - "BFS explores level by level, so first time we reach destination is optimal"
 * - "I'll use a queue to track positions and distances"
 * - "I'll mark cells as visited immediately when adding to queue to avoid duplicates"
 */