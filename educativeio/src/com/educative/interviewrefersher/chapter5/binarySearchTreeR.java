package com.educative.interviewrefersher.chapter5;

import java.util.ArrayList;
import java.util.List;

class binarySearchTreeR {

	private Node root;


	public Node getRoot() {
		return root;
    }
    
		
	public void setRoot(Node root) {
		this.root = root;
	}

	//Recursive function to insert a value in BST 
	public Node recursive_insert(Node currentNode, int value) {
		List<String> strings= new ArrayList<String>();
		//Base Case
		if (currentNode == null) {
			return new Node(value);
		}

		if (value < currentNode.getData()) {
			//Iterate left sub-tree
			currentNode.setLeftChild(recursive_insert(currentNode.getLeftChild(), value));
		} else if (value > currentNode.getData()) {
			//Iterate right sub-tree
			currentNode.setRightChild(recursive_insert(currentNode.getRightChild(), value));
		} else {
			// value already exists
			return currentNode;
		}

		return currentNode;

	}

	//Function to call recursive insert
	public boolean add(int value) {

		root = recursive_insert(this.root, value);
		return true;
	}

	//Function to check if Tree is empty or not  
	public boolean isEmpty() 
    {
		return root == null; //if root is null then it means Tree is empty
	}

	//Just for Testing purpose 
	public void printTree(Node current) 
    {
		if (current == null) return;

		System.out.print(current.getData() + ",");
		printTree(current.getLeftChild());
		printTree(current.getRightChild());

	}
	
	//Searches value in BST
	//Either returns the Node with that value or return null
	public Node search(int value) {

		if (isEmpty()) return null; // if tree is empty simply return null

		Node currentNode = this.root;

		while (currentNode != null) {

			if (currentNode.getData() == value) return currentNode; // compare data from current node
			
			if (currentNode.getData() > value) //if data from current node is greater than value
				currentNode = currentNode.getLeftChild(); // then move towards left subtree
			else 
				currentNode = currentNode.getRightChild(); //else move towards right subtree
		}

		System.out.println(value + " Not found in the Tree!");
		return null;
	}
	
	 /* A recursive function to insert a new key in BST */
    Node deleteRec(Node root, int key) 
    { 
        /* Base Case: If the tree is empty */
        if (root == null) {
        	return root; 
        }  
        /* Otherwise, recur down the tree */
        if (key < root.getData()) {
        	Node leftChild= deleteRec(root.leftChild, key); 
            root.leftChild =leftChild; 
        }
        else if (key > root.getData()) {
        	Node rightChild= deleteRec(root.rightChild, key);
        	root.rightChild =rightChild; 
        }
             
  
        // if key is same as root's key, then This is the node 
        // to be deleted 
        else
        { 
            // node with only one child or no child 
            if (root.leftChild == null) 
                return root.rightChild; 
            else if (root.rightChild == null) 
                return root.leftChild; 
  
            // node with two children: Get the inorder successor (smallest 
            // in the right subtree) 
            int valueToBeReplaced=minValue(root.rightChild);
           root.setData(valueToBeReplaced); 
           int valueReplaced= root.getData();
            Node temp= deleteRec(root.rightChild,valueReplaced);
            // Delete the inorder successor 
            root.rightChild = temp;
        } 
        
        
        
  
        return root; 
    } 
    
    int minValue(Node root) 
    {  
        int minv = root.getData(); 
        while (root.leftChild != null) 
        { 
            minv = root.leftChild.getData(); 
            root = root.leftChild; 
        } 
        return minv; 
    } 
    
   

		
		
	public static void main(String args[]) {


		binarySearchTreeR bsT = new binarySearchTreeR();

		bsT.add(5);
		bsT.add(2);
		bsT.add(-4);
		bsT.add(6);
		bsT.add(18);
		bsT.add(21);
		bsT.add(17);
		bsT.add(25);
		bsT.add(20);
		

		System.out.print("Tree : ");
		bsT.printTree(bsT.getRoot());

		System.out.print("\nDeleting Node 50: ");
		bsT.deleteRec(bsT.getRoot(),21);
		bsT.printTree(bsT.getRoot());

	}
}