package com.educative.coderust.chapter2.linkedlist;

class ReverseKNodes {
	/*
	 * Reverses alternate k nodes and returns the pointer to the new head node
	 */

	static LinkedListNode reverse(LinkedListNode head, int k) {
		LinkedListNode current = head;
		LinkedListNode next = null;
		LinkedListNode prev = null;

		int count = 0;

		/* Reverse first k nodes of linked list */
		while (count < k && current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
			count++;
		}

		/*
		 * next is now a pointer to (k+1)th node Recursively call for the list starting
		 * from current. And make rest of the list as next of first node
		 */
		if (next != null)
			head.next = reverse(next, k);

		// prev is now head of input list
		return prev;
	}

	static LinkedListNode kAltReverse(LinkedListNode node, int k) {
		LinkedListNode current = node;
		LinkedListNode next = null, prev = null;
		int count = 0;

		/* 1) reverse first k nodes of the linked list */
		while (current != null && count < k) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
			count++;
		}

		/*
		 * 2) Now head points to the kth node. So change next of head to (k+1)th node
		 */
		if (node != null) {
			node.next = current;
		}

		/*
		 * 3) We do not want to reverse next k nodes. So move the current pointer to
		 * skip next k nodes
		 */

		count = 0;
		while (count < k - 1 && current != null) {
			current = current.next;
			count++;
		}

		/*
		 * 4) Recursively call for the list starting from current->next. And make rest
		 * of the list as next of first node
		 */
		if (current != null) {
			current.next = kAltReverse(current.next, k);
		}

		/* 5) prev is new head of the input list */
		return prev;
	}

	static LinkedListNode reverseKNodes(LinkedListNode head, int k) {

		// if k is 0 or 1, then list is not changed
		if (k <= 1 || head == null) {
			return head;
		}

		LinkedListNode reversed = null;
		LinkedListNode prevTail = null;

		while (head != null && k > 0) {
			LinkedListNode currentHead = null;
			LinkedListNode currentTail = head;

			int n = k;
			while (head != null && n > 0) {
				LinkedListNode temp = head.next;
				head.next = currentHead;
				currentHead = head;

				head = temp;
				n--;
			}

			if (reversed == null) {
				reversed = currentHead;
			}

			if (prevTail != null) {
				prevTail.next = currentHead;
			}
			prevTail = currentTail;
		}

		return reversed;
	}

	public static void main(String[] args) {
		int[] v1 = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		LinkedListNode listHead = LinkedList.createLinkedList(v1);
		System.out.print("Original list: ");
		LinkedList.display(listHead);
		listHead = reverseKNodes(listHead, 3);
		System.out.print("List reversed by 4: ");
		LinkedList.display(listHead);
	}
}