package com.educative.coderust.chapter2.linkedlist;

class ReverseLinkedList {
	public static LinkedListNode reverse(LinkedListNode head) {
		// no need to reverse if head is null
		// or there is only 1 node.
		if (head == null || head.next == null) {
			return head;
		}

		LinkedListNode listToDo = head.next;
		LinkedListNode reversedList = head;

		reversedList.next = null;

		while (listToDo != null) {
			LinkedListNode temp = listToDo;
			listToDo = listToDo.next;

			temp.next = reversedList;
			reversedList = temp;
		}

		return reversedList;
	}
	
	 /* Function to reverse the linked list */
	LinkedListNode reverseIterative(LinkedListNode node) 
    { 
		LinkedListNode prev = null; 
		LinkedListNode current = node; 
		LinkedListNode next = null; 
        while (current != null) { 
            next = current.next; 
            current.next = prev; 
            prev = current; 
            current = next; 
        } 
        node = prev; 
        return node; 
    } 
    
	public static LinkedListNode reverseRecursion(LinkedListNode head) {
	    //no need to reverse if head is 
	    //null or there is only 1 node.
	    if (head == null || head.next == null) {
	        return head;
	    }

	    LinkedListNode reversedList = reverseRecursion(head.next);

	    head.next.next = head;
	    head.next = null;
	    return reversedList;
	  }

	public static void main(String[] args) {
		LinkedListNode listHead = null;
		int[] arr = { 7, 14 };
		listHead = LinkedList.createLinkedList(arr);

		System.out.print("Original: ");
		LinkedList.display(listHead);

		listHead = reverseRecursion(listHead);
		System.out.print("After Reverse: ");
		LinkedList.display(listHead);
	}
}