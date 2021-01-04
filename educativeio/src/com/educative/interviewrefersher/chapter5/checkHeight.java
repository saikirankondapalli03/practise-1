package com.educative.interviewrefersher.chapter5;

class checkHeight {
	public static int findHeight(Node root) {
		// Base case, leaf nodes have 0 height
		if (root == null)
			return -1;
		else {
			int leftTree = findHeight(root.getLeftChild());
			int rightTree = findHeight(root.getRightChild());
			int max = Math.max(leftTree, rightTree);
			return 1 + max;
			// Find Height of left subtree right subtree
			// Return greater height value of left or right subtree (plus 1)
		}
	}

	public static void main(String args[]) {

		binarySearchTree bsT = new binarySearchTree();

		bsT.add(5);
		bsT.add(2);
		bsT.add(-4);
		bsT.add(6);
		bsT.add(18);
		bsT.add(21);
		bsT.add(17);
		bsT.add(25);
		bsT.add(20);

		System.out.println(findHeight(bsT.getRoot()));

	}
}