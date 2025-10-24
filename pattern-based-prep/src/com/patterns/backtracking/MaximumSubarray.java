package com.patterns.backtracking;

/**
 * Maximum Subarray Problem - LeetCode 53
 * 
 * Problem: Find the contiguous subarray with the largest sum
 * 
 * Two approaches:
 * 1. Simple Recursion: O(2^n) time, O(n) space
 * 2. DP (Memoized Recursion): O(n) time, O(n) space
 * 
 * DP Recurrence: dp[i] = max(nums[i], dp[i-1] + nums[i])
 */
public class MaximumSubarray {
    
    /**
     * Simple recursive approach - explores all possibilities
     * @param nums input array
     * @return maximum subarray sum
     */
    public static int maxSubArrayRecursive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSum = Integer.MIN_VALUE;
        
        // Try each position as potential end of max subarray
        for (int i = 0; i < nums.length; i++) {
            maxSum = Math.max(maxSum, maxEndingAt(nums, i));
        }
        
        return maxSum;
    }
    
    /**
     * Recursive helper: max sum of subarray ending at index i
     */
    private static int maxEndingAt(int[] nums, int i) {
        // Base case: first element
        if (i == 0) {
            return nums[0];
        }
        
        // Recurrence: extend previous subarray OR start fresh
        return Math.max(nums[i], maxEndingAt(nums, i - 1) + nums[i]);
    }
    
    /**
     * Pure DP approach - bottom-up iterative
     * @param nums input array
     * @return maximum subarray sum
     */
    public static int maxSubArrayDP(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // DP array: dp[i] = max sum ending at index i
        int[] dp = new int[nums.length];
        
        // Base case
        dp[0] = nums[0];
        int maxSum = dp[0];
        
        // Fill DP table using recurrence relation
        for (int i = 1; i < nums.length; i++) {
            // dp[i] = max(nums[i], dp[i-1] + nums[i])
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            
            // Track global maximum
            maxSum = Math.max(maxSum, dp[i]);
        }
        
        return maxSum;
    }
    
    
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Recursive: " + maxSubArrayRecursive(nums));
        System.out.println("DP (Bottom-up): " + maxSubArrayDP(nums));
        
        System.out.println();
        System.out.println("Recursive: O(2^n) time, O(n) space");
        System.out.println("DP: O(n) time, O(n) space");
    }
}