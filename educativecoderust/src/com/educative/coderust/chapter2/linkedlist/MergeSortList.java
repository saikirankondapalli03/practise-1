package com.educative.coderust.chapter2.linkedlist;

class MergeSortList {
	public static LinkedListNode mergeSorted(LinkedListNode head1, LinkedListNode head2) {

		// if both lists are empty then merged list is also empty
		// if one of the lists is empty then other is the merged list
		if (head1 == null) {
			return head2;
		} else if (head2 == null) {
			return head1;
		}

		LinkedListNode mergedHead = null;
		if (head1.data <= head2.data) {
			mergedHead = head1;
			head1 = head1.next;
		} else {
			mergedHead = head2;
			head2 = head2.next;
		}

		LinkedListNode mergedTail = mergedHead;

		while (head1 != null && head2 != null) {
			LinkedListNode temp = null;
			if (head1.data <= head2.data) {
				temp = head1;
				head1 = head1.next;
			} else {
				temp = head2;
				head2 = head2.next;
			}

			mergedTail.next = temp;
			mergedTail = temp;
		}

		if (head1 != null) {
			mergedTail.next = head1;
		} else if (head2 != null) {
			mergedTail.next = head2;
		}

		return mergedHead;
	}

	public static void main(String[] args) {
		int[] arr1 = new int[] { 4, 8, 15, 19, 22 };
		int[] arr2 = new int[] { 7, 9, 10, 16 };
		LinkedListNode listHead1 = LinkedList.createLinkedList(arr1);
		System.out.print("List 1: ");
		LinkedList.display(listHead1);

		LinkedListNode listHead2 = LinkedList.createLinkedList(arr2);
		System.out.print("List 2: ");
		LinkedList.display(listHead2);

		System.out.print("Merged: ");
		LinkedListNode merge = mergeSorted(listHead1, listHead2);
		LinkedList.display(merge);
	}
}