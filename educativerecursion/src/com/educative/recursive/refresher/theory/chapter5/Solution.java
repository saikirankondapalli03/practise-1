package com.educative.recursive.refresher.theory.chapter5;

public class Solution {

   static class Node {
     
	 int value;
	 Node next;
  }
  
  
  public static Node outputHead = null, outputTail = null;
  
  
  public static Node reverse(Node head) {
		reverseList(head);
		return outputHead;
  } 
  
  
  public static void reverseList(Node head) {
	  
		if( head.next == null ) {
			outputHead = head;
			outputTail = head;
			return;
		}
		
		reverseList(head.next);
		
		head.next = null;
		
		outputTail.next = head;
		
		outputTail = outputTail.next;
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
		 reverseList(head);
		for (Node i = head; i != null; i = i.next) {
			System.out.print(i.value + " ");
		}

	}
}