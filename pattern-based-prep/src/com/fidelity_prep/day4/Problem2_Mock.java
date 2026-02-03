package fidelity_prep.day4;

import java.util.*;

/**
 * MOCK INTERVIEW PROBLEM 2
 * Time: 45 minutes
 * 
 * Problem: Longest Consecutive Sequence
 * LeetCode: 128. Longest Consecutive Sequence
 * Difficulty: Medium
 * 
 * ============================================
 * YOUR APPROACH (10 min - write here)
 * ============================================
 * 
 * Pattern Recognition:
 * 
 * 
 * Logical Steps:
 * 1.
 * 2.
 * 3.
 * 
 * Edge Cases:
 * 
 * 
 * Complexity:
 * Time: 
 * Space: 
 * 
 * ============================================
 * CODE (25 min)
 * ============================================
 */

public class Problem2_Mock {
    public int longestConsecutive(int[] nums) {
        // YOUR CODE HERE
        
        return 0;
    }
    
    // Test cases
    public static void main(String[] args) {
        Problem2_Mock solution = new Problem2_Mock();
        
        int[] input1 = {100,4,200,1,3,2};
        System.out.println(solution.longestConsecutive(input1)); // Expected: 4
        
        int[] input2 = {0,3,7,2,5,8,4,6,0,1};
        System.out.println(solution.longestConsecutive(input2)); // Expected: 9
    }
    
    // ============================================
    // SOLUTION (Check after 45 min)
    // ============================================
    /*
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longest = 0;
        
        for (int num : numSet) {
            // Only start counting from beginning of sequence
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                
                longest = Math.max(longest, currentLength);
            }
        }
        
        return longest;
    }
    
    Time: O(n) - each number visited at most twice
    Space: O(n) - hash set
    */
}
