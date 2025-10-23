/*
package com.patterns.slidingwindow.pattern1fixedsize;

import java.util.*;

/**
 * Pattern 1: Fixed Size Sliding Window
 * Use Case: Know exact window size, process each window
 * Time: O(n), Space: O(1)
 */
/*
public class FixedSizeTemplate {
    
    // Basic fixed window template
    public double[] averageOfSubarrays(int[] arr, int k) {
        double[] result = new double[arr.length - k + 1];
        double windowSum = 0;
        
        // Calculate first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        result[0] = windowSum / k;
        
        // Slide window: remove first, add next
        for (int i = k; i < arr.length; i++) {
            windowSum = windowSum - arr[i - k] + arr[i];
            result[i - k + 1] = windowSum / k;
        }
        return result;
    }
    
    // Maximum in sliding window template
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices
        int[] result = new int[nums.length - k + 1];
        
        for (int i = 0; i < nums.length; i++) {
            // Remove indices outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove smaller elements (maintain decreasing order)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            // Add to result when window is complete
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}

*/
