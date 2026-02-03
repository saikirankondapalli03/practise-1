package fidelity_prep.day2;

import java.util.*;

/**
 * Problem 4: Kth Largest Element in an Array
 * LeetCode: 215. Kth Largest Element in an Array
 * 
 * Pattern: Heap (Priority Queue)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a HEAP problem because:
 * - We need to find kth largest (optimization)
 * - We can use min-heap of size k
 * - Heap gives us O(log k) insertion and O(1) access to min
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find the kth largest element
 * 
 * 2. Heap approach:
 *    - Use min-heap of size k
 *    - Keep only k largest elements
 *    - Root of heap is kth largest
 * 
 * 3. Logic:
 *    - Create min-heap
 *    - For each number:
 *        - If heap size < k: add to heap
 *        - Else if number > heap.peek(): 
 *            - Remove smallest (poll)
 *            - Add new number
 *    - Return heap.peek() (kth largest)
 * 
 * 4. Example: [3,2,1,5,6,4], k=2
 *    - Process 3: heap=[3]
 *    - Process 2: heap=[2,3]
 *    - Process 1: heap=[2,3] (1 < 2, skip)
 *    - Process 5: heap=[3,5] (5 > 2, replace)
 *    - Process 6: heap=[5,6] (6 > 3, replace)
 *    - Process 4: heap=[5,6] (4 < 5, skip)
 *    - Answer: 5 (heap.peek())
 * 
 * 5. Why min-heap?
 *    - We want to keep k largest elements
 *    - Min-heap's root is smallest of those k
 *    - That smallest is the kth largest overall
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem4_KthLargest {
    
    // TODO: Implement findKthLargest method
    public int findKthLargest(int[] nums, int k) {
        // YOUR CODE HERE
        // Think about:
        // - What type of heap? (min or max?)
        // - What size should heap be?
        // - When to add vs replace?
        
        return 0;
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem4_KthLargest solution = new Problem4_KthLargest();
        
        // Test case 1
        int result1 = solution.findKthLargest(new int[]{3,2,1,5,6,4}, 2);
        System.out.println("Test 1: " + result1); // Expected: 5
        
        // Test case 2
        int result2 = solution.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4);
        System.out.println("Test 2: " + result2); // Expected: 4
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int findKthLargest(int[] nums, int k) {
        // Min-heap to keep k largest elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                // Current number is larger than smallest in heap
                minHeap.poll(); // Remove smallest
                minHeap.offer(num); // Add current
            }
        }
        
        return minHeap.peek(); // Root is kth largest
    }
    
    Time Complexity: O(n log k) - n elements, each heap operation is O(log k)
    Space Complexity: O(k) - heap size is k
    */
}
