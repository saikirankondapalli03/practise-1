package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class N_aryBinaryConversion {
	public static BinaryTreeNode convertN_aryToBinary(TreeNode root) {
		return convertN_aryToBinary(root, true);
	}

	public static BinaryTreeNode convertN_aryToBinary(TreeNode node, boolean isLeft) {
		if (node == null) {
			return null;
		}

		BinaryTreeNode btnode = new BinaryTreeNode(node.data);
		BinaryTreeNode lastnode = btnode;
		for (int i = 0; i < node.children.size(); i++) {
			if (isLeft) {
				lastnode.left = convertN_aryToBinary(node.children.get(i), !isLeft);
				lastnode = lastnode.left;
			} else {
				lastnode.right = convertN_aryToBinary(node.children.get(i), !isLeft);
				lastnode = lastnode.right;
			}
		}
		return btnode;
	}

	public static TreeNode convertBinaryToN_aryTree(BinaryTreeNode root) {
		return convertBinaryToN_aryTreeRec(root, true);
	}

	public static TreeNode convertBinaryToN_aryTreeRec(BinaryTreeNode node, boolean isLeft) {
		if (node == null) {
			return null;
		}

		TreeNode nnode = new TreeNode(node.data);
		BinaryTreeNode temp = node;

		if (isLeft) {
			while (temp.left != null) {
				TreeNode child = convertBinaryToN_aryTreeRec(temp.left, !isLeft);
				nnode.children.add(child);
				temp = temp.left;
			}
		} else {
			while (temp.right != null) {
				TreeNode child = convertBinaryToN_aryTreeRec(temp.right, !isLeft);
				nnode.children.add(child);
				temp = temp.right;
			}
		}
		return nnode;
	}

	public static void main(String[] argv) {
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		TreeNode node3 = new TreeNode(3);
		TreeNode node4 = new TreeNode(4);
		node1.children.add(node2);
		node1.children.add(node3);
		node1.children.add(node4);
		TreeNode node5 = new TreeNode(5);
		TreeNode node6 = new TreeNode(6);
		node3.children.add(node5);
		node3.children.add(node6);

		List<Integer> list=preorder(node1);
		System.out.println("Original n-ary tree");
		// TreeNode.display_level_order(node1);

		BinaryTreeNode bnode1 = convertN_aryToBinary(node1);
		System.out.println("\nConverted binary tree");
		BinaryTree.displayLevelOrder(bnode1);
		// If the tree is converted into BT then the following statement must return "5"
		// System.out.println("\nRoot.Left.Left.Right = " +
		// bnode1.left.left.right.data);

		// System.out.println("Converting BT to N_ary Tree\n");
		TreeNode tnode1 = convertBinaryToN_aryTree(bnode1);
		System.out.println("Converted n-ary tree");
		// TreeNode.displayLevelOrder(tnode1);
		
		maxDepth(node1);
	}

	private static class TreeNode {

		int data;
		List<TreeNode> children = new ArrayList<>();

		public TreeNode() {
		}

		public TreeNode(int val) {
			this.data = val;
		}
	}

	public TreeNode clone(TreeNode root) {
		if (root == null) {
			return null;
		}

		TreeNode rootClone = new TreeNode(root.data);
		for (TreeNode child : root.children) {
			TreeNode childClone = clone(child);
			rootClone.children.add(childClone);
		}

		return rootClone;
	}

	public static List<Integer> preorder(TreeNode root) {
		LinkedList<TreeNode> stack = new LinkedList<>();
		LinkedList<Integer> output = new LinkedList<>();
		if (root == null) {
			return output;
		}

		stack.add(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pollLast();
			output.add(node.data);
			Collections.reverse(node.children);
			for (TreeNode item : node.children) {
				stack.add(item);
			}
		}
		return output;
	}

	public static int maxDepth(TreeNode root) {
		if (root == null) {
			return 0;
		} else if (root.children.isEmpty()) {
			return 1;
		} else {
			List<Integer> heights = new LinkedList<>();
			for (TreeNode item : root.children) {
				heights.add(maxDepth(item));
			}
			return Collections.max(heights) + 1;
		}
	}
}