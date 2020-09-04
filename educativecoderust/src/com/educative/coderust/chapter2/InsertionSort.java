package com.educative.coderust.chapter2;

class InsertionSort {
	public static LinkedListNode sortedInsert(LinkedListNode sorted, LinkedListNode head) {

		if (head == null) {
			return sorted;
		}

		if (sorted == null || head.data <= sorted.data) {
			head.next = sorted;
			return head;
		}

		LinkedListNode curr = sorted;

		//23-> 29 -> 82 
		while (curr.next != null && (curr.next.data < head.data)) {

			curr = curr.next;
		}

		head.next = curr.next;
		curr.next = head;

		return sorted;
	}

	public static LinkedListNode insertionSort(LinkedListNode head) {

		LinkedListNode sorted = null;
		LinkedListNode curr = head;

		while (curr != null) {

			LinkedListNode temp = curr.next;

			sorted = sortedInsert(sorted, curr);

			curr = temp;
		}

		return sorted;
	}

	public static void main(String[] args) {

		int[] list = { 29, 23, 82, 11 ,31};
		int[] listExpected = { 11, 23, 29, 31, 82 };
		LinkedListNode listHead = LinkedList.createLinkedList(list);
		LinkedListNode listHeadExpected = LinkedList.createLinkedList(listExpected);

		System.out.print("Original: ");
		LinkedList.display(listHead);

		listHead = insertionSort(listHead);
		System.out.print("After sorting: ");
		LinkedList.display(listHead);
	}
}