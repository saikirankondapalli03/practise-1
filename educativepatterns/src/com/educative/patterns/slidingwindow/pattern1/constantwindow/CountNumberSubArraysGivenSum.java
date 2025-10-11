package com.educative.patterns.slidingwindow.pattern1.constantwindow;


//"Given an array of binary integers (0s and 1s) and an integer S,
//write a function to count the number of contiguous subarrays that sum to exactly S.

//Completed: GOOD
public class CountNumberSubArraysGivenSum {

	
	/*
	 *
	 * This creates a recursion tree where:
	   The first call may process n elements
	   The second call may process n-1 elements
	   The third call may process n-2 elements
	   And so on...
	   This forms an arithmetic series: n + (n-1) + (n-2) + ... + 2 + 1
	   The sum of this series is n(n+1)/2, which simplifies to O(n^2).
	 */
    public int numSubarraysWithSumRecursive(int[] A, int S) {
        return countAtMostRecursive(A, S, 0) - countAtMostRecursive(A, S - 1, 0);
    }

    private int countAtMostRecursive(int[] A, int S, int start) {
        // Base cases
        if (start >= A.length) return 0;
        if (S < 0) return 0;

        // Count subarrays starting from this position
        int count = 0;
        int sum = 0;
        for (int i = start; i < A.length; i++) {
            sum += A[i];
            if (sum <= S) {
                count++;
            } else {
                break;
            }
        }

        // Add count of subarrays starting from next position
        return count + countAtMostRecursive(A, S, start + 1);
    }
    
    // Function to find the number of subarrays with exactly sum S
    // TC : o(n)
    public int numSubarraysWithSum(int[] A, int S) {
    	int atMostS= atMost(A, S);
    	int atMostSMinus1 = atMost(A, S - 1); 
        return atMostS - atMostSMinus1;
    }

    private int atMost(int[] A, int S) {
        // If S is negative, no valid subarrays exist
        if (S < 0) return 0;

        int res = 0;  // Variable to store the total count of valid subarrays
        int i = 0;    // Left pointer of the sliding window

        // Sliding window approach to count subarrays
        for (int j = 0; j < A.length; j++) {
            // Include A[j] in the current window by subtracting it from S
            // S now represents the remaining sum we can accommodate
            S -= A[j];  

            // If S becomes negative, we need to shrink the window from the left
            while (S < 0) {
                // Add back the leftmost element of the window to S
                // and move the left pointer (i) to the right
                S += A[i++];  
            }

            // At this point, the subarray A[i] to A[j] has sum <= original S
            // Count all subarrays ending at index j:
            // There are (j - i + 1) such subarrays, namely:
            // A[j], A[j-1]...A[j], A[j-2]...A[j], ..., A[i]...A[j]
            res += j - i + 1; 
        }

        // Return the total count of subarrays with sum <= S
        return res;
    }
    
    public static void main(String[] args) {
    	CountNumberSubArraysGivenSum sol = new CountNumberSubArraysGivenSum();
        int[] A = {1, 1, 0, 1, 1,1,0,1}; // Example array
        
        // 1
        //1
        //0
        //1
        //11
        //10
        //01
        //110
        //101
        
        int S = 4; // Desired sum
        /* 
         *
         * a) (indices 0-2)
		   b) (indices 2-4)
           c) (indices 0-3)
           d) (indices 1-4)
         */
        int result = sol.numSubarraysWithSum(A, S);
        System.out.println("Number of subarrays with sum " + S + ": " + result);
    }
}
