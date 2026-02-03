package fidelity_prep.day3;

import java.util.*;

/**
 * Problem 1: Subsets
 * LeetCode: 78. Subsets
 * 
 * Pattern: Backtracking
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Generate all possible subsets (power set)
 * 
 * 2. Backtracking approach:
 *    - At each step, choose to include or exclude element
 *    - Explore both choices
 *    - Add current path to result at each step (all sizes)
 * 
 * 3. Decision tree for [1,2]:
 *        []
 *       /  \
 *     [1]   []
 *     / \   / \
 *   [1,2] [1] [2] []
 * 
 * 4. Logic:
 *    - Add current path to result (every path is valid)
 *    - For each remaining element:
 *        - Choose: add to path
 *        - Explore: recurse with next index
 *        - Unchoose: remove from path
 */

public class Problem1_Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path)); // Add every path
        
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]); // Choose
            backtrack(nums, i + 1, path, result); // Explore
            path.remove(path.size() - 1); // Unchoose
        }
    }
}
