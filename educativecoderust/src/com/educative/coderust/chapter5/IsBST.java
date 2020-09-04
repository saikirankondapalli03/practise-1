package com.educative.coderust.chapter5;

class IsBST{
	  private static boolean isBstRec(BinaryTreeNode root, int min_value, int max_value) {

	    if (root == null) {
	      return true;
	    }

	    if (root.data < min_value || root.data > max_value) {
	      return false;
	    }

	    return isBstRec(root.left, min_value, root.data) &&
	           isBstRec(root.right, root.data, max_value);
	  }

	  public static boolean isBst(BinaryTreeNode root) {
	    return isBstRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	  }
	 
	  public static void main(String[] argv) {
	    BinaryTreeNode root = new BinaryTreeNode(100);
	    BinaryTree.insert(root, 50);
	    BinaryTree.insert(root, 200);
	    BinaryTree.insert(root, 25);
	    // Add a node at an incorrect position
	    BinaryTree.insert(root, 125);
	    BinaryTree.insert(root, 150);
	    BinaryTree.insert(root, 300);
	    
	    BinaryTree.displayInorder(root);
	    System.out.println();
	    System.out.println(Boolean.toString(isBst(root)));
	  }
	  
	  static BinaryTreeNode prev = null;

	  public static boolean isBstInorder(BinaryTreeNode root) {

	    if (root == null) {
	      return true;
	    }

	    if (!isBstInorder(root.left)) {
	      return false;
	    }

	    if (prev != null && prev.data >= root.data) {
	      return false;
	    }
	    prev = root;

	    if (!isBstInorder(root.right)) {
	      return false;
	    }

	    return true;
	  } 
	}