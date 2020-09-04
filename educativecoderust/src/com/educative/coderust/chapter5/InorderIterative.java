package com.educative.coderust.chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class InorderIterative{
	  static String iterativeInorder(BinaryTreeNode root)
	  {
	    String result = "";
	    if(root == null)
	      return result;

	    Stack<BinaryTreeNode> stk = new Stack<BinaryTreeNode>();
	  
	    while(!stk.empty() || root != null)
	    {
	      if(root != null)
	      {
	        stk.push(root);
	        root = root.left;
	        continue;
	      }
	      result+=stk.peek().data + " ";
	      System.out.print(stk.peek().data + " ");
	      root = stk.pop().right;
	    }
	    return result;
	  }

	  public static void main(String[] args) {
	    List<Integer> input = new ArrayList<Integer>();
	    input.add(100);input.add(50);input.add(200);input.add(25);input.add(75);input.add(35);
	    BinaryTreeNode root = BinaryTree.createBST(input);
	    System.out.print("Inorder Iterative Traversal= ");
	    iterativeInorder(root);
	    System.out.println();
	  }
	}  