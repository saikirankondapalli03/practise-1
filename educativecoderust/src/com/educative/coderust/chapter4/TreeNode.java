package com.educative.coderust.chapter4;

import java.util.ArrayDeque;
import java.util.ArrayList;

class TreeNode {
	public int data;
	public String text;
	public ArrayList<TreeNode> Children = new ArrayList<TreeNode>();

	public TreeNode(int data) {
		this.data = data;
	}

	public TreeNode(String text) {
		this.text = text;
	}

	public static void display_level_order(TreeNode root) {
		if (root == null)
			return;

		ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
		queue.addLast(root);
		TreeNode tempNode = new TreeNode(100);
		queue.addLast(tempNode);

		while (!queue.isEmpty()) {
			TreeNode temp = queue.removeFirst();

			if (temp == tempNode) {
				System.out.println();
				if (!queue.isEmpty()) {
					queue.addLast(tempNode);
					continue;
				} else {
					return;
				}

			}

			System.out.print(temp.data + ", ");

			for (int i = 0; i < temp.Children.size(); i++) {
				queue.addLast(temp.Children.get(i));
			}
		}
		System.out.println();
	}
}
