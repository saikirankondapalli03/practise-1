package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.List;

class ConnectSiblings {
	private static BinaryTreeNode connectNextLevel(BinaryTreeNode head) {
		BinaryTreeNode current = head;
		BinaryTreeNode nextLevelHead = null;
		BinaryTreeNode prev = null;

 		while (current != null) {
			BinaryTreeNode currentLeft = current.left;
			BinaryTreeNode currentRight = current.right;
			if (currentLeft != null && currentRight != null) {
				if (nextLevelHead == null) {
					nextLevelHead = currentLeft;
				}
				currentLeft.next = currentRight;

				if (prev != null) {
					prev.next = currentLeft;
				}
				prev = currentRight;
			} else if (currentLeft != null) {
				if (nextLevelHead == null) {
					nextLevelHead = currentLeft;
				}
				if (prev != null) {
					prev.next = currentLeft;
				}
				prev = currentLeft;
			} else if (currentRight != null) {
				if (nextLevelHead == null) {
					nextLevelHead = currentRight;
				}

				if (prev != null) {
					prev.next = currentRight;
				}
				prev = currentRight;
			}
			current = current.next;
		}

		if (prev != null) {
			prev.next = null;
		}

		return nextLevelHead;
	}

	public static void populateSiblingPointers(BinaryTreeNode root) {

		if (root == null) {
			return;
		}

		root.next = null;

		do {
			root = connectNextLevel(root);
		} while (root != null);
	}

	static List<Integer> getLevelOrderUsingNext(BinaryTreeNode root) {
		List<Integer> output = new ArrayList<Integer>();
		while (root != null) {
			BinaryTreeNode head = root;
			BinaryTreeNode next_head = null;
			while (head != null) {
				output.add(head.data);

				if (next_head == null) {
					next_head = head.left != null ? head.left : head.right;
				}
				head = head.next;
			}
			root = next_head;
		}
		return output;
	}

	public static void main(String[] args) {
		List<Integer> v = new ArrayList<Integer>();
		v.add(100);
		v.add(50);
		v.add(200);
		v.add(25);
		v.add(75);
		v.add(300);
		v.add(10);
		v.add(350);
		v.add(15);

		BinaryTreeNode head = BinaryTree.createBST(v);

		List<Integer> v1 = BinaryTree.getLevelOrder(head);

		populateSiblingPointers(head);

		List<Integer> result = getLevelOrderUsingNext(head);

		System.out.println(result);
		System.out.println(head.next); // null
		System.out.println(head.left.next.data); // 200
		System.out.println(head.left.right.next.data); // 300
		System.out.println(head.right.right.next); // null
	}
}
