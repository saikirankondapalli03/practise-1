package com.educative.interviewrefersher.chapter5;

class binarySearchTree {

	private Node root;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	// Iterative Function to insert a value in BST
	public boolean add(int value) {

		// If Tree is empty then insert Root with the given value inside Tree
		if (isEmpty()) {
			root = new Node(value);
			return true;
		}

		// Starting from root
		Node currentNode = root;

		// Traversing the tree untill valid position to insert the value
		while (currentNode != null) {

			Node leftChild = currentNode.getLeftChild();
			Node rightChild = currentNode.getRightChild();

			// If the value to insert is less than root value then move to left subtree
			// else move to right subtree of root
			// and before moving check if the subtree is null, if it's then insert the
			// value.
			if (currentNode.getData() > value) {
				if (leftChild == null) {
					leftChild = new Node(value);
					currentNode.setLeftChild(leftChild);
					return true;
				}
				currentNode = leftChild;
			} else {
				if (rightChild == null) {
					rightChild = new Node(value);
					currentNode.setRightChild(rightChild);
					return true;
				}
				currentNode = rightChild;
			} // end of else
		} // end of while
		return false;
	}

    
	//3 conditions for delete
		//1.node is leaf node.
		//2.node has 1 child.
		//3.node has 2 children.
		boolean delete(int value, Node currentNode) {
	       
	        if (root == null) {
	            return false;
	        } 

	        Node parent = null; //To Store parent of currentNode
	        while(currentNode != null && (currentNode.getData() != value)) {
	            parent = currentNode;
	            if (currentNode.getData() > value)
	                currentNode = currentNode.getLeftChild();
	            else
	                currentNode = currentNode.getRightChild();
	            
	        }
	       
	        if(currentNode == null) {
	            return false;
	        }
	        else if(currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
	            //1. Node is Leaf Node
	            //if that leaf node is the root (a tree with just root)
	            if(root.getData() == currentNode.getData()){
	                setRoot(null);
	                return true;
	            }
	            else if(currentNode.getData() < parent.getData()){
					parent.setLeftChild(null);
	                return true;
	             }
	            else{
	                parent.setRightChild(null);
	                return true;
	            }
	        } else if(currentNode.getRightChild() == null) {
	            
	            if(root.getData() == currentNode.getData()){
	                setRoot(currentNode.getLeftChild());
	                return true;
	            }
	            else if(currentNode.getData() < parent.getData()){
	                parent.setLeftChild(currentNode.getLeftChild());
	                return true;
	            }
	            else{

	                parent.setRightChild(currentNode.getLeftChild());
	                return true;
	            }

	        }
	        else if(currentNode.getLeftChild() == null) {
	            
	            if(root.getData() == currentNode.getData()){
	                setRoot(currentNode.getRightChild());
	                return true;
	            }
	            else if(currentNode.getData() < parent.getData()){
	                parent.setLeftChild(currentNode.getRightChild());
	                return true;
	            }
	            else{
	                parent.setRightChild(currentNode.getRightChild());
	                return true;
	            }

	        }
	        else {
	            //Find Least Value Node in right-subtree of current Node
	            Node leastNode = findLeastNode(currentNode.getRightChild());
	            //Set CurrentNode's Data to the least value in its right-subtree
	            int temp = leastNode.getData();
	            delete(temp, root);
	            currentNode.setData(temp);
	            //Delete the leafNode which had the least value
	            return true;
	        }

	    }

		//Helper function to find least value node in right-subtree of currentNode
		private Node findLeastNode(Node currentNode) {

			Node temp = currentNode;

			while (temp.getLeftChild() != null) {
				temp = temp.getLeftChild();
			}

			return temp;

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

		bsT.add(6);
		bsT.add(4);
		bsT.add(9);
		bsT.add(5);
		bsT.add(2);
		bsT.add(8);
		bsT.add(12);
		bsT.add(10);
		bsT.add(14);

		bsT.printTree(bsT.getRoot());
	}

}