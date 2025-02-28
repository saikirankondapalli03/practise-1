package com.educative.patterns.chapter1.window.pattern1.constantwindow;

public class CountNumberSubArraysGivenSum {

    // Function to find the number of subarrays with exactly sum S
    public int numSubarraysWithSum(int[] A, int S) {
        return atMost(A, S) - atMost(A, S - 1);
    }

    // Helper function to find the number of subarrays with at most sum S
    private int atMost(int[] A, int S) {
        if (S < 0) return 0;
        int res = 0, i = 0;

        // Sliding window approach to count subarrays
        for (int j = 0; j < A.length; j++) {
        // Include A[j] in the current window
            S -= A[j];  
            while (S < 0) {
            // Shrink the window if the sum exceeds S
                S += A[i++];  
            }
            // Count all subarrays ending at j
            res += j - i + 1; 
        }

        return res;
    }

    public static void main(String[] args) {
    	CountNumberSubArraysGivenSum sol = new CountNumberSubArraysGivenSum();
        int[] A = {1, 0, 1, 0, 1}; // Example array
        int S = 2; // Desired sum
        int result = sol.numSubarraysWithSum(A, S);
        System.out.println("Number of subarrays with sum " + S + ": " + result);
    }
}
