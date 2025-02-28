package com.educative.patterns.chapter1.window.pattern1.constantwindow;


//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/pattern-and-template/theory
public class LongestSubarraySum {

    // Function to find the longest subarray with sum <= K
    public static int longestSubarrayWithSum(int[] arr, int K) {
        int n = arr.length;
        int maxLength = 0; // Initialize the maximum length to 0
        int sum = 0;       // Initialize the current sum to 0
        int left = 0;      // Left pointer for the sliding window

        // Iterate over the array using the right pointer
        for (int right = 0; right < n; right++) {
            sum += arr[right]; // Add the current element to the sum

            // Shrink the window from the left if sum exceeds K
            while (sum > K) {
                sum -= arr[left]; // Subtract the leftmost element from the sum
                left++;           // Move the left pointer to the right
            }

            // Update the maximum length if the current window is valid
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength; // Return the maximum length of the valid subarray
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 7, 10}; // Example array
        int K = 14; // Example value of K

        // Find and print the length of the longest subarray with sum <= K
        int result = longestSubarrayWithSum(arr, K);
        System.out.println("The longest subarray length with sum <= " + K + " is: " + result);
    }
}
