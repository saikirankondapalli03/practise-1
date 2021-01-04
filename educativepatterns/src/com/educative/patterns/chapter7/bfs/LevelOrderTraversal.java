package com.educative.patterns.chapter7.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class LevelOrderTraversal {
	public static List<List<Integer>> traverse(TreeNode root) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (root == null)
			return result;

		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> currentLevel = new ArrayList<>(levelSize);
			for (int i = 0; i < levelSize; i++) {
				TreeNode currentNode = queue.poll();
				// add the node to the current level
				currentLevel.add(currentNode.val);
				// insert the children of current node in the queue
				if (currentNode.left != null)
					queue.offer(currentNode.left);
				if (currentNode.right != null)
					queue.offer(currentNode.right);
			}
			result.add(currentLevel);
		}

		return result;
	}

	// done
	public static List<Integer> getLevelOrder(TreeNode root) {
		List<Integer> output = new ArrayList<Integer>();
		if (root == null)
			return output;
		ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
		queue.addLast(root);
		while (!queue.isEmpty()) {
			TreeNode temp = queue.removeFirst();
			// System.out.print(temp.data + ", ");
			output.add(temp.val);
			if (temp.left != null) {
				queue.addLast(temp.left);
				// System.out.println(temp.left.data + "LEFT");
			}
			if (temp.right != null) {
				queue.addLast(temp.right);
				// System.out.println(temp.right.data + "RIGHT");
			}
		}
		return output;
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(12);
		root.left = new TreeNode(7);
		root.right = new TreeNode(1);
		root.left.left = new TreeNode(9);
		root.right.left = new TreeNode(10);
		root.right.right = new TreeNode(5);
		List<List<Integer>> result = LevelOrderTraversal.traverse(root);
		System.out.println(LevelOrderTraversal.getLevelOrder(root));
		System.out.println("Level order traversal: " + result);
	}
}
