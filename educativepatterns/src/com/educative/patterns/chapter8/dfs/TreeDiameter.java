package com.educative.patterns.chapter8.dfs;

class TreeDiameter {
	private static int treeDiameter = 0;
	public static int findDiameter(TreeNode root) {
		calculateHeight(root);
		return treeDiameter;
	}

	public static int findHeight(TreeNode root) {
		// Base case, leaf nodes have 0 height
		if (root == null)
			return -1;
		else {
			return 1 + Math.max(findHeight(root.left), findHeight(root.right));
			// Find Height of left subtree right subtree
			// Return greater height value of left or right subtree (plus 1)
		}
	}

	private static int calculateHeight(TreeNode currentNode) {
		if (currentNode == null)
			return 0;
		int leftTreeHeight = calculateHeight(currentNode.left);
		int rightTreeHeight = calculateHeight(currentNode.right);

		// diameter at the current node will be equal to the height of left subtree +
		// the height of right sub-trees + '1' for the current node
		int diameter = leftTreeHeight + rightTreeHeight + 1;

		// update the global tree diameter
		treeDiameter = Math.max(treeDiameter, diameter);

		// height of the current node will be equal to the maximum of the hights of
		// left or right subtrees plus '1' for the current node
		return Math.max(leftTreeHeight, rightTreeHeight) + 1;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(6);
		System.out.println("Tree Diameter: " + TreeDiameter.findDiameter(root));
		root.left.left = null;
		root.right.left.left = new TreeNode(7);
		root.right.left.right = new TreeNode(8);
		root.right.right.left = new TreeNode(9);
		root.right.left.right.left = new TreeNode(10);
		root.right.right.left.left = new TreeNode(11);
		System.out.println("Tree Diameter: " + TreeDiameter.findDiameter(root));
	}
}
