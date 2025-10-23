package patterns.slidingwindow.pattern1.constantwindow;


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

    private int atMost(int[] nums, int maxAllowedSum) {
        // No valid subarrays possible if target sum is negative
        if (maxAllowedSum < 0) return 0;

        int totalValidSubarrays = 0;   // Total count of valid subarrays
        int windowStart = 0;           // Left boundary of the sliding window

        // Iterate with the right boundary of the window
        for (int windowEnd = 0; windowEnd < nums.length; windowEnd++) {

            // Include the current element in the window
            maxAllowedSum -= nums[windowEnd];

            // Shrink the window from the left until the total sum <= allowed sum
            while (maxAllowedSum < 0) {
                maxAllowedSum += nums[windowStart];
                windowStart++;
            }

            // Every subarray ending at 'windowEnd' and starting between
            // 'windowStart' and 'windowEnd' (inclusive) has sum <= original maxAllowedSum
            totalValidSubarrays += windowEnd - windowStart + 1;
        }

        // Return total count of subarrays with sum <= maxAllowedSum
        return totalValidSubarrays;
    }
    
    
    int atMost1(int[] nums, int K) {
        if (K < 0) return 0;         // base case
        int left = 0, count = 0, sum = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];       // expand window

            while (sum > K) {         // shrink until valid
                sum -= nums[left];
                left++;
            }

            count += right - left + 1; // all subarrays ending at 'right'
        }
        return count;
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
