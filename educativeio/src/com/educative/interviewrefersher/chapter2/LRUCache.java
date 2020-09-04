package com.educative.interviewrefersher.chapter2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

//Linked list operations
//SinglyLinkedList(int data) 
//SinglyLinkedList(int key, int data)
//SinglyLinkedList(int data, SinglyLinkedList next)
//SinglyLinkedList(int data, SinglyLinkedList next, SinglyLinkedList arbitrary_pointer)

class LRUCache {
	int capacity;

	// SinglyLinkedList holds key and value pairs
	Set<Integer> cache;
	LinkedList<SinglyLinkedList> cacheVals;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		cache = new HashSet<Integer>(capacity);
		cacheVals = new LinkedList<SinglyLinkedList>();
	}

	SinglyLinkedList get(int key) {

		if (!cache.contains(key)) {

			return null;
		} else {

			ListIterator<SinglyLinkedList> iterator1 = cacheVals.listIterator(0);
			while (iterator1.hasNext()) {
				SinglyLinkedList node = iterator1.next();
				if (node.key == key) {
					return node;
				}
			}
			return null;
		}
	}

	void set(int key, int value) {
		SinglyLinkedList node = get(key);
		if (node == null) {
			evictIfNeeded();
			node = new LinkedList(key, value);
 			cache.add(key);
		} else {
			cacheVals.remove(node);
		}
		cacheVals.addLast(node);
	}

	void evictIfNeeded() {
		if (cacheVals.size() >= capacity) {
			SinglyLinkedList node = cacheVals.remove();
			cache.remove(node.key);

		}
	}

	void print() {
		ListIterator<SinglyLinkedList> iterator = cacheVals.listIterator(0);
		while (iterator.hasNext()) {
			SinglyLinkedList node = iterator.next();
			System.out.print("(" + node.key + "," + node.data + ")");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		LRUCache cache = new LRUCache(2);

		cache.set(10, 20);
		cache.print();

		cache.set(15, 25);
		cache.print();

		cache.set(20, 30);
		cache.print();

		cache.set(25, 35);
		cache.print();

		cache.set(5, 25);
		cache.print();
	}
}