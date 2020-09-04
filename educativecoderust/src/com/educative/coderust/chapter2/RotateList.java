package com.educative.coderust.chapter2;

class RotateList {
	private static int findLength(LinkedListNode head) {
		int len = 0;

		while (head != null) {
			++len;
			head = head.next;
		}

		return len;
	}

	private static int adjustRotationsNeeded(int n, int len) {
		// If n is positive then number of rotations performed is from right side
		// and if n is negative then number of rotations performed from left side
		// Let's optimize the number of rotations.
		// Handle case if 'n' is a negative number.
		n = n % len;

		if (n < 0) {
			n = n + len;
		}

		return n;
	}

	public static LinkedListNode rotateList(LinkedListNode head, int n) {
		if (head == null || n == 0) {
			return head;
		}

		int len = findLength(head);

		// If n (number of rotations required) is bigger than
		// length of linked list or if n is negative then
		// we need to adjust total number of rotations needed
		n = adjustRotationsNeeded(n, len);

		if (n == 0) {
			return head;
		}

		// Find the start of rotated list.
		// If we have 1,2,3,4,5 where n = 2,
		// 4 is the start of rotated list.
		int rotationsCount = len - n - 1;
		LinkedListNode temp = head;

		while (rotationsCount > 0) {
			rotationsCount--;
			temp = temp.next;
		}

		// After the above loop temp will be pointing
		// to one node prior to rotation point
		LinkedListNode newHead = temp.next;

		// Set new end of list.
		temp.next = null;

		// Iterate to the end of list and
		// link that to original head.
		temp = newHead;
		while (temp.next != null) {
			temp = temp.next;
		}
		temp.next = head;

		return newHead;
	}

	public static void main(String[] args) {
		int[] list1 = { 1, 2, 3, 4, 5 ,6,7};
		LinkedListNode listHead = LinkedList.createLinkedList(list1);

		System.out.print("Original list: ");
		LinkedList.display(listHead);

		listHead = rotateList(listHead, 2);
		System.out.print("List rotated by 2: ");
		LinkedList.display(listHead);
	}
}