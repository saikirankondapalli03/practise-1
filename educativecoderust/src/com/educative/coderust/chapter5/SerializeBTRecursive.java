package com.educative.coderust.chapter5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

class SerializeBTRecursive {
	  public static void serializePreorder(BinaryTreeNode node, ObjectOutputStream stream)
	  throws java.io.IOException {
	    if (node == null) {
	      return;
	    }

	    stream.writeInt(node.data);
	    serializePreorder(node.left, stream);
	    serializePreorder(node.right, stream);
	  }

	  public static void serializeInorder(BinaryTreeNode node, ObjectOutputStream stream)
	  throws java.io.IOException {
	    if (node == null) {
	      return;
	    }

	    serializeInorder(node.left, stream);
	    stream.writeInt(node.data);
	    serializeInorder(node.right, stream);
	  }

	  public static ArrayList<Integer> deserializePreorder(ObjectInputStream stream, int size)
	  throws java.io.IOException {

	    ArrayList<Integer> v = new ArrayList<>();
	    int val;

	    int i = 0;
	    while (i < size) {
	      val = stream.readInt();
	      v.add(val);
	      i++;
	    }

	    return v;
	  }

	  public static ArrayList<Integer> deserializeInorder(ObjectInputStream stream, int size)
	  throws java.io.IOException {
	    ArrayList<Integer> v = new ArrayList<>();
	    int val;

	    int i = 0;
	    while (i < size) {
	      val = stream.readInt();
	      v.add(val);
	      i++;
	    }

	    return v;
	  }

	  public static BinaryTreeNode deserialize(ArrayList preOrder, ArrayList inOrder, int preStart,
	  int inStart, int preEnd, int inEnd) {

	    // check if there is no element or one element
	    if (preStart > preEnd)
	      return null;
	    else if (preStart == preEnd) {
	      BinaryTreeNode node = new BinaryTreeNode(
	          (int) preOrder.get(preStart));
	      node.left = null;
	      node.right = null;
	      return node;
	    }
	    // otherwise first element in preOrder array is root, we find that value in
	    // inOrder array
	    // and determine how many elemnets are in left and right subtrees
	    int rootIndexInorder = 0;

	    for (int i = inStart; i <= inEnd; i++) {
	      if (inOrder.get(i).equals(preOrder.get(preStart))) {
	        rootIndexInorder = i; // we find the first value of preOrder in inOrder
	                              // array.
	        break;
	      }
	    }

	    // now we calculate number of elements in right subtree and left subtree
	    int leftSubCount = rootIndexInorder - inStart;
	    int rightSubCount = inEnd - rootIndexInorder;

	    BinaryTreeNode node = new BinaryTreeNode(
	        (int) preOrder.get(preStart));
	    node.left = deserialize(preOrder, inOrder,
	        preStart + 1, inStart, preStart + leftSubCount,
	        inStart + leftSubCount - 1);
	    node.right = deserialize(preOrder, inOrder,
	        preStart + leftSubCount + 1,
	        inStart + leftSubCount + 1, preEnd, inEnd);
	    return node;
	  }

	  public static void main(String[] args) {

	    List<Integer> input = new ArrayList<Integer>();
	    input.add(100);input.add(50);input.add(200);input.add(25);input.add(75);input.add(125);input.add(350);
	    BinaryTreeNode root = BinaryTree.createBST(input);
	    try {
	      ByteArrayOutputStream baostream1 = new ByteArrayOutputStream();
	      ObjectOutputStream stream1 = new ObjectOutputStream(baostream1);
	      serializePreorder(root, stream1);
	      stream1.close();

	      ArrayList<Integer> preOrder = new ArrayList<Integer>();

	      ByteArrayInputStream baistream1 = new ByteArrayInputStream(
	      baostream1.toByteArray());
	      ObjectInputStream  istream1 = new ObjectInputStream(baistream1);
	      preOrder = deserializePreorder(istream1,input.size());
	      istream1.close();
	      baistream1.close();
	      baostream1.close();
	      System.out.println(preOrder);

	      ByteArrayOutputStream baostream2 = new ByteArrayOutputStream();
	      ObjectOutputStream stream2 = new ObjectOutputStream(baostream2);
	      serializeInorder(root, stream2);
	      stream2.close();
	    
	      ArrayList<Integer> inOrder = new ArrayList<Integer>();

	      ByteArrayInputStream baistream2 = new ByteArrayInputStream(
	      baostream2.toByteArray());
	      ObjectInputStream  istream2 = new ObjectInputStream(baistream2);
	      inOrder = deserializeInorder(istream2,input.size());
	      istream2.close();
	      baistream2.close();
	      System.out.println(inOrder);

	      BinaryTreeNode root2 = deserialize(preOrder, inOrder, 0, 0, preOrder.size() -1, inOrder.size()-1);
	      System.out.println("Result:");
	      BinaryTree.displayLevelOrder(root2); 
	    } catch(Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	}