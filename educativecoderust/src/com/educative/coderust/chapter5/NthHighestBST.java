package com.educative.coderust.chapter5;

class NthHighestBST {
	public static int currentCount = 0;

	public static BinaryTreeNode findNthHighestInBST(BinaryTreeNode node, int n) {

		if (node == null) {
			return null;
		}

		BinaryTreeNode result = findNthHighestInBST(node.right, n);

		if (result != null) {
			return result;
		}

		currentCount++;
		if (n == currentCount) {
			return node;
		}

		result = findNthHighestInBST(node.left, n);

		if (result != null) {
			return result;
		}

		return null;
	}

	public static BinaryTreeNode findNthHighestInBSTDifferentStyle(BinaryTreeNode node, int n) {

		if (node == null) {
			return null;
		}

		int left_count = 0;

		if (node.left != null) {
			left_count = node.left.count;
		}

		int k = node.count - left_count;

		if (k == n) {
			return node;
		} else if (k > n) {
			return findNthHighestInBSTDifferentStyle(node.right, n);
		} else {
			return findNthHighestInBSTDifferentStyle(node.left, n - k);
		}
	}

	public static void main(String[] argv) {
		BinaryTreeNode root = new BinaryTreeNode(100);
		root.count = 7;

		BinaryTree.insert(root, 50);
		BinaryTreeNode node12 = BinaryTree.findInBst(root, 50);
		node12.count = 3;

		BinaryTree.insert(root, 200);
		BinaryTreeNode node3 = BinaryTree.findInBst(root, 200);
		node3.count = 3;

		BinaryTree.insert(root, 25);
		BinaryTreeNode node4 = BinaryTree.findInBst(root, 25);
		node4.count = 1;

		BinaryTree.insert(root, 75);
		BinaryTreeNode node8 = BinaryTree.findInBst(root, 75);
		node8.count = 1;

		BinaryTree.insert(root, 125);
		BinaryTreeNode node18 = BinaryTree.findInBst(root, 125);
		node18.count = 1;

		BinaryTree.insert(root, 350);
		BinaryTreeNode node16 = BinaryTree.findInBst(root, 350);
		node16.count = 1;

		BinaryTree.displayInorder(root);
		System.out.println();

		int n = 5;
		currentCount = 0;
		BinaryTreeNode nth_highest_node = findNthHighestInBST(root, n);
		System.out.println(nth_highest_node.data);

	}
}