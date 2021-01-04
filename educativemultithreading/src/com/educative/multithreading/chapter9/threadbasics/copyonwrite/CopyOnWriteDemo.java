package com.educative.multithreading.chapter9.threadbasics.copyonwrite;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;

/**
 * Java program to illustrate CopyOnWriteArrayList
 */
public class CopyOnWriteDemo {
	public static void main(String[] args) throws InterruptedException {
		// Initializing a regular Arraylist
		ArrayList<Integer> array_list = new ArrayList<>();
		array_list.ensureCapacity(500000);
		// Initializing a new CopyOnWrite Arraylist with 500,000 numbers
		CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(array_list);

		// Calculating the time it takes to add a number in CopyOnWrite Arraylist
		long startTime = System.nanoTime();
		numbers.add(500001);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);

		// Calculating the time it takes to add a number in regular Arraylist
		long startTime_al = System.nanoTime();
		array_list.add(500001);
		long endTime_al = System.nanoTime();
		long duration_al = (endTime_al - startTime_al);

		System.out.println("Time taken by a regular arraylist: " + duration_al + " nano seconds");
		System.out.println("Time taken by a CopyOnWrite arraylist: " + duration + " nano seconds");

	}
}