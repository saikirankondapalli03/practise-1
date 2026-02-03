package fidelity_prep.day3;

/**
 * Problem 4: Search in Rotated Sorted Array
 * LeetCode: 33. Search in Rotated Sorted Array
 * 
 * Pattern: Binary Search (Adaptation)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Array is rotated but sorted in parts
 *    Example: [4,5,6,7,0,1,2] (rotated at index 4)
 * 
 * 2. Key insight: One half is always sorted
 *    - Compare mid with left/right to find sorted half
 *    - If left half sorted: check if target in range
 *    - If right half sorted: check if target in range
 * 
 * 3. Logic:
 *    - If nums[left] <= nums[mid]: left half sorted
 *      - If target in [left, mid]: search left
 *      - Else: search right
 *    - Else: right half sorted
 *      - If target in [mid, right]: search right
 *      - Else: search left
 */

public class Problem4_SearchRotated {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) return mid;
            
            // Left half is sorted
            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } 
            // Right half is sorted
            else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
}
