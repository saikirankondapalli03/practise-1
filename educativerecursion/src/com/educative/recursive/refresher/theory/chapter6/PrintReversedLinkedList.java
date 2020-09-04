package com.educative.recursive.refresher.theory.chapter6;

class PrintReversedLinkedList {

	// Linked List Node
	static class Node {
		int value;
		Node next;
	};
	
	public static void reverse(Node head) {

	      // Base case
	      if (head == null) {
	        return;
	      }

	      // Recursive case
	      else {
	        reverse(head.next);
	        System.out.print(head.value + " ");
	      }
	    }
	
	
	public static Node output = null;

	public static Node reverseFirst(Node head) {
		reverseHelper(head);
		return output;
	}
	
	public static void reverseHelper(Node head) {
		
		if(head.next == null) {
			output = head;
			return;
		}
		
		reverseHelper(head.next);
		
		head.next = null;
		
		output.next = head;
		
	}
	
	
	

	// Complete the reverse function below.
	static Node reverseIt(Node head) {
		
		if (head == null) {
			return head;
		}

		// last node or only one node
		if (head.next == null) {
			return head;
		}

		// # A. label the end node as the new head node
		Node newHeadNode = reverseIt(head.next);

		// # B. set the new head node's next_node to be the previous head node (which is now the end node)
		head.next.next = head;
		// # C. set the old head node's next_node to None, which makes it the end node
		head.next = null;

		// send back new head node in every recursion
		return newHeadNode;
	}
	

	static Node insertAtHead(Node temp_head, int new_value) {
		Node new_Node = new Node();
		new_Node.value = new_value;
		new_Node.next = (temp_head);
		(temp_head) = new_Node;

		return temp_head;
	}

	
	
	public static void main(String args[]) {
		// Empty Linked List
		Node head = null;

		// Linked List = 1->2->3->4->5
		head = insertAtHead(head, 4);
		head = insertAtHead(head, 3);
		head = insertAtHead(head, 2);
		head = insertAtHead(head, 1);

		// Print the original Linked List
		System.out.println("Linked List: ");
		for (Node i = head; i != null; i = i.next) {
			System.out.print(i.value + " ");
		}

		// Print the reversed Linked List
		System.out.println(" ");
		System.out.println("Reversed Linked List: ");
		Node result = reverseIt(head);
		for (Node i = result; i != null; i = i.next) {
			System.out.print(i.value + " ");
		}

	}
}