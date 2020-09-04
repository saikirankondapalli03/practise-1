package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class PrintTreePerimeter {
	public static void printLeftPerimeter(BinaryTreeNode root, StringBuilder result) {
		while (root != null) {

			int curr_val = root.data;

			if (root.left != null) {
				root = root.left;
			} else if (root.right != null) {
				root = root.right;
			} else {
				break; // leaf node.
			}
			// System.out.print(curr_val + " ");
			result.append(String.valueOf(curr_val) + " ");
		}
	}

	public static void printRightPerimeter(BinaryTreeNode root, StringBuilder result) {
		// stack for right side values.
		Stack<Integer> r_values = new Stack<Integer>();

		while (root != null) {

			int curr_val = root.data;

			if (root.right != null) {
				root = root.right;
			} else if (root.left != null) {
				root = root.left;
			} else {
				break; // leaf node.
			}

			r_values.push(curr_val);
		}

		while (!r_values.isEmpty()) {
			// System.out.print(r_values.pop() + " ");
			result.append(String.valueOf(r_values.pop()) + " ");
		}
	}

	public static void printLeafNodes(BinaryTreeNode root, StringBuilder result) {
		if (root != null) {
			printLeafNodes(root.left, result);
			printLeafNodes(root.right, result);

			if (root.left == null && root.right == null) {
				// System.out.print(root.data + " ");
				result.append(String.valueOf(root.data) + " ");
			}
		}
	}

	public static String displayTreePerimeter(BinaryTreeNode root) {
		StringBuilder result = new StringBuilder();
		if (root != null) {
			// System.out.print(root.data + " ");
			result.append(String.valueOf(root.data) + " ");

			printLeftPerimeter(root.left, result);

			if (root.left != null || root.right != null) {
				// We don't need to print if root is also the leaf node.
				printLeafNodes(root, result);
			}

			printRightPerimeter(root.right, result);
		}
		return result.toString();
	}

	public static void main(String[] argv) {
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(100);
		arr.add(50);
		arr.add(200);
		arr.add(25);
		arr.add(60);
		arr.add(350);
		arr.add(10);
		arr.add(70);
		arr.add(400);

		BinaryTreeNode root = BinaryTree.createBST(arr);
		BinaryTree.displayLevelOrder(root);
		BinaryTree.anotherDisplayTree(root);
		System.out.print("Perimeter:\n");
		System.out.println(displayTreePerimeter(root));
	}
}