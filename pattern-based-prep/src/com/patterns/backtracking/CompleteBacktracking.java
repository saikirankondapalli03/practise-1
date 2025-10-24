package com.patterns.backtracking;

import java.util.*;

/*
 * COMPLETE BACKTRACKING GUIDE
 * 
 * Backtracking is used when we need to explore ALL possible solutions
 * and find the ones that satisfy certain conditions.
 * 
 * Core Template:
 * 1. Choose - Make a choice and add to current path
 * 2. Explore - Recursively explore with this choice
 * 3. Unchoose - Remove the choice (backtrack) to try other options
 * 
 * This file contains:
 * - 5 Core Problems (Permutations, Combinations, Subsets, Combination Sum I & II)
 * - 4 Template Variations (Mutable, Immutable, Index-based, Dynamic)
 * - Complete examples with complexity analysis
 */
public class CompleteBacktracking {
    
    // ========== CORE PROBLEM 1: PERMUTATIONS ==========
    /*
     * Problem: Generate all permutations of an array
     * Example: [1,2,3] → [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
     * 
     * Time: O(n! * n), Space: O(n)
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        
        permuteHelper(nums, path, used, result);
        return result;
    }
    
    private static void permuteHelper(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            
            path.add(nums[i]);      // CHOOSE
            used[i] = true;
            permuteHelper(nums, path, used, result);  // EXPLORE
            path.remove(path.size() - 1);             // UNCHOOSE
            used[i] = false;
        }
    }
    
    // ========== CORE PROBLEM 2: COMBINATIONS ==========
    /*
     * Problem: Generate all combinations of k numbers from 1 to n
     * Example: n=4, k=2 → [[1,2], [1,3], [1,4], [2,3], [2,4], [3,4]]
     * 
     * Time: O(C(n,k) * k), Space: O(k)
     */
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        combineHelper(n, k, 1, path, result);
        return result;
    }
    
    private static void combineHelper(int n, int k, int start, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i <= n; i++) {
            path.add(i);                              // CHOOSE
            combineHelper(n, k, i + 1, path, result); // EXPLORE
            path.remove(path.size() - 1);             // UNCHOOSE
        }
    }
    
    // ========== CORE PROBLEM 3: SUBSETS ==========
    /*
     * Problem: Generate all subsets (power set) of an array
     * Example: [1,2,3] → [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
     * 
     * Time: O(2^n * n), Space: O(n)
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        subsetsHelper(nums, 0, path, result);
        return result;
    }
    
    private static void subsetsHelper(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path)); // Add current subset
        
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);                        // CHOOSE
            subsetsHelper(nums, i + 1, path, result); // EXPLORE
            path.remove(path.size() - 1);             // UNCHOOSE
        }
    }
    
    // ========== CORE PROBLEM 4: COMBINATION SUM ==========
    /*
     * Problem: Find all combinations that sum to target (elements can be reused)
     * Example: [2,3,6,7], target=7 → [[2,2,3], [7]]
     * 
     * Time: O(2^target), Space: O(target)
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        Arrays.sort(candidates);
        combinationSumHelper(candidates, target, 0, path, result);
        return result;
    }
    
    private static void combinationSumHelper(int[] candidates, int target, int start, 
                                           List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) break; // Pruning
            
            path.add(candidates[i]);                                      // CHOOSE
            combinationSumHelper(candidates, target - candidates[i], i, path, result); // EXPLORE (same i = reuse)
            path.remove(path.size() - 1);                                 // UNCHOOSE
        }
    }
    
    // ========== CORE PROBLEM 5: COMBINATION SUM II ==========
    /*
     * Problem: Find all combinations that sum to target (no reuse, handle duplicates)
     * Example: [10,1,2,7,6,1,5], target=8 → [[1,1,6], [1,2,5], [1,7], [2,6]]
     * 
     * Time: O(2^n), Space: O(target)
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        Arrays.sort(candidates);
        combinationSum2Helper(candidates, target, 0, path, result);
        return result;
    }
    
    private static void combinationSum2Helper(int[] candidates, int target, int start,
                                            List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i-1]) continue; // Skip duplicates
            if (candidates[i] > target) break; // Pruning
            
            path.add(candidates[i]);                                          // CHOOSE
            combinationSum2Helper(candidates, target - candidates[i], i + 1, path, result); // EXPLORE (i+1 = no reuse)
            path.remove(path.size() - 1);                                     // UNCHOOSE
        }
    }
    
    // ========== TEMPLATE 1: MUTABLE STATE ==========
    /*
     * Use when: Working with StringBuilder, modifying lists, etc.
     * Pattern: Choose → Explore → Unchoose
     */
    public static List<String> phoneNumberMutable(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrackMutable(digits, 0, new StringBuilder(), result, mapping);
        return result;
    }
    
    private static void backtrackMutable(String digits, int index, StringBuilder current, 
                                        List<String> result, String[] mapping) {
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }
        
        String letters = mapping[digits.charAt(index) - '0'];
        for (char letter : letters.toCharArray()) {
            current.append(letter);                    // CHOOSE
            backtrackMutable(digits, index + 1, current, result, mapping);  // EXPLORE
            current.deleteCharAt(current.length() - 1); // UNCHOOSE
        }
    }
    
    // ========== TEMPLATE 2: IMMUTABLE STATE ==========
    /*
     * Use when: Working with strings, primitive values
     * Pattern: Choose → Explore (no unchoose needed)
     */
    public static List<String> phoneNumberImmutable(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrackImmutable(digits, 0, "", result, mapping);
        return result;
    }
    
    private static void backtrackImmutable(String digits, int index, String current, 
                                          List<String> result, String[] mapping) {
        if (index == digits.length()) {
            result.add(current);
            return;
        }
        
        String letters = mapping[digits.charAt(index) - '0'];
        for (char letter : letters.toCharArray()) {
            // CHOOSE & EXPLORE (string is immutable, so no unchoose needed)
            backtrackImmutable(digits, index + 1, current + letter, result, mapping);
        }
    }
    
    // ========== TEMPLATE 3: N-QUEENS (DYNAMIC CHOICES) ==========
    /*
     * Use when: Choices depend on current state
     * Example: N-Queens, Sudoku
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.');
        }
        
        backtrackQueens(board, 0, result);
        return result;
    }
    
    private static void backtrackQueens(char[][] board, int row, List<List<String>> result) {
        if (row == board.length) {
            result.add(boardToList(board));
            return;
        }
        
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
        
        // Check diagonals
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        
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
    
    // ========== DEMONSTRATION AND TESTING ==========
    
    public static void main(String[] args) {
        System.out.println("=== COMPLETE BACKTRACKING DEMONSTRATION ===\n");
        
        // Core Problems
        System.out.println("1. PERMUTATIONS [1,2,3]: " + permute(new int[]{1,2,3}));
        System.out.println("2. COMBINATIONS C(4,2): " + combine(4, 2));
        System.out.println("3. SUBSETS [1,2,3]: " + subsets(new int[]{1,2,3}));
        System.out.println("4. COMBINATION SUM [2,3,6,7] target=7: " + combinationSum(new int[]{2,3,6,7}, 7));
        System.out.println("5. COMBINATION SUM II [10,1,2,7,6,1,5] target=8: " + combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
        
        // Template Examples
        System.out.println("\n=== TEMPLATE EXAMPLES ===");
        System.out.println("Mutable Template (Phone): " + phoneNumberMutable("23"));
        System.out.println("Immutable Template (Phone): " + phoneNumberImmutable("23"));
        System.out.println("Dynamic Template (N-Queens 4x4): " + solveNQueens(4).size() + " solutions");
        
        System.out.println("\n=== BACKTRACKING PATTERNS SUMMARY ===");
        System.out.println("• Permutations: Order matters, use boolean[] for tracking");
        System.out.println("• Combinations: Order doesn't matter, use start index");
        System.out.println("• Subsets: Add at every call, not just base case");
        System.out.println("• Combination Sum: Allow reuse (same index in recursion)");
        System.out.println("• Combination Sum II: No reuse, skip duplicates");
    }
}

/*
 * COMPLETE BACKTRACKING REFERENCE
 * 
 * CORE TEMPLATE:
 * void backtrack(state, choices) {
 *     if (base_case) {
 *         add_to_result(state);
 *         return;
 *     }
 *     
 *     for (choice in choices) {
 *         if (valid_choice) {
 *             make_choice(state, choice);     // CHOOSE
 *             backtrack(new_state, new_choices); // EXPLORE
 *             undo_choice(state, choice);     // UNCHOOSE
 *         }
 *     }
 * }
 * 
 * PROBLEM PATTERNS:
 * 1. Permutations: All arrangements, order matters → use boolean[] used
 * 2. Combinations: Choose k from n, order doesn't matter → use start index
 * 3. Subsets: All possible subsets → add at every recursive call
 * 4. Combination Sum: Target sum with reuse → same start index in recursion
 * 5. Combination Sum II: Target sum without reuse → skip duplicates
 * 
 * TEMPLATE VARIATIONS:
 * 1. Mutable State: StringBuilder, List → explicit unchoose needed
 * 2. Immutable State: String, primitives → no unchoose needed
 * 3. Index-based: Choose from array indices → track used elements
 * 4. Dynamic Choices: Generate valid moves → validate before choosing
 * 
 * COMPLEXITY ANALYSIS:
 * - Permutations: O(n!) - n! arrangements
 * - Combinations: O(C(n,k)) - binomial coefficient
 * - Subsets: O(2^n) - each element in/out
 * - Combination Sum: O(2^target) - explore all possibilities
 * - N-Queens: O(n!) - place queens row by row
 * 
 * INTERVIEW TIPS:
 * - "I'll use backtracking to explore all possibilities"
 * - "The template is: choose, explore, unchoose"
 * - "I need to handle duplicates by sorting and skipping"
 * - "Time complexity depends on number of valid solutions"
 */