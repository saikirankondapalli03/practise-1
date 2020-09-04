package com.educative.recursive.refresher.theory.chapter6;

import com.educative.recursive.refresher.theory.baseclasses.SinglyLinkedList;
import com.educative.recursive.refresher.theory.baseclasses.SinglyLinkedList.Node;

public class LinkedListSearch {
	public static boolean search(SinglyLinkedList<Integer>.Node head, int num) { 
	      // Base case
	      if (head == null) {
	        return false;
	      }

	      // Recursive case
	      else {
	    	  if (head.data == num) {
	          return true;
	        }
	        else {
	          return search(head.nextNode, num);
	        }
	      }
	    } 

	    public static void main( String args[] ) {
	        /* Start with the empty list */
	    	SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>(); 
	        list.insertAtHead(0); 
	        list.insertAtHead(3); 
	        list.insertAtHead(1); 
	        list.insertAtHead(6); 
	        list.insertAtHead(4); 

	        System.out.println("Linked List: ");
	        for (Node i = list.getHeadNode(); i != null; i = i.nextNode) {
	          System.out.print(i.data + " ");
	        }

	        System.out.println(" ");
	        int searchFor = 8;
	        boolean result = search(list.getHeadNode(), searchFor);
	        System.out.println("Is " + searchFor + " present in the list? : " + result);    
	    }
}
