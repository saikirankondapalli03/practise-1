package fidelity_prep.day3;

/**
 * Problem 3: Binary Search
 * LeetCode: 704. Binary Search
 * 
 * Pattern: Binary Search (Basic)
 * Difficulty: Easy
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Find target in sorted array
 * 
 * 2. Binary search logic:
 *    - Compare target with middle element
 *    - If equal: found
 *    - If target < mid: search left half
 *    - If target > mid: search right half
 * 
 * 3. Boundary handling:
 *    - left <= right (include equality)
 *    - mid = left + (right - left) / 2 (avoid overflow)
 *    - Update: left = mid + 1 or right = mid - 1
 */

public class Problem3_BinarySearch {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
}
