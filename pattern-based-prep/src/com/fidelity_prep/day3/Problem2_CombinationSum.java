package fidelity_prep.day3;

import java.util.*;

/**
 * Problem 2: Combination Sum
 * LeetCode: 39. Combination Sum
 * 
 * Pattern: Backtracking with constraints
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Find all combinations that sum to target
 *    - Can reuse same number
 *    - All numbers positive
 * 
 * 2. Backtracking approach:
 *    - Try each number, add to path
 *    - Recurse with same index (can reuse)
 *    - If sum == target: add to result
 *    - If sum > target: backtrack (prune)
 * 
 * 3. Example: [2,3,6,7], target=7
 *    - Try 2: path=[2], sum=2, recurse
 *      - Try 2: path=[2,2], sum=4, recurse
 *        - Try 2: path=[2,2,2], sum=6, recurse
 *          - Try 2: path=[2,2,2,2], sum=8 > 7, backtrack
 *        - Try 3: path=[2,2,3], sum=7, found!
 */

public class Problem2_CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int target, int start, 
                          List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (target < 0) return; // Prune
        
        for (int i = start; i < candidates.length; i++) {
            path.add(candidates[i]);
            backtrack(candidates, target - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}
