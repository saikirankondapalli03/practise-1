package fidelity_prep.day1;

import java.util.*;

/**
 * Problem 5: 3Sum
 * LeetCode: 15. 3Sum
 * 
 * Pattern: Two Pointers (Extension to Three Sum)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a TWO POINTERS problem because:
 * - We can sort the array first
 * - For each element, we can use two pointers for the remaining two
 * - Similar to two sum but with one more dimension
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find all unique triplets that sum to zero
 * 
 * 2. Approach:
 *    - Sort the array first
 *    - For each element at index i, find two elements that sum to -nums[i]
 *    - Use two pointers: left = i+1, right = length-1
 *    - This reduces 3Sum to 2Sum for each i
 * 
 * 3. How to avoid duplicates?
 *    - Skip duplicate values for i
 *    - Skip duplicate values for left pointer
 *    - Skip duplicate values for right pointer
 * 
 * 4. Example: [-1,0,1,2,-1,-4]
 *    - Sort: [-4,-1,-1,0,1,2]
 *    - i=0: nums[0]=-4, need sum=4 from rest
 *      -> left=1 (-1), right=5 (2), sum=1 -> too small, move left
 *      -> left=2 (-1), right=5 (2), sum=1 -> too small, move left
 *      -> left=3 (0), right=5 (2), sum=2 -> too small, move left
 *      -> No solution for i=0
 *    - i=1: nums[1]=-1, need sum=1 from rest
 *      -> left=2 (-1), right=5 (2), sum=1 -> Found! [-1,-1,2]
 *      -> Continue...
 * 
 * 5. Edge cases:
 *    - Array length < 3
 *    - All zeros
 *    - No solution
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem5_ThreeSum {
    
    // TODO: Implement threeSum method
    // Find all unique triplets that sum to zero
    
    public List<List<Integer>> threeSum(int[] nums) {
        // YOUR CODE HERE
        // Think about:
        // - Should you sort first?
        // - How to use two pointers for each i?
        // - How to avoid duplicates?
        
        return new ArrayList<>();
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem5_ThreeSum solution = new Problem5_ThreeSum();
        
        // Test case 1
        List<List<Integer>> result1 = solution.threeSum(new int[]{-1,0,1,2,-1,-4});
        System.out.println("Test 1: " + result1);
        // Expected: [[-1,-1,2],[-1,0,1]]
        
        // Test case 2
        List<List<Integer>> result2 = solution.threeSum(new int[]{0,1,1});
        System.out.println("Test 2: " + result2);
        // Expected: []
        
        // Test case 3
        List<List<Integer>> result3 = solution.threeSum(new int[]{0,0,0});
        System.out.println("Test 3: " + result3);
        // Expected: [[0,0,0]]
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for right
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
    
    Time Complexity: O(n²) - n iterations, each with O(n) two pointer scan
    Space Complexity: O(1) excluding output array
    */
}
