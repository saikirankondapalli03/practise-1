package com.educative.patterns.chapter13;

import java.util.*;

class ConnectRopes {

	public static int minimumCostToConnectRopes(int[] ropeLengths) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
		// add all ropes to the min heap
		for (int i = 0; i < ropeLengths.length; i++)
			minHeap.add(ropeLengths[i]);

		// go through the values of the heap, in each step take top (lowest) rope
		// lengths from the min heap
		// connect them and push the result back to the min heap.
		// keep doing this until the heap is left with only one rope
		int result = 0, temp = 0;
		while (minHeap.size() > 1) {
			int x1= minHeap.poll();
			int x2=  minHeap.poll(); 
			temp = x1+x2;
			result += temp;
			minHeap.add(temp);
		}

		return result;
	}

	public static void main(String[] args) {
		int result = ConnectRopes.minimumCostToConnectRopes(new int[] { 1,3,11 });
		System.out.println("Minimum cost to connect ropes: " + result);
		result = ConnectRopes.minimumCostToConnectRopes(new int[] { 3, 4, 5, 6 });
		System.out.println("Minimum cost to connect ropes: " + result);
		result = ConnectRopes.minimumCostToConnectRopes(new int[] { 1, 3, 11, 5, 2 });
		System.out.println("Minimum cost to connect ropes: " + result);
	}
}
