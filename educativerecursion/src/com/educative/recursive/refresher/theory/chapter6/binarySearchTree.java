package com.educative.recursive.refresher.theory.chapter6;


class binarySearchTree {

	// Variables
	private Node root;

	// Getter for Root
	public Node getRoot() {
		return root;
	}

	// Setter for root
	public void setRoot(Node root) {
		this.root = root;
	}

	// Recursive function to insert a value in BST
	public Node recursive_insert(Node currentNode, int value) {

		// Base Case
		if (currentNode == null) {
			return new Node(value);
		}

		if (value < currentNode.getData()) {
			// Iterate left sub-tree
			currentNode.setLeftChild(recursive_insert(currentNode.getLeftChild(), value));
		} else if (value > currentNode.getData()) {
			// Iterate right sub-tree
			currentNode.setRightChild(recursive_insert(currentNode.getRightChild(), value));
		} else {
			// value already exists
			return currentNode;
		}

		return currentNode;
	}

	// Function to call recursive insert
	public boolean insert(int value) {

		root = recursive_insert(this.root, value);
		return true;
	}

	// Function to check if Tree is empty or not
	public boolean isEmpty() {
		return root == null; // if root is null then it means Tree is empty
	}

	// Just for Testing purpose
	public void printTree(Node current) {

		if (current == null)
			return;

		System.out.print(current.getData() + ",");
		printTree(current.getLeftChild());
		printTree(current.getRightChild());

	}

	public static void main(String args[]) {

		binarySearchTree bsT = new binarySearchTree();
		bsT.insert(6);
		bsT.insert(4);
		bsT.insert(8);
		bsT.insert(5);
		bsT.insert(2);
		bsT.insert(8);
		bsT.insert(12);
		bsT.insert(10);
		bsT.insert(14);
		bsT.printTree(bsT.getRoot());

	}
}