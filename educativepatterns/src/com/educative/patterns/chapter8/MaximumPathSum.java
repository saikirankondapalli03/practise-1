package com.educative.patterns.chapter8;

class MaximumPathSum {

	private static int globalMaximumSum;

	public static int findMaximumPathSum(TreeNode root) {
		globalMaximumSum = Integer.MIN_VALUE;
		findMaximumPathSumRecursive(root);
		return globalMaximumSum;
	}

	private static int findMaximumPathSumRecursive(TreeNode currentNode) {
		if (currentNode == null )
			return 0;

		int maxPathSumFromLeft = findMaximumPathSumRecursive(currentNode.left);
		int maxPathSumFromRight = findMaximumPathSumRecursive(currentNode.right);

		// ignore paths with negative sums, since we need to find the maximum sum we
		// should
		// ignore any path which has an overall negative sum.
		maxPathSumFromLeft = Math.max(maxPathSumFromLeft, 0);
		maxPathSumFromRight = Math.max(maxPathSumFromRight, 0);

		// maximum path sum at the current node will be equal to the sum from the left
		// subtree +
		// the sum from right subtree + val of current node
		int localMaximumSum = maxPathSumFromLeft + maxPathSumFromRight + currentNode.val;

		// update the global maximum sum
		globalMaximumSum = Math.max(globalMaximumSum, localMaximumSum);
		System.out.println(globalMaximumSum);
		// maximum sum of any path from the current node will be equal to the maximum of
		// the sums from left or right subtrees plus the value of the current node
		int result=Math.max(maxPathSumFromLeft, maxPathSumFromRight) + currentNode.val;
		return result;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.right= new TreeNode(6);
		//System.out.println("Maximum Path Sum: " + MaximumPathSum.findMaximumPathSum(root));

		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.right.left.left = new TreeNode(7);
		root.right.left.right = new TreeNode(8);
		root.right.right.left = new TreeNode(9);
		System.out.println("Maximum Path Sum: " + MaximumPathSum.findMaximumPathSum(root));

		root = new TreeNode(-1);
		root.left = new TreeNode(-3);
		System.out.println("Maximum Path Sum: " + MaximumPathSum.findMaximumPathSum(root));
	}
}
