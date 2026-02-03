package fidelity_prep.day2;

import java.util.*;

/**
 * Problem 2: Binary Tree Level Order Traversal
 * LeetCode: 102. Binary Tree Level Order Traversal
 * 
 * Pattern: BFS (Breadth-First Search)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a BFS problem because:
 * - We need to process nodes level by level
 * - We need a queue to maintain order
 * - We process all nodes at current level before moving to next
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to return nodes grouped by level
 * 
 * 2. BFS approach:
 *    - Use a queue to store nodes
 *    - Process nodes level by level
 *    - For each level, process all nodes in queue
 *    - Add children to queue for next level
 * 
 * 3. Key insight: How to know when a level ends?
 *    - Before processing a level, get queue size
 *    - Process exactly that many nodes
 *    - Remaining nodes in queue are for next level
 * 
 * 4. Example:
 *       3
 *      / \
 *     9   20
 *        /  \
 *       15   7
 *    
 *    Level 0: [3]
 *    Level 1: [9, 20]
 *    Level 2: [15, 7]
 * 
 * 5. Algorithm:
 *    - Initialize queue with root
 *    - While queue not empty:
 *        - Get current level size
 *        - Create list for current level
 *        - Process nodes for current level size
 *        - Add node value to level list
 *        - Add children to queue
 *        - Add level list to result
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem2_LevelOrder {
    
    // TODO: Implement levelOrder method
    // Return list of lists, each inner list is a level
    public List<List<Integer>> levelOrder(TreeNode root) {
        // YOUR CODE HERE
        // Think about:
        // - What data structure to use for BFS?
        // - How to separate levels?
        // - When to add children to queue?
        
        return new ArrayList<>();
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem2_LevelOrder solution = new Problem2_LevelOrder();
        
        // Test case 1
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println("Test 1: " + solution.levelOrder(root1));
        // Expected: [[3],[9,20],[15,7]]
        
        // Test case 2
        TreeNode root2 = new TreeNode(1);
        System.out.println("Test 2: " + solution.levelOrder(root2));
        // Expected: [[1]]
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                // Add children for next level
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    Time Complexity: O(n) - visit each node once
    Space Complexity: O(n) - queue can hold at most n/2 nodes (last level)
    */
}
