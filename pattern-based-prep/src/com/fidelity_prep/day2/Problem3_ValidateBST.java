package fidelity_prep.day2;

/**
 * Problem 3: Validate Binary Search Tree
 * LeetCode: 98. Validate Binary Search Tree
 * 
 * Pattern: DFS with constraints
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * 
 * 1. BST property: left < node < right (for all nodes)
 * 
 * 2. Key insight: Not just check immediate children!
 *    - All nodes in left subtree must be < root
 *    - All nodes in right subtree must be > root
 * 
 * 3. Approach: Pass min/max bounds down the tree
 *    - For left child: must be < parent, and > min
 *    - For right child: must be > parent, and < max
 * 
 * 4. Logic:
 *    - isValid(node, min, max)
 *    - Base: null node is valid
 *    - Check: node.val must be > min and < max
 *    - Recurse: left with (min, node.val), right with (node.val, max)
 */

public class Problem3_ValidateBST {
    public boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean isValid(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValid(node.left, min, node.val) && 
               isValid(node.right, node.val, max);
    }
}
