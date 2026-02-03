package fidelity_prep.day1;

/**
 * Problem 1: Two Sum (Sorted Array)
 * LeetCode: 167. Two Sum II - Input array is sorted
 * 
 * Pattern: Two Pointers (Opposite Direction)
 * Difficulty: Easy
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a TWO POINTERS problem because:
 * - Array is sorted
 * - We're looking for a pair
 * - We can eliminate half the search space at each step
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find two numbers that add up to target
 * 
 * 2. Since array is sorted, I can:
 *    - Start with left = 0, right = length - 1
 *    - If sum < target: move left pointer right (need bigger number)
 *    - If sum > target: move right pointer left (need smaller number)
 *    - If sum == target: found it!
 * 
 * 3. Edge cases:
 *    - What if no solution exists?
 *    - What if array has duplicates?
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem1_TwoSum {
    
    // TODO: Implement twoSum method
    // Signature: public int[] twoSum(int[] numbers, int target)
    // Return 1-indexed positions [index1, index2] where index1 < index2
    // If no solution, return [-1, -1]
    
    // Example: numbers = [2, 7, 11, 15], target = 9
    // Output: [1, 2] (because numbers[0] + numbers[1] = 2 + 7 = 9)
    
    public int[] twoSum(int[] numbers, int target) {
        // YOUR CODE HERE
        // Follow your logical steps from Step 2
        
        return new int[]{-1, -1};
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem1_TwoSum solution = new Problem1_TwoSum();
        
        // Test case 1
        int[] result1 = solution.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println("Test 1: " + java.util.Arrays.toString(result1)); // Expected: [1, 2]
        
        // Test case 2
        int[] result2 = solution.twoSum(new int[]{2, 3, 4}, 6);
        System.out.println("Test 2: " + java.util.Arrays.toString(result2)); // Expected: [1, 3]
        
        // Test case 3
        int[] result3 = solution.twoSum(new int[]{-1, 0}, -1);
        System.out.println("Test 3: " + java.util.Arrays.toString(result3)); // Expected: [1, 2]
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++; // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
        
        return new int[]{-1, -1}; // Not found
    }
    
    Time Complexity: O(n) - single pass
    Space Complexity: O(1) - only using pointers
    */
}
