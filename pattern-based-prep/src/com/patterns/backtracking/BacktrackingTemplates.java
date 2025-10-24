package com.patterns.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * BACKTRACKING TEMPLATES - Different Approaches
 * 
 * Template 1: Mutable State (StringBuilder, List) - Need explicit backtrack
 * Template 2: Immutable State (String) - No explicit backtrack needed
 * Template 3: Index-based choices - Process array/string elements
 * Template 4: Generate choices - Create options dynamically
 */

public class BacktrackingTemplates {
    
    // ========== TEMPLATE 1: MUTABLE STATE ==========
    // Use when: Working with StringBuilder, modifying lists, etc.
    // Pattern: Choose → Explore → Unchoose
    
    public static List<String> phoneNumberMutable(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrackMutable(digits, 0, new StringBuilder(), result, mapping);
        return result;
    }
    
    private static void backtrackMutable(String digits, int index, StringBuilder current, 
                                        List<String> result, String[] mapping) {
        // Base case
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        // Try each choice
        String letters = mapping[digits.charAt(index) - '0'];
        for (char letter : letters.toCharArray()) {
            current.append(letter);                    // CHOOSE
            backtrackMutable(digits, index + 1, current, result, mapping);  // EXPLORE
            current.deleteCharAt(current.length() - 1); // UNCHOOSE
        }
    }
    
    // ========== TEMPLATE 2: IMMUTABLE STATE ==========
    // Use when: Working with strings, primitive values
    // Pattern: Choose → Explore (no unchoose needed)
    
    public static List<String> phoneNumberImmutable(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrackImmutable(digits, 0, "", result, mapping);
        return result;
    }
    
    private static void backtrackImmutable(String digits, int index, String current, 
                                          List<String> result, String[] mapping) {
        // Base case
        if (index == digits.length()) {
            result.add(current);
            return;
        }
        
        // Try each choice
        String letters = mapping[digits.charAt(index) - '0'];
        for (char letter : letters.toCharArray()) {
            // CHOOSE & EXPLORE (string is immutable, so no unchoose needed)
            backtrackImmutable(digits, index + 1, current + letter, result, mapping);
        }
    }
    
    // ========== TEMPLATE 3: INDEX-BASED CHOICES ==========
    // Use when: Choosing elements from array/string by index
    // Example: Permutations, Combinations
    
    public static List<List<Integer>> permutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermutations(nums, new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }
    
    private static void backtrackPermutations(int[] nums, List<Integer> current, 
                                            boolean[] used, List<List<Integer>> result) {
        // Base case
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Try each unused element
        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                current.add(nums[i]);     // CHOOSE
                used[i] = true;
                backtrackPermutations(nums, current, used, result);  // EXPLORE
                current.remove(current.size() - 1);  // UNCHOOSE
                used[i] = false;
            }
        }
    }
    
    // ========== TEMPLATE 4: GENERATE CHOICES DYNAMICALLY ==========
    // Use when: Choices depend on current state (N-Queens, Sudoku)
    // Example: N-Queens
    
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        // Initialize board
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrackQueens(board, 0, result);
        return result;
    }
    
    private static void backtrackQueens(char[][] board, int row, List<List<String>> result) {
        // Base case
        if (row == board.length) {
            result.add(boardToList(board));
            return;
        }
        
        // Try each column in current row
        for (int col = 0; col < board.length; col++) {
            if (isValidQueenPlacement(board, row, col)) {
                board[row][col] = 'Q';                    // CHOOSE
                backtrackQueens(board, row + 1, result); // EXPLORE
                board[row][col] = '.';                    // UNCHOOSE
            }
        }
    }
    
    private static boolean isValidQueenPlacement(char[][] board, int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        
        // Check diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        
        // Check anti-diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        
        return true;
    }
    
    private static List<String> boardToList(char[][] board) {
        List<String> result = new ArrayList<>();
        for (char[] row : board) {
            result.add(new String(row));
        }
        return result;
    }
    
    // ========== TEMPLATE SUMMARY ==========
    /*
     * TEMPLATE 1 (Mutable): Choose → Explore → Unchoose
     * TEMPLATE 2 (Immutable): Choose & Explore (no unchoose)
     * TEMPLATE 3 (Index-based): Loop through indices, track used
     * TEMPLATE 4 (Dynamic): Generate valid choices based on current state
     */
    
    public static void main(String[] args) {
        System.out.println("Template 1 (Mutable): " + phoneNumberMutable("23"));
        System.out.println("Template 2 (Immutable): " + phoneNumberImmutable("23"));
        System.out.println("Template 3 (Permutations): " + permutations(new int[]{1,2,3}));
        System.out.println("Template 4 (N-Queens): " + solveNQueens(4).size() + " solutions");
    }
}