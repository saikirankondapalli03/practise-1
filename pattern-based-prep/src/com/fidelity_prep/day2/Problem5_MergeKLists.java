package fidelity_prep.day2;

import java.util.*;

/**
 * Problem 5: Merge K Sorted Lists
 * LeetCode: 23. Merge K Sorted Lists
 * 
 * Pattern: Heap + Linked Lists
 * Difficulty: Hard
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Need to merge k sorted lists into one
 * 
 * 2. Heap approach:
 *    - Use min-heap to always get smallest element
 *    - Add head of each list to heap
 *    - Pop smallest, add to result, push its next
 * 
 * 3. Logic:
 *    - Create min-heap with comparator on node.val
 *    - Add all list heads to heap
 *    - While heap not empty:
 *        - Pop smallest node
 *        - Add to result list
 *        - If node.next exists, add to heap
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class Problem5_MergeKLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        for (ListNode node : lists) {
            if (node != null) minHeap.offer(node);
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (!minHeap.isEmpty()) {
            ListNode smallest = minHeap.poll();
            current.next = smallest;
            current = current.next;
            
            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }
        
        return dummy.next;
    }
}
