package fidelity_prep.day2;

/**
 * Problem 6: Path Sum
 * LeetCode: 112. Path Sum
 * 
 * Pattern: DFS Tree Traversal
 * Difficulty: Easy
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Check if any root-to-leaf path sums to target
 * 
 * 2. Recursive approach:
 *    - Subtract current value from target
 *    - If leaf and remaining sum == 0: found path
 *    - Recurse on left and right with new target
 * 
 * 3. Base cases:
 *    - Null node: return false
 *    - Leaf node: return (target == node.val)
 */

public class Problem6_PathSum {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
}
