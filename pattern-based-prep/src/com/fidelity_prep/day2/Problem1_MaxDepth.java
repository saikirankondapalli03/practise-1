package fidelity_prep.day2;

/**
 * Problem 1: Maximum Depth of Binary Tree
 * LeetCode: 104. Maximum Depth of Binary Tree
 * 
 * Pattern: DFS (Depth-First Search)
 * Difficulty: Easy
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a DFS problem because:
 * - We need to explore all paths to leaves
 * - We're calculating a tree property (depth)
 * - Recursive structure fits naturally
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find the maximum depth (longest path from root to leaf)
 * 
 * 2. Recursive thinking:
 *    - What's the depth of a node?
 *    - Depth of current node = 1 + max(depth of left subtree, depth of right subtree)
 * 
 * 3. Base case:
 *    - If node is null, depth is 0
 *    - If node is leaf, depth is 1
 * 
 * 4. Example tree:
 *       3
 *      / \
 *     9   20
 *        /  \
 *       15   7
 *    - Root (3): 1 + max(depth(9), depth(20))
 *    - Node (9): 1 + max(0, 0) = 1
 *    - Node (20): 1 + max(depth(15), depth(7))
 *    - Node (15): 1 + max(0, 0) = 1
 *    - Node (7): 1 + max(0, 0) = 1
 *    - Node (20): 1 + max(1, 1) = 2
 *    - Root (3): 1 + max(1, 2) = 3
 * 
 * 5. Logic:
 *    - If root is null, return 0
 *    - Recursively get depth of left subtree
 *    - Recursively get depth of right subtree
 *    - Return 1 + max(leftDepth, rightDepth)
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem1_MaxDepth {
    
    // TODO: Implement maxDepth method
    public int maxDepth(TreeNode root) {
        // YOUR CODE HERE
        // Think about:
        // - What's the base case?
        // - How to combine results from left and right?
        
        return 0;
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem1_MaxDepth solution = new Problem1_MaxDepth();
        
        // Test case 1: [3,9,20,null,null,15,7]
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println("Test 1: " + solution.maxDepth(root1)); // Expected: 3
        
        // Test case 2: [1,null,2]
        TreeNode root2 = new TreeNode(1);
        root2.right = new TreeNode(2);
        System.out.println("Test 2: " + solution.maxDepth(root2)); // Expected: 2
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursively get depth of left and right subtrees
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // Depth of current node = 1 + max of children's depths
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    Time Complexity: O(n) - visit each node once
    Space Complexity: O(h) where h is height (recursion stack)
    */
}
