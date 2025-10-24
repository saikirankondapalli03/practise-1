package com.patterns.maxminheapcombo;

import java.util.Collections;
import java.util.PriorityQueue;

public class MaxMinHeapTemplate {
    
    // Template for Max-Min Heap Combination Problems
    public static void solveTemplate() {
        
        // Step 1: Two heaps for different purposes
        PriorityQueue<Integer> filterHeap = new PriorityQueue<>();  // Min-heap for filtering
        PriorityQueue<Integer> optimizeHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap for optimization
        
        // Step 2: Initialize - usually put everything in filter heap
        // for (item : allItems) {
        //     filterHeap.add(item);
        // }
        
        // Step 3: Main processing loop
        // while (hasMoreWork) {
        //     
        //     // Move eligible items from filter to optimize heap
        //     while (!filterHeap.isEmpty() && meetsFilterCriteria(filterHeap.peek())) {
        //         optimizeHeap.add(filterHeap.poll());
        //     }
        //     
        //     // If no eligible items, break or handle
        //     if (optimizeHeap.isEmpty()) {
        //         break;
        //     }
        //     
        //     // Pick optimal item and process
        //     int bestItem = optimizeHeap.poll();
        //     processItem(bestItem);
        // }
    }
    
    // Example 1: IPO Problem
    public static int maximizeCapital(int[] capital, int[] profits, int maxProjects, int money) {
        PriorityQueue<Integer> cantAfford = new PriorityQueue<>((a, b) -> capital[a] - capital[b]);
        PriorityQueue<Integer> canAfford = new PriorityQueue<>((a, b) -> profits[b] - profits[a]);
        
        for (int i = 0; i < capital.length; i++) cantAfford.add(i);
        
        for (int i = 0; i < maxProjects; i++) {
            while (!cantAfford.isEmpty() && capital[cantAfford.peek()] <= money) {
                canAfford.add(cantAfford.poll());
            }
            if (canAfford.isEmpty()) break;
            money += profits[canAfford.poll()];
        }
        return money;
    }
    
    // Example 2: Find Median from Data Stream
    static class MedianFinder {
        PriorityQueue<Integer> smallHalf = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap
        PriorityQueue<Integer> largeHalf = new PriorityQueue<>(); // Min-heap
        
        public void addNum(int num) {
            if (smallHalf.isEmpty() || num <= smallHalf.peek()) {
                smallHalf.add(num);
            } else {
                largeHalf.add(num);
            }
            
            // Balance heaps
            if (smallHalf.size() > largeHalf.size() + 1) {
                largeHalf.add(smallHalf.poll());
            } else if (largeHalf.size() > smallHalf.size() + 1) {
                smallHalf.add(largeHalf.poll());
            }
        }
        
        public double findMedian() {
            if (smallHalf.size() == largeHalf.size()) {
                return (smallHalf.peek() + largeHalf.peek()) / 2.0;
            }
            return smallHalf.size() > largeHalf.size() ? smallHalf.peek() : largeHalf.peek();
        }
    }
    
    public static void main(String[] args) {
        // Test IPO
        int result = maximizeCapital(new int[]{2, 4, 6}, new int[]{3, 8, 10}, 2, 5);
        System.out.println("Max capital: " + result);
        
        // Test Median Finder
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        System.out.println("Median: " + mf.findMedian()); // 1.5
        mf.addNum(3);
        System.out.println("Median: " + mf.findMedian()); // 2.0
    }
}