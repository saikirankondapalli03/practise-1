package com.educative.tc.chapter3;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NDimensionalList {
	public static void main(String args[]) throws Exception {
		// 1D list
		NDimensionalList list = new NDimensionalList(1);
		list.put(0, -10);
		System.out.println(list.get(0));
		System.out.println();

		// 2D list
		list = new NDimensionalList(2);
		list.put(0, -3);
		System.out.println(list.get(0));
		list.put(3, 5);
		System.out.println(list.get(3));
		System.out.println();

		// 3D list
		list = new NDimensionalList(3);
		list.put(25, -100);
		System.out.println(list.get(25));
		list.put(0, 21);
		System.out.println(list.get(0));
		list.put(26, 211);
		System.out.println(list.get(26));
		System.out.println();

		// 5D list
		list = new NDimensionalList(5);
		list.put(124, 313);
		System.out.println(list.get(124));
		System.out.println();

	}

	int n;
	int maxIndex;
	List<Object> superList;

	public NDimensionalList(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();

		this.n = n;
		this.maxIndex = (int) Math.pow(n, n);
		allocate(n, superList);
	}

	// Recursive method that initializes the resulting N-Dimensional List
	private void allocate(int rem, List<Object> list) {

		if (rem == -1)
			return;

		if (superList == null) {
			superList = list = new LinkedList<Object>();
			allocate(rem - 1, list);
		} else {
			for (int i = 0; i < n; i++) {
				List<Object> subList = new LinkedList<Object>();
				list.add(i, subList);
				allocate(rem - 1, subList);
			}
		}
	}

	// Calculates the index to the right node
	private Stack<Integer> getIndices(int index) {
		Stack<Integer> stack = new Stack<Integer>();

		for (int i = 0; i < n; i++) {
			stack.push(index % n);
			index = index / n;
		}

		return stack;
	}

	@SuppressWarnings("unchecked")
	public Integer get(int i) throws Exception {

		if (i < 0 || i >= maxIndex)
			throw new IndexOutOfBoundsException();

		Stack<Integer> stack = getIndices(i);
		List<Object> temp = superList;

		while (!stack.isEmpty()) {
			temp = (List<Object>) temp.get(stack.pop());
		}

		return (Integer) (temp.get(0));
	}

	@SuppressWarnings("unchecked")
	public void put(int i, int num) {
		if (i < 0 || i >= maxIndex)
			throw new IndexOutOfBoundsException();

		Stack<Integer> stack = getIndices(i);
		List<Object> temp = superList;

		while (!stack.isEmpty()) {
			temp = (List<Object>) temp.get(stack.pop());
		}

		temp.add(0, num);
	}
}
