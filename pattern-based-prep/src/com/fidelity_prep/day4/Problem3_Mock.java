package fidelity_prep.day4;

import java.util.*;

/**
 * MOCK INTERVIEW PROBLEM 3
 * Time: 45 minutes
 * 
 * Problem: LRU Cache
 * LeetCode: 146. LRU Cache
 * Difficulty: Medium
 * 
 * ============================================
 * YOUR APPROACH (10 min - write here)
 * ============================================
 * 
 * Pattern Recognition:
 * 
 * 
 * Logical Steps:
 * 1.
 * 2.
 * 3.
 * 
 * Edge Cases:
 * 
 * 
 * Complexity:
 * Time: 
 * Space: 
 * 
 * ============================================
 * CODE (25 min)
 * ============================================
 */

class LRUCache {
    // YOUR CODE HERE
    
    public LRUCache(int capacity) {
        
    }
    
    public int get(int key) {
        return -1;
    }
    
    public void put(int key, int value) {
        
    }
}

public class Problem3_Mock {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // 1
        cache.put(3, 3);
        System.out.println(cache.get(2)); // -1
        cache.put(4, 4);
        System.out.println(cache.get(1)); // -1
        System.out.println(cache.get(3)); // 3
        System.out.println(cache.get(4)); // 4
    }
    
    // ============================================
    // SOLUTION (Check after 45 min)
    // ============================================
    /*
    Use HashMap + Doubly Linked List
    
    class Node {
        int key, val;
        Node prev, next;
    }
    
    - HashMap for O(1) access
    - Doubly linked list for O(1) add/remove
    - Move to head on get/put
    - Remove tail when capacity exceeded
    
    Time: O(1) for both operations
    Space: O(capacity)
    */
}
