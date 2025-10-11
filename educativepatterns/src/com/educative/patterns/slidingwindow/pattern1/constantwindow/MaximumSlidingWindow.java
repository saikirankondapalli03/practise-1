package com.educative.patterns.slidingwindow.pattern1.constantwindow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MaximumSlidingWindow {
	
	  public static int[] maxSlidingWindow(int[] nums, int k) {
	        if (nums == null || nums.length == 0 || k == 0) {
	            return new int[0];
	        }

	        int n = nums.length;
	        int[] leftMax = new int[n];
	        int[] rightMax = new int[n];
	        int[] result = new int[n - k + 1];

	        // Compute leftMax array
	        for (int i = 0; i < n; i++) {
	            if (i % k == 0) {
	                leftMax[i] = nums[i];
	            } else {
	                leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
	            }
	        }

	        // Compute rightMax array
	        for (int i = n - 1; i >= 0; i--) {
	            if (i % k == 0 || i == n - 1) {
	                rightMax[i] = nums[i];
	            } else {
	                rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
	            }
	        }

	        // Compute sliding window maximum
	        for (int i = 0; i <= n - k; i++) {
	            result[i] = Math.max(rightMax[i], leftMax[i + k - 1]);
	        }

	        return result;
	    }
	  
	  
	  
	  public static int[] maxSlidingWindowSimple(int[] nums, int k) {
		    if (nums == null || nums.length == 0 || k == 0) {
		        return new int[0];
		    }

		    int n = nums.length;
		    int[] result = new int[n - k + 1];
		    int resultIndex = 0;

		    for (int i = 0; i <= n - k; i++) {
		        int maxVal = nums[i];
		        for (int j = i; j < i + k; j++) {
		            maxVal = Math.max(maxVal, nums[j]);
		        }
		        result[resultIndex++] = maxVal;
		    }

		    return result;
		}
	  
	  

	  public static int[] findMaxSlidingWindow(int[] nums, int k) {
		    // Handle edge cases
		    if (nums == null || nums.length == 0 || k <= 0) {
		        return new int[0];
		    }
		    
		    //[3, 1, 2, 4, 5, 6, 7, 8, 9, 10]
		    int n = nums.length;
		    // Create result array to store maximum of each window
		    int[] result = new int[n - k + 1];
		    int j = 0; // Index for result array
		    
		    // Create a deque to store indices of potential maximum elements
		    Deque<Integer> deque = new ArrayDeque<>();
		    
		    // Iterate through the array
		    for (int i = 0; i < n; i++) {
	        	// This checks if the element at the front of the deque
		    	// is outside the current window. If it is, we remove it.

		        // Remove indices that are out of the current window
		        if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
		            deque.pollFirst();
		        }
		        
		        // Remove indices of all smaller elements
		        //This removes elements smaller than the current element 
		        //from the back of the deque. 
		        //These elements can't be the maximum in future windows.
		        
		        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
		            deque.pollLast();
		        }
		        
		        // Add current element's index
		        deque.offerLast(i);
		        
		        // If we have a valid window, add to result
		        if (i >= k - 1) {
		            result[j++] = nums[deque.peekFirst()];
		        }
		    }
		    
		    return result;
		}


	
	public static int[] maxSlidingWindowUsingHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int i = 0; i < n; i++) {
            // Remove elements outside the current window
            if (i >= k && maxHeap.contains(nums[i - k])) {
                maxHeap.remove(nums[i - k]);
            }
            
            // Add current element to the heap
            maxHeap.offer(nums[i]);
            
            // If we have a valid window, add max to result
            if (i >= k - 1) {
                result[index++] = maxHeap.peek();
            }
        }
        
        return result;
    }



	public static void main(String[] args) {

		int[] array = { 3,1, 2, 4, 5, 6, 7, 8, 9, 10 };
		System.out.println("Array = " + Arrays.toString(array));
		System.out.println("Max = " + Arrays.toString(findMaxSlidingWindow(array, 3)));

		int[] array2 = { 10, 6, 9, -3, 23, -1, 34, 56, 67, -1, -4, -8, -2, 9, 10, 34, 67 };

		System.out.println("Array = " + Arrays.toString(array2));
		System.out.println("Max = " + findMaxSlidingWindow(array2, 3));
		int[] result= MaximumSlidingWindow.maxSlidingWindowUsingHeap(array2, 3);
		System.out.println("Array = " + Arrays.toString(result));

	}
}
