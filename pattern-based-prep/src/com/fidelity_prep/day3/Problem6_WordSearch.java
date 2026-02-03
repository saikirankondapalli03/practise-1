package fidelity_prep.day3;

/**
 * Problem 6: Word Search
 * LeetCode: 79. Word Search
 * 
 * Pattern: Backtracking on Matrix
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Find if word exists in 2D board (adjacent cells)
 * 
 * 2. Backtracking approach:
 *    - Start from each cell
 *    - Try all 4 directions
 *    - Mark visited, recurse, unmark
 * 
 * 3. Logic:
 *    - For each cell, if matches first char: start DFS
 *    - DFS: check if current char matches
 *    - If matches and is last char: found
 *    - Mark cell as visited
 *    - Try 4 directions
 *    - Unmark cell (backtrack)
 * 
 * 4. Pruning:
 *    - Out of bounds
 *    - Already visited
 *    - Character doesn't match
 */

public class Problem6_WordSearch {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (backtrack(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean backtrack(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;
        if (board[row][col] != word.charAt(index)) return false;
        
        char temp = board[row][col];
        board[row][col] = '#'; // Mark visited
        
        boolean found = backtrack(board, word, row + 1, col, index + 1) ||
                        backtrack(board, word, row - 1, col, index + 1) ||
                        backtrack(board, word, row, col + 1, index + 1) ||
                        backtrack(board, word, row, col - 1, index + 1);
        
        board[row][col] = temp; // Unmark
        return found;
    }
}
