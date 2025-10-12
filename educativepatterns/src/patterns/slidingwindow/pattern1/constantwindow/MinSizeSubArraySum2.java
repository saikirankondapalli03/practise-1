package patterns.slidingwindow.pattern1.constantwindow;

import java.util.*;

class MinimumSubArraySum {
    public static int minSubArrayLen(int target, int[] nums) {
        // Initializing windowSize to a max number
        int windowSize = Integer.MAX_VALUE;
        int currSubArrSize = 0;
        // Initialize start pointer to 0 and sum to 0
        int start = 0;
        int sum = 0;

        // Iterate over the input array
        for (int end = 0; end < nums.length; end++) {
            sum += nums[end];
            // check if we can remove elements from the start of the subarray
            // while still satisfying the target condition
            while (sum >= target) {
                // Finding size of current subarray
                currSubArrSize = (end + 1) - start;
                windowSize = Math.min(windowSize, currSubArrSize);
                sum -= nums[start];
                start += 1;
            }
        }

        if (windowSize != Integer.MAX_VALUE) {
            return windowSize;
        }
        return 0;
    }

    // Driver code
    public static void main(String[] args) {
        int[] target = {7, 4, 11, 10, 5, 15};
        int[][] inputArr = {
            {2, 3, 1, 2, 4, 3},
            {1, 4, 4},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 3, 4},
            {1, 2, 1, 3},
            {5, 4, 9, 8, 11, 3, 7, 12, 15, 44}
        };
        for (int i = 0; i < target.length; i++) {
            int windowSize = minSubArrayLen(target[i], inputArr[i]);
            System.out.print((i + 1) + ".\tInput array: " + Arrays.toString(inputArr[i]));
            System.out.print("\n\tTarget: " + target[i]);
            System.out.println("\n\tMinimum Length of Subarray: " + windowSize);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}