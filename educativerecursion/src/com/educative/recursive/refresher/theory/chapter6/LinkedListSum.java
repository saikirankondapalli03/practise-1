package com.educative.recursive.refresher.theory.chapter6;

class LinkedListSum {

    // Linked List Node
    static class Node {
      int value;
      Node next;
    };

    static int sumList(Node head) {

      // Base case
      if (head == null) {
        return 0;
      }

      // Recursive case
      else {
        return (head.value + (sumList(head.next)));
      }
    } 

    static Node insertAtHead(Node temp_head, int new_value) {
      Node new_Node = new Node();
      new_Node.value = new_value;
      new_Node.next = (temp_head);
      (temp_head) = new_Node;

      return temp_head;
    }

    public static void main( String args[] ) {
        // Empty Linked List
        Node head = null;

        // Linked List = 3->1->5->8->2
        head = insertAtHead(head, 2);        
        head = insertAtHead(head, 8);        
        head = insertAtHead(head, 5);        
        head = insertAtHead(head, 1);        
        head = insertAtHead(head, 3);

        System.out.println("Linked List: ");
        for (Node i = head; i != null; i = i.next) {
          System.out.print(i.value + " ");
        } 

        int sum = sumList(head);

        System.out.println(" ");
        System.out.println("Sum: " + sum);
    }
}
