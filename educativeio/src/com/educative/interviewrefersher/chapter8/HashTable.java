package com.educative.interviewrefersher.chapter8;

import java.util.ArrayList;

//Class to represent entire hash table
class HashTable {
	// to store chains of slots
	private ArrayList<HashEntry> bucket;

	// num of slots in each bucket
	private int slots;

	// total hashed keys
	private int size;

	public HashTable() {
		bucket = new ArrayList<HashEntry>();
		slots = 5;
		size = 0;

		// Initialize nodes
		for (int i = 0; i < slots; i++)
			bucket.add(null);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	// Look for the index based on key
	// search function
	private int getIndex(String key) {
		int hashCode = key.hashCode();
		int index = hashCode % slots;
		return index;
	}

	// delete a value
	public int delete(String key) {

		// Look for the index based on key
		int b_Index = getIndex(key);

		HashEntry head = bucket.get(b_Index);

		// Search the key in slots
		HashEntry prev = null;
		while (head != null) {
			// If the key exists
			if (head.key.equals(key))
				break;

			// If the key not found yet
			prev = head;
			head = head.next;
		}

		// If the key does not exist
		if (head == null)
			return 0;

		size--;

		// Delete the value by key
		if (prev != null)
			prev.next = head.next;
		else
			bucket.set(b_Index, head.next);

		return head.value;
	}

	// to get a value for key from table
	public int get(String key) {
		int b_Index = getIndex(key);
		HashEntry head = bucket.get(b_Index);

		// Find the key in slots
		while (head != null) {
			if (head.key.equals(key))
				return head.value;
			head = head.next;
		}

		// If key does not exist
		return 0;
	}

	// Insert the key into table
	public void insert(String key, int value) {
		int b_Index = getIndex(key);
		HashEntry head = bucket.get(b_Index);
		// See if the key exists
		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}

		// Insert key into the bucket
		size++;
		head = bucket.get(b_Index);
		HashEntry new_slot = new HashEntry(key, value);
		new_slot.next = head;
		bucket.set(b_Index, new_slot);

		// If 60% of the array gets filled, double the size
		if ((1.0 * size) / slots >= 0.6) {
			ArrayList<HashEntry> temp = bucket;
			bucket = new ArrayList<>();
			slots = 2 * slots;
			size = 0;
			for (int i = 0; i < slots; i++)
				bucket.add(null);

			for (HashEntry head_Node : temp) {
				while (head_Node != null) {
					insert(head_Node.key, head_Node.value);
					head_Node = head_Node.next;
				}
			}
		}
	}
	

	public static void main(String[] args) {
		HashTable table = new HashTable(); // Create a HashTable
		// Before Insertion
		System.out.println("Table Size = " + table.size());
		table.insert("This", 1); // Key-Value Pair
		table.insert("is", 2);
		table.insert("a", 3);
		table.insert("Test", 4);
		table.insert("Driver", 5);
		System.out.println("Table Size = " + table.size());
		// first search the key then delete it in the table
		// see the implementation first
		System.out.println(table.delete("is") + " : This key is deleted from table");
		System.out.println("Now Size of the table = " + table.size());
		if (table.isEmpty())
			System.out.println("Table is Empty");
		else
			System.out.println("Table is not Empty");

	}

}