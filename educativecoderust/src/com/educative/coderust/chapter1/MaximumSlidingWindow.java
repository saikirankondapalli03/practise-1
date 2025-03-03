package com.educative.coderust.chapter1;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

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
	  
	  
	public static ArrayDeque<Integer> findMaxSlidingWindow(int[] arr, int windowSize) {

		ArrayDeque<Integer> result = new ArrayDeque<>(); // ArrayDeque for storing values
		Deque<Integer> list = new LinkedList<Integer>(); // creating a linked list

		if (arr.length > 0) {

			if (arr.length < windowSize) // Invalid State
				return result;
			
			// find the first window maximum value and put it into the result
			for (int i = 0; i < windowSize; ++i) {
				// Removing last smallest element index
				while (!list.isEmpty() && arr[i] >= arr[list.peekLast()]) {
					list.removeLast();
				}

				// Adding newly picked element index
				list.addLast(i);
			}

			// for rest of the window to the end of the array , do these steps
			// exclude
			for (int i = windowSize; i < arr.length; ++i) {
				result.add(arr[list.peek()]);

				// Removing all the elements indexes which are not in the current window
				while ((!list.isEmpty()) && list.peek() <= i - windowSize)
					list.removeFirst();

				// Removing the smaller elements indexes which are not required
				while ((!list.isEmpty()) && arr[i] >= arr[list.peekLast()])
					list.removeLast();

				// Adding newly picked element index
				list.addLast(i);
			}

			// Adding the max number of the current window in the result
			result.add(arr[list.peek()]);
			return result; // returning result
		} else
			return result;
	}

	public static void main(String[] args) {

		int[] array = { 3,1, 2, 4, 5, 6, 7, 8, 9, 10 };
		System.out.println("Array = " + Arrays.toString(array));
		System.out.println("Max = " + findMaxSlidingWindow(array, 3));

		int[] array2 = { 10, 6, 9, -3, 23, -1, 34, 56, 67, -1, -4, -8, -2, 9, 10, 34, 67 };
		System.out.println("Array = " + Arrays.toString(array2));
		System.out.println("Max = " + findMaxSlidingWindow(array2, 3));
	}
}
