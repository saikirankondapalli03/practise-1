package com.educative.recursive.refresher.theory.chapter2;

import com.educative.recursive.refresher.theory.baseclasses.SinglyLinkedList;

public class LengthLinkedList {
	
	  /* Returns count of nodes in linked list */
    public static int lengthOfList(SinglyLinkedList.Node head) 
    { 
        // Base case 
        if (head == null) {
            return 0; 
        }
        // Recursive case
        else {
        	int temp1=1;
        	int temp2=lengthOfList(head.nextNode);
        	return temp1+temp2;
        }
    } 
  
    public static void main(String[] args) 
    { 
        /* Start with the empty list */
    	SinglyLinkedList llist = new SinglyLinkedList(); 
        llist.insertAtEnd(2); 
        llist.insertAtEnd(3); 
        llist.insertAtEnd(1); 
        llist.insertAtEnd(2); 
        llist.insertAtEnd(1); 
  
        System.out.println("Count of nodes is = " + lengthOfList(llist.getHeadNode())); 
    } 
}
