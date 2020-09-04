package com.educative.coderust.chapter2;

class reverseEven {
	// Helper function to merge two lists.
	public static LinkedListNode mergeAlternating(LinkedListNode list1, LinkedListNode listeven) {

		if (list1 == null) {
			return listeven;
		}

		if (listeven == null) {
			return list1;
		}

		LinkedListNode head = list1;

		while (list1.next != null && listeven != null) {
			LinkedListNode temp = listeven;
			listeven = listeven.next;

			temp.next = list1.next;
			list1.next = temp;
			list1 = temp.next;
		}

		if (list1.next == null) {
			list1.next = listeven;
		}

		return head;
	}

	public static LinkedListNode reverseEvenNodes(LinkedListNode head) {

		// Let's extract even nodes from the existing
		// list and create a new list consisting of
		// even nodes. We will push the even nodes at
		// head since we want them to be in reverse order.

		LinkedListNode curr = head;
		LinkedListNode listEven = null;

		while (curr != null && curr.next != null) {
			LinkedListNode even = curr.next;
			curr.next = even.next;

			// Push at the head of new list.
			even.next = listEven;
			listEven = even;

			curr = curr.next;
		}

		return mergeAlternating(head, listEven);
	}

	public static void main(String[] args) {

		int[] arr = { 7, 14, 21, 28, 9 };
		LinkedListNode listHead = LinkedList.createLinkedList(arr);
		System.out.print("Original list: ");
		LinkedList.display(listHead);

		listHead = reverseEvenNodes(listHead);
		System.out.print("After Reverse: ");
		LinkedList.display(listHead);
	}
}