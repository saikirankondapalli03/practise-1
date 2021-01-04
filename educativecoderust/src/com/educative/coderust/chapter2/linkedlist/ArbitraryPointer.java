package com.educative.coderust.chapter2.linkedlist;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

class ArbitraryPointer {
	public static LinkedListNode deepCopyArbitraryPointer(LinkedListNode head) {

		if (head == null) {
			return null;
		}

		LinkedListNode current = head;
		LinkedListNode newHead = null;
		LinkedListNode newPrev = null;
		Hashtable<LinkedListNode, LinkedListNode> map = new Hashtable<LinkedListNode, LinkedListNode>();

		// create copy of the linked list, recording the corresponding
		// nodes in hashmap without updating arbitrary pointer
		while (current != null) {
			LinkedListNode newNode = new LinkedListNode(current.data);

			// copy the old arbitrary pointer in the new node
			newNode.arbitraryPointer = current.arbitraryPointer;

			if (newPrev != null) {
				newPrev.next = newNode;
			} else {
				newHead = newNode;
			}

			map.put(current, newNode);

			newPrev = newNode;
			current = current.next;
		}

		LinkedListNode newCurrent = newHead;

		// updating arbitraryPointer
		while (newCurrent != null) {
			if (newCurrent.arbitraryPointer != null) {
				LinkedListNode node = map.get(newCurrent.arbitraryPointer);

				newCurrent.arbitraryPointer = node;
			}

			newCurrent = newCurrent.next;
		}

		return newHead;
	}

	public static LinkedListNode deepCopyArbitraryPointerWithDuplicate(LinkedListNode head)
	{
		if (head == null) 
		{
			return null;
		}

		LinkedListNode current = head;

		// inserting new nodes within the existing linkedlist
		while (current != null) {
			LinkedListNode newNode = new LinkedListNode(current.data);
			newNode.next = current.next;
			current.next = newNode;
			current = newNode.next;
		}

		// setting correct arbitrary pointers
		current = head;
		while (current != null) 
		{
			if (current.arbitraryPointer != null) 
			{
				current.next.arbitraryPointer = current.arbitraryPointer.next;
			}
			current = current.next.next;
		}
		// separating lists
		current = head;
		LinkedListNode newHead = head.next;
		LinkedListNode copiedCurrent = head.next;

		while (current != null) {
			copiedCurrent = current.next;
			current.next = copiedCurrent.next;
			if (copiedCurrent.next != null) {
				copiedCurrent.next = copiedCurrent.next.next;
			}
			current = current.next;
		}

		return newHead;
	}

	public static LinkedListNode createLinkedListWithArbPointers(int length) {
		LinkedListNode head = LinkedList.createRandomList(length);
		ArrayList<LinkedListNode> v = new ArrayList<>();
		LinkedListNode temp = head;
		while (temp != null) {
			v.add(temp);
			temp = temp.next;
		}

		Random generator = new Random();
		for (int i = 0; i < v.size(); i++) {

			int j = (generator.nextInt((v.size() - 1)));
			int p = generator.nextInt(100);
			if (p < 75) {
				v.get(i).arbitraryPointer = v.get(j);
			}
		}
		return head;
	}

	public static String printWithArbPointers(LinkedListNode head) {
		String printedResult = "";
		while (head != null) {
			String temp = "";
			printedResult += head.data;
			if (head.arbitraryPointer != null) {
				temp += head.arbitraryPointer.data;
			}
			printedResult += " (" + temp + ")";
			head = head.next;
			if (head != null)
				printedResult += ", ";
		}
		return printedResult;
	}

	public static void main(String[] args) {
		// LinkedListNode head = createLinkedListWithArbPointers(5);

		LinkedListNode start = new LinkedListNode(7);
		start.next = new LinkedListNode(14);
		start.next.next = new LinkedListNode(21);
		// start.next.next.next = new LinkedListNode(4);
		// start.next.next.next.next = new LinkedListNode(5);

		// 1's random points to 3
		start.arbitraryPointer = start.next.next;

		// 2's random points to 1
		start.next.arbitraryPointer = null;

		// 3's and 4's random points to 5
		start.next.next.arbitraryPointer = start;

		System.out.println("Original list : ");
		// print(start);

		LinkedListNode head2 = deepCopyArbitraryPointer(start);

		System.out.print("Original list: " + printWithArbPointers(start));

		System.out.print("\nDeep copied list: " + printWithArbPointers(head2));

		// head = createLinkedListWithArbPointers(3);

		// System.out.print("\nChanged original list: " + printWithArbPointers(head));

		// System.out.print("\nUnchanged deep copied list: " +
		// printWithArbPointers(head2));
	}
}