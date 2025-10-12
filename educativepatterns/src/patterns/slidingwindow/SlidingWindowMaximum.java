package patterns.slidingwindow;

import java.util.*;
import java.util.stream.*;

class SlidingWindowMaximum {
	// function to clean up the window
	public static Deque<Integer> cleanUp(int i, Deque<Integer> currentWindow, int[] nums) {
		while (currentWindow.size() != 0 && nums[i] >= nums[currentWindow.getLast()]) {
			currentWindow.removeLast();
		}
		return currentWindow;
	}
	
	public static int[] findMaxInWindows(int[] nums, int w) {
        if (nums == null || nums.length == 0 || w <= 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - w + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            // Remove elements outside the current window
            if (!deque.isEmpty() && deque.peekFirst() == i - w) {
                deque.pollFirst();
            }
            
            // Remove smaller elements from the rear
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            // Start storing results when we hit the first window
            if (i >= w - 1) {
                result[i - w + 1] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }


	// function to find the maximum in all possible windows
	public static int[] findMaxSlidingWindow(int[] nums, int w) {
		if (nums.length == 1) {
			return nums;
		}
		int [] output = new int[nums.length - w + 1];
		Deque<Integer> currentWindow = new ArrayDeque<>();
		for (int i = 0; i < w; i++) {
        	currentWindow = SlidingWindowMaximum.cleanUp(i, currentWindow, nums);
			currentWindow.add(i);
		}
		output[0] = nums[currentWindow.getFirst()];
		for (int i = w; i < nums.length; i++) {
			cleanUp(i, currentWindow, nums);
			if (!currentWindow.isEmpty() && currentWindow.getFirst() <= (i - w)) {
				currentWindow.removeFirst();
			}
			currentWindow.add(i);
			output[i - w + 1] = nums[currentWindow.getFirst()];
		}
		return output;
	}
	
	// driver code
	public static void main(String args[]) {
		
		  int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
	        int w = 3;
	        int[] maxInWindows = findMaxInWindows(nums, w);
	        System.out.println(Arrays.toString(maxInWindows));
	        
	        
		int windowSizes [] = {3, 3, 3, 3, 2, 4, 3, 2, 3, 6};
		int [][] numLists = {
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
		    {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
			{10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
			{1, 5, 8, 10, 10, 10, 12, 14, 15, 19, 19, 19, 17, 14, 13, 12, 12, 12, 14, 18, 22, 26, 26, 26, 28, 29, 30},
			{10, 6, 9, -3, 23, -1, 34, 56, 67, -1, -4, -8, -2, 9, 10, 34, 67},
			{4, 5, 6, 1, 2, 3},
			{9, 5, 3, 1, 6, 3},
			{2, 4, 6, 8, 10, 12, 14, 16},
			{-1, -1, -2, -4, -6, -7},
			{4, 4, 4, 4, 4, 4}
		};

		for (int i = 0; i < numLists.length; i++) {
			System.out.println(i + 1 + ".\tInput array:\t" + Arrays.toString(numLists[i]));
			System.out.println("\tWindow size:\t" + windowSizes[i]);
			System.out.println("\n\tMaximum in each sliding window:\t" + Arrays.toString(findMaxSlidingWindow(numLists[i], windowSizes[i])));
            Stream.generate(() -> "-").limit(100).forEach(System.out::print);
			System.out.println();
		}
	}
}