package com.educative.coderust.chapter5;

class BinaryTreeNode {
	public BinaryTreeNode(int data, BinaryTreeNode left, BinaryTreeNode right) {
		super();
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public int data;
	public BinaryTreeNode left;
	public BinaryTreeNode right;

	// below data members used only for some of the problems
	public BinaryTreeNode next;
	public BinaryTreeNode parent;
	public int count;

	public BinaryTreeNode(int d) {
		data = d;
		left = null;
		right = null;
		parent = null;
		count = 0;
	}

	BinaryTreeNode copy() {
		BinaryTreeNode left = null;
		BinaryTreeNode right = null;
		if (this.left != null) {
			left = this.left.copy();
		}
		if (this.right != null) {
			right = this.right.copy();
		}
		return new BinaryTreeNode(data, left, right);
	}
}