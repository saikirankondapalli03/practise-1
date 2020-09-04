package com.educative.coderust.chapter5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

class ConnectAllSiblings {
	public static void populateSiblingPointers(BinaryTreeNode root) {
		if (root == null)
			return;

		BinaryTreeNode current = root;
		BinaryTreeNode last = root;

		while (current != null) {
			if (current.left != null) {
				last.next = current.left;
				last = current.left;
			}

			if (current.right != null) {
				last.next = current.right;
				last = current.right;
			}

			last.next = null;
			current = current.next;
		}
	}

	public static List<Integer> getLevelOrderTraversalWithSibling(BinaryTreeNode root) {
		List<Integer> l = new ArrayList<Integer>();
		while (root != null) {
			l.add(root.data);
			// System.out.print(root.data + ", ");
			root = root.next;
		}
		return l;
	}

	public static void main(String[] args) {

		List<Integer> input = new ArrayList<Integer>();
		input.add(100);
		input.add(50);
		input.add(200);
		input.add(25);
		input.add(75);
		input.add(125);
		input.add(350);
		BinaryTreeNode root = BinaryTree.createBST(input);
		populateSiblingPointers(root);
		//populateSiblingPointersUsingQueue(root);

		List<Integer> l2 = getLevelOrderTraversalWithSibling(root);
		System.out.println(l2);
	}

	public static void populateSiblingPointersUsingQueue(BinaryTreeNode root) {
		if (root == null)
			return;

		ArrayDeque<BinaryTreeNode> queue = new ArrayDeque<BinaryTreeNode>();
		queue.addLast(root);
		BinaryTreeNode prev = null;

		while (!queue.isEmpty()) {
			BinaryTreeNode temp = queue.removeFirst();

			if (prev != null) {
				prev.next = temp;
			}

			prev = temp;

			if (temp.left != null) {
				queue.addLast(temp.left);
			}

			if (temp.right != null) {
				queue.addLast(temp.right);
			}
		}
		prev.next = null;
	}

	public static List<Integer> getLevelOrderTraversalWithSiblingUsingQueue(BinaryTreeNode root) {
		List<Integer> l = new ArrayList<Integer>();
		while (root != null) {
			l.add(root.data);
			root = root.next;
		}
		return l;
	}
}