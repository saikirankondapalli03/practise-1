package patterns.slidingwindow.pattern1.constantwindow;

import java.util.*;

public class MinSizeSubArraySum2 {
	public static int minSubArrayLen(int targetSum, int[] nums) {
	    // Track the minimum length found so far
	    int minWindowLength = Integer.MAX_VALUE;

	    // Maintain the current running sum within the window
	    int currentWindowSum = 0;

	    // Left boundary of the sliding window
	    int windowStart = 0;

	    // Expand the window by moving windowEnd
	    for (int windowEnd = 0; windowEnd < nums.length; windowEnd++) {

	        // Add the current number to the window sum
	        currentWindowSum += nums[windowEnd];

	        // While current window satisfies target condition,
	        // try shrinking it from the left to find smaller valid window
	        while (currentWindowSum >= targetSum) {
	            int currentWindowLength = windowEnd - windowStart + 1;
	            minWindowLength = Math.min(minWindowLength, currentWindowLength);

	            // Shrink the window from the left
	            currentWindowSum -= nums[windowStart];
	            windowStart++;
	        }
	    }

	    // If no valid subarray found, return 0
	    return (minWindowLength == Integer.MAX_VALUE) ? 0 : minWindowLength;
	}
	// Driver code
	public static void main(String[] args) {
		int[] target = { 7, 4, 11, 10, 5, 15 };
		int[][] inputArr = { { 2, 3, 1, 2, 4, 3 }, { 1, 4, 4 }, { 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 2, 3, 4 },
				{ 1, 2, 1, 3 }, { 5, 4, 9, 8, 11, 3, 7, 12, 15, 44 } };
		for (int i = 0; i < target.length; i++) {
			int windowSize = minSubArrayLen(target[i], inputArr[i]);
			System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(inputArr[i]));
			System.out.print("\n\tTarget: " + target[i]);
			System.out.println("\n\tMinimum Length of Subarray: " + windowSize);
			System.out.println(new String(new char[100]).replace('\0', '-'));
		}
	}
}