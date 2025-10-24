package com.patterns.backtracking;

/*
 * Flood Fill Algorithm using Backtracking/DFS
 * Given a 2D grid and a starting point, fill all connected cells of the same color with a new color
 */
public class FloodFill {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) return image;
        
        int originalColor = image[sr][sc];
        dfs(image, sr, sc, originalColor, newColor);
        return image;
    }
    
    private static void dfs(int[][] image, int row, int col, int originalColor, int newColor) {
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length ||
            image[row][col] != originalColor) {
            return;
        }
        
        image[row][col] = newColor;
        
        for (int[] dir : DIRECTIONS) {
            dfs(image, row + dir[0], col + dir[1], originalColor, newColor);
        }
    }
    
    public static void main(String[] args) {
        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
        int[][] result = floodFill(image, 1, 1, 2);
        
        for (int[] row : result) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }
    }
}