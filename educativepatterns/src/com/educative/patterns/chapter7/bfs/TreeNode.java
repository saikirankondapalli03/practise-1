package com.educative.patterns.chapter7.bfs;

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode next;

	TreeNode(int x) {
		val = x;
	}

	// level order traversal using 'next' pointer
	public void printLevelOrder() {
		TreeNode nextLevelRoot = this;
		while (nextLevelRoot != null) {
			TreeNode current = nextLevelRoot;
			nextLevelRoot = null;
			while (current != null) {
				System.out.print(current.val + " ");
				if (nextLevelRoot == null) {
					if (current.left != null)
						nextLevelRoot = current.left;
					else if (current.right != null)
						nextLevelRoot = current.right;
				}
				current = current.next;
			}
			System.out.println();
		}
	}
	
	
	 public void printTree() {
		    TreeNode current = this;
		    System.out.print("Traversal using 'next' pointer: ");
		    while (current != null) {
		      System.out.print(current.val + " ");
		      current = current.next;
		    }
		  }
};