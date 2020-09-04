package com.educative.interviewrefersher.chapter6;

import java.util.Arrays;

class Trie {
	private TrieNode root; // Root Node

	public TrieNode getRoot() {
		return root;
	}

	// Constructor
	Trie() {
		root = new TrieNode();
	}

	// Function to get the index of a character 'x'
	public int getIndex(char x) {
		return x - 'a'; // the index is based on the position of character in alphabets
	}

	// Function to insert a key in the Trie
	public void insert(String key) {
		if (key == null) // Null keys are not allowed
		{
			System.out.println("Null Key can not be Inserted!");
			return;
		}
		key = key.toLowerCase(); // Keys are stored in lowercase
		TrieNode currentNode = this.root;
		// to store character index
		// Iterate the Trie with given character index,
		// If it is null, then simply create a TrieNode and go down a level
		for (int level = 0; level < key.length(); level++) {
			int index = getIndex(key.charAt(level));
			if (currentNode.children[index] == null) {
				currentNode.children[index] = new TrieNode();
			}
			currentNode = currentNode.children[index];
		}
		// Mark the end character as leaf node
		currentNode.markAsLeaf();
	}

	// Function to search given key in Trie
	public boolean search(String key) {
		if (key == null)
			return false; // Null Key

		key = key.toLowerCase();
		TrieNode currentNode = this.root;

		// Iterate the Trie with given character index,
		// If it is null at any point then we stop and return false
		// We will return true only if we reach leafNode and have traversed the
		// Trie based on the length of the key

		for (int level = 0; level < key.length(); level++) {
			int index = getIndex(key.charAt(level));
			if (currentNode.children[index] == null)
				return false;
			currentNode = currentNode.children[index];
		}
		if (currentNode.isEndWord)
			return true;

		return false;
	}

	// Recursive function to delete given key
	private boolean deleteHelper(String key, TrieNode currentNode, int length, int level) 
	{
		boolean deletedSelf = false;
		if (currentNode == null) 
		{
			System.out.println("Key does not exist");
			return deletedSelf;
		}
		// Base Case: If we have reached at the node which points to the alphabet at the
		// end of the key.
		if (level == length) 
		{
			// If there are no nodes ahead of this node in this path
			// Then we can delete this node
			if (hasNoChildren(currentNode)) 
			{
				currentNode = null;
				deletedSelf = true;
				return deletedSelf;
			}
			// If there are nodes ahead of currentNode in this path
			// Then we cannot delete currentNode. We simply unmark this as leaf
			else 
			{
				currentNode.unMarkAsLeaf();
				deletedSelf = false;
				return deletedSelf;
			}
		} else {
			TrieNode childNode = currentNode.children[getIndex(key.charAt(level))];
			boolean childDeleted = deleteHelper(key, childNode, length, level + 1);
			if (childDeleted) {
				// Making children pointer also null: since child is deleted
				currentNode.children[getIndex(key.charAt(level))] = null;
				// If currentNode is leaf node that means currntNode is part of another key
				// and hence we can not delete this node and it's parent path nodes
				if (currentNode.isEndWord) {
					deletedSelf = false;
					return deletedSelf;
				}
				// If childNode is deleted but if currentNode has more children then currentNode
				// must be part of another key.
				// So, we cannot delete currenNode
				else if (!hasNoChildren(currentNode)) {
					deletedSelf = false;
					return deletedSelf;
				}
				// Else we can delete currentNode
				else {
					currentNode = null;
					deletedSelf = true;
					return deletedSelf;
				}
			} else {
				deletedSelf = false;
			}
		}
		return deletedSelf;
	}

	// Helper Function to return true if currentNode does not have any children
	private boolean hasNoChildren(TrieNode currentNode) {
		for (int i = 0; i < currentNode.children.length; i++) {
			if ((currentNode.children[i]) != null)
				return false;
		}
		return true;
	}

	// Function to delete given key from Trie
	public void delete(String key) {
		if ((root == null) || (key == null)) {
			System.out.println("Null key or Empty trie error");
			return;
		}
		deleteHelper(key, root, key.length(), 0);
	}

	public static void main(String args[]) {
		// Input keys (use only 'a' through 'z' and lower case)
		// "the", "a", "any", "bye", "their","abc"
		String keys[] = { "the", "there"};
		String output[] = { "Not present in trie", "Present in trie" };
		Trie t = new Trie();

		System.out.println("Keys: " + Arrays.toString(keys));

		// Construct trie

		int i;
		for (i = 0; i < keys.length; i++)
			t.insert(keys[i]);
		t.delete("there");
		
		/*// Search for different keys and delete if found
		if (t.search("there") == true) {
			System.out.println("there --- " + output[1]);
			t.delete("there");
			System.out.println("Deleted key \"there\"");
		} else
			System.out.println("the --- " + output[0]);

		if (t.search("these") == true) {
			System.out.println("these --- " + output[1]);
			t.delete("these");
			System.out.println("Deleted key \"these\"");
		} else
			System.out.println("these --- " + output[0]);

		if (t.search("abc") == true) {
			System.out.println("abc --- " + output[1]);
			t.delete("abc");
			System.out.println("Deleted key \"abc\"");
		} else
			System.out.println("abc --- " + output[0]);

		// check if the string has deleted correctly or still present
		if (t.search("abc") == true)
			System.out.println("abc --- " + output[1]);
		else
			System.out.println("abc --- " + output[0]);
	}*/
	}
}
