package com.educative.interviewrefersher.chapter5;

import java.util.HashMap;

class IterativeAncesstors
{ 
	
	public static void vSum(HashMap<Integer,Integer> hash, Node root, int c) {
		/* Construct a binary tree like this
				6
			   / \
			  4   9
			 / \  |  \
			2	5 8	  12
					  / \
					 10  14 
		 */
		if(root.leftChild!=null)
			vSum(hash,root.leftChild,c-1);
		if(root.rightChild!=null)
			vSum(hash,root.rightChild,c-1);
		int data=0;
		if(hash.containsKey(c))
			data= hash.get(c);
		hash.put(c,root.getData()+data);
	}
	// Iterative Function to print all ancestors of a given key 
	public static String findAncestors(Node root, int k) { 
		
		String result = ""; 
		Node tempNode = root; 
		while(tempNode != null && tempNode.getData() != k){ 
			result = result + tempNode.getData() + ","; 
			if(k <= tempNode.getData()){ 
				tempNode = tempNode.getLeftChild(); 
			} else{ 
				tempNode = tempNode.getRightChild(); 
			} 
		} 
		if(tempNode == null){ 
			return ""; 
		} 
		return result; 
	}

	// Driver code 
	public static void main(String[] args) 
	{ 		
		binarySearchTree tree = new binarySearchTree(); 		
		/* Construct a binary tree like this
				6
			   / \
			  4   9
			 / \  |  \
			2	5 8	  12
					  / \
					 10  14 
		*/
		tree.add(6); 
		tree.add(4); 
		tree.add(9); 
		tree.add(2); 
		tree.add(5); 
		tree.add(8); 
		 
		tree.add(12); 
		tree.add(10); 
		tree.add(14); 

		HashMap<Integer,Integer> hash = new HashMap<Integer,Integer>();
		vSum(hash, tree.getRoot(), 0);
		for(int k:hash.keySet()) {
			System.out.println("key(pos):" +k + "sum:"+ hash.get(k)+"");
		}
		
		
		int key = 10; 
		System.out.print(key + " --> ");
		System.out.print(findAncestors(tree.getRoot(), key)); 

		System.out.println();
		key = 5; 
		System.out.print(key + " --> ");
		System.out.print(findAncestors(tree.getRoot(), key)); 
		
		
	}
	/*
	
	Node findLCA(int n1, int n2) 
    { 
        return findLCA(root, n1, n2); 
    } 
  
    // This function returns pointer to LCA of two given 
    // values n1 and n2. This function assumes that n1 and 
    // n2 are present in Binary Tree 
    Node findLCA(Node node, int n1, int n2) 
    { 
        // Base case 
        if (node == null) 
            return null; 
  
        // If either n1 or n2 matches with root's key, report 
        // the presence by returning root (Note that if a key is 
        // ancestor of other, then the ancestor key becomes LCA 
        if (node.data == n1 || node.data == n2) 
            return node; 
  
        // Look for keys in left and right subtrees 
        Node left_lca = findLCA(node.left, n1, n2); 
        Node right_lca = findLCA(node.right, n1, n2); 
  
        // If both of the above calls return Non-NULL, then one key 
        // is present in once subtree and other is present in other, 
        // So this node is the LCA 
        if (left_lca!=null && right_lca!=null) 
            return node; 
  
        // Otherwise check if left subtree or right subtree is LCA 
        return (left_lca != null) ? left_lca : right_lca; 
    }*/
} 