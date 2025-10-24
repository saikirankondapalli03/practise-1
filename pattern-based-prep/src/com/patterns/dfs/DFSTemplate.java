package com.patterns.dfs;

/*
 * DFS Template for various problems
 * Common patterns: Grid traversal, Tree traversal, Graph traversal, Backtracking
 */
public class DFSTemplate {
    
    // Grid DFS Template
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public static void gridDFS(int[][] grid, boolean[][] visited, int row, int col) {
        // Base cases
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
            visited[row][col] || grid[row][col] == 0) {
            return;
        }
        
        // Mark as visited
        visited[row][col] = true;
        
        // Process current cell
        // ... your logic here ...
        
        // Explore all 4 directions
        for (int[] dir : DIRECTIONS) {
            gridDFS(grid, visited, row + dir[0], col + dir[1]);
        }
    }
    
    // Tree DFS Template
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
    
    public static void treeDFS(TreeNode root) {
        if (root == null) return;
        
        // Pre-order processing
        // ... process root ...
        
        treeDFS(root.left);   // Left subtree
        treeDFS(root.right);  // Right subtree
        
        // Post-order processing
        // ... process root after children ...
    }
    
    // Graph DFS Template
    public static void graphDFS(int[][] graph, boolean[] visited, int node) {
        visited[node] = true;
        
        // Process current node
        // ... your logic here ...
        
        // Visit all adjacent nodes
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                graphDFS(graph, visited, neighbor);
            }
        }
    }
    
    // Backtracking DFS Template
    public static void backtrackDFS(int[] nums, boolean[] used, java.util.List<Integer> current, 
                                   java.util.List<java.util.List<Integer>> result) {
        // Base case - found valid solution
        if (current.size() == nums.length) {
            result.add(new java.util.ArrayList<>(current));
            return;
        }
        
        // Try all possibilities
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            
            // Choose
            current.add(nums[i]);
            used[i] = true;
            
            // Explore
            backtrackDFS(nums, used, current, result);
            
            // Unchoose (backtrack)
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("DFS Template - Use these patterns for various DFS problems");
    }
}