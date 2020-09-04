package com.educative.coderust.chapter5;

class MirrorBT {
	public static void mirrorBinaryTree(BinaryTreeNode root) {

		if (root == null) {
			return;
		}

		// We will do a post-order traversal (visit left, right and root) of the binary tree.

		if (root.left != null) {
			mirrorBinaryTree(root.left);
		}

		if (root.right != null) {
			mirrorBinaryTree(root.right);
		}

		// Let's swap the left and right nodes at current level.

		BinaryTreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
	}

	public static void main(String[] argv) {
		BinaryTreeNode root = new BinaryTreeNode(200);
		BinaryTree.insert(root, 50);
		BinaryTree.insert(root, 100);
		BinaryTree.insert(root, 25);
		BinaryTree.insert(root, 275);
		BinaryTree.insert(root, 250);
		
		BinaryTree.insert(root, 300);
		BinaryTree.displayInorder(root);
		System.out.println();

		mirrorBinaryTree(root);
		System.out.println("Mirrored Tree:");
		BinaryTree.displayLevelOrder(root);
	}
}
