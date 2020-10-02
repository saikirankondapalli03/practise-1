package com.educative.tc.chapter4;

@SuppressWarnings("unchecked")
public class DoublyLinkedList<T> {

	private ListNode<T> front;
	private ListNode<T> end;
	private int size;

	public DoublyLinkedList() {
		end = front = null;
	}

	public ListNode<T> addFront(T x) {
		ListNode<T> retVal;
		if (size == 0) {
			front = new ListNode<>(x);
			end = front;
			retVal = front;
		} else {
			ListNode<T> newNode = new ListNode<>(null, x, null);
			newNode.next = front;
			front.prev = newNode;
			front = newNode;
			retVal = newNode;
		}
		size++;
		return retVal;
	}

	ListNode<T> removeLast() {
		ListNode<T> item = end;
		end = end.prev;
		size--;
		return item;
	}

	public void removeNode(ListNode node) {
		if (size == 0) {
			return;
		}

		if (size == 1) {
			end = front = node = null;
		} else {
			ListNode prev = node.prev;
			ListNode next = node.next;

			if (prev != null)
				prev.next = next;

			if (next != null)
				next.prev = prev;

			node = null;
		}

		size--;
	}

	public int size() {
		return size;
	}
}
