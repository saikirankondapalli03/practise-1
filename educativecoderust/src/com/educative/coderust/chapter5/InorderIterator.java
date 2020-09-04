package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class InorderIterator {
	  Stack<BinaryTreeNode> stk = new Stack<BinaryTreeNode>();

	  public InorderIterator(BinaryTreeNode root) {
	    populateIterator(root);
	  }

	  public void populateIterator(BinaryTreeNode root) {
	    while(root != null) {
	      stk.push(root);
	      root = root.left;
	    }
	  }

	  public boolean hasNext() {
	    return !stk.isEmpty();
	  }

	  public BinaryTreeNode getNext() {
	    if(stk.isEmpty())
	      return null;

	    BinaryTreeNode rVal = stk.pop();
	    BinaryTreeNode temp = rVal.right;
	    populateIterator(temp);

	    return rVal;
	  }
	  
	  public static String inorderUsingIterator(BinaryTreeNode root) {
	    InorderIterator iter = new InorderIterator(root);
	    String result = "";
	    while (iter.hasNext()) {
	      result += iter.getNext().data + " "; 
	    }
	    return result;
	  }

	  public static void main(String[] args) {
	    
	    List<Integer> input = new ArrayList<Integer>();
	    input.add(100);input.add(50);input.add(200);input.add(25);input.add(75);input.add(125);input.add(300);
	    input.add(12); input.add(35); input.add(60);
	    BinaryTreeNode root = BinaryTree.createBST(input);
	    System.out.print("Inorder Iterator = ");
	    System.out.println(inorderUsingIterator(root));
	  }
	}