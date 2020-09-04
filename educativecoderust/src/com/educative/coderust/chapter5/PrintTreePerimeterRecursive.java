package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.List;

class PrintTreePerimeterRecursive {

	public static void printLeftPerimeter(BinaryTreeNode root, StringBuffer result) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			// System.out.print(root.data + " ");
			result.append(String.valueOf(root.data) + " ");
			printLeftPerimeter(root.left, result);
		} else if (root.right != null) {
			// System.out.print(root.data + " ");
			result.append(String.valueOf(root.data) + " ");
			printLeftPerimeter(root.right, result);
		}
	}

	public static void printRightPerimeter(BinaryTreeNode root, StringBuffer result) {
		if (root == null) {
			return;
		}
		if (root.right != null) {
			printRightPerimeter(root.right, result);
			result.append(String.valueOf(root.data) + " ");
			// System.out.print(root.data + " ");
		} else if (root.left != null) {
			printRightPerimeter(root.left, result);
			result.append(String.valueOf(root.data) + " ");
			// System.out.print(root.data + " ");
		}
	}

	public static void printLeafNodes(BinaryTreeNode root, StringBuffer result) {
		if (root == null) {
			return;
		}
		printLeafNodes(root.left, result);
		// Print the node if it is a leaf node
		if (root.left == null && root.right == null) {
			// System.out.print(root.data + " ");
			result.append(String.valueOf(root.data) + " ");
		}
		printLeafNodes(root.right, result);
	}

	public static String displayTreePerimeter(BinaryTreeNode root) {
		StringBuffer result = new StringBuffer();
		if (root != null) {
			// System.out.print(root.data + " ");
			result.append(String.valueOf(root.data) + " ");

			printLeftPerimeter(root.left, result);

			// Print all leaf nodes
			printLeafNodes(root.left, result);
			printLeafNodes(root.right, result);

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