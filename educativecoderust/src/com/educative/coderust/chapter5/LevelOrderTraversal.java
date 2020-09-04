package com.educative.coderust.chapter5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class LevelOrderTraversal {
	// Using one queue
	public static String levelOrderTraversal(BinaryTreeNode root) {

		if (root == null) {
			return "";
		}

		String result = "";

		Queue<BinaryTreeNode> current_queue = new ArrayDeque<BinaryTreeNode>();

		BinaryTreeNode dummyNode = new BinaryTreeNode(0);

		current_queue.add(root);
		current_queue.add(dummyNode);

		while (!current_queue.isEmpty()) {
			BinaryTreeNode temp = current_queue.poll();
			System.out.print(temp.data + " ");
			result += String.valueOf(temp.data) + " ";

			if (temp.left != null) {
				current_queue.add(temp.left);
			}

			if (temp.right != null) {
				current_queue.add(temp.right);
			}

			if (current_queue.peek() == dummyNode) {
				System.out.println();

				current_queue.remove();

				if (!current_queue.isEmpty()) {
					current_queue.add(dummyNode);
				}
			}
		}
		System.out.println();
		return result;
	}

	 // Compute the maximum height of a tree
	  public static int getMaxHeight(BinaryTreeNode root) 
	  { 
	    if (root == null) 
	      return 0; 
	    else
	    { 
	      int leftHeight = getMaxHeight(root.left); 
	      int rightHeight = getMaxHeight(root.right); 
	          
	      // Return the larger value
	      return leftHeight > rightHeight ? leftHeight+1 : rightHeight+1; 
	    } 
	  } 

	  // get nodes at a specific level
	  public static void getHLevelOrder (BinaryTreeNode root ,int level, StringBuffer output) 
	  { 
	    if (root == null) 
	      return; 
	    if (level == 1) {
	      System.out.print(root.data + " ");
	      output.append(String.valueOf(root.data) + " ");
	    } 
	    else if (level > 1) 
	    { 
	      getHLevelOrder(root.left, level-1, output); 
	      getHLevelOrder(root.right, level-1, output); 
	    } 
	  } 

	  public static String levelOrderTraversalUsingRecursion(BinaryTreeNode root) 
	  { 
	    int h = getMaxHeight(root); 
	    StringBuffer output = new StringBuffer();
	    for (int i=1; i<=h; i++) {
	      getHLevelOrder(root, i, output);
	      System.out.println(); 
	    }
	    return output.toString();
	  } 

	  
	  
	public static void main(String[] argv) {

		List<Integer> input = new ArrayList<Integer>();
		input.add(100);
		input.add(50);
		input.add(200);
		input.add(25);
		input.add(75);
		input.add(125);
		input.add(350);
		BinaryTreeNode root = BinaryTree.createBST(input);
		System.out.println("Level Order Traversal:");
		levelOrderTraversal(root);
	}
}