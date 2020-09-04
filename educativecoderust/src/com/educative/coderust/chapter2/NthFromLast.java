package com.educative.coderust.chapter2;

class NthFromLast {
	public static LinkedListNode findNthFromLast(LinkedListNode head, int n) {
		if (head == null || n < 1) {
			return null;
		}

		// We will use two pointers head and tail
		// where head and tail are 'n' nodes apart.
		LinkedListNode tail = head;

		while (tail != null && n > 0) {
			tail = tail.next;
			--n;
		}

		// Check out-of-bounds
		if (n != 0) {
			return null;
		}

		// When tail pointer reaches the end of
		// list, head is pointing at nth node.
		while (tail != null) {
			tail = tail.next;
			head = head.next;
		}

		return head;
	}

	public static void main(String[] args) {
		LinkedListNode listHead = null;
		int[] arr = { 7, 14, 21, 28, 35, 42 };

		listHead = LinkedList.createLinkedList(arr);
		System.out.print("List: ");
		LinkedList.display(listHead);

		LinkedListNode temp = findNthFromLast(listHead, 3);
		System.out.println(String.format("3rd element from last: %d", temp.data));
	}
}