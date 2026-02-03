package fidelity_prep.day3;

/**
 * Problem 5: Find First and Last Position
 * LeetCode: 34. Find First and Last Position of Element in Sorted Array
 * 
 * Pattern: Binary Search (Boundaries)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN
 * ============================================
 * 
 * 1. Find first and last occurrence of target
 * 
 * 2. Two binary searches:
 *    - Find first: when nums[mid] == target, go left
 *    - Find last: when nums[mid] == target, go right
 * 
 * 3. First position logic:
 *    - If nums[mid] >= target: might be first, search left
 *    - Else: search right
 *    - When found, continue left to find first
 * 
 * 4. Last position logic:
 *    - If nums[mid] <= target: might be last, search right
 *    - Else: search left
 *    - When found, continue right to find last
 */

public class Problem5_FirstLastPosition {
    public int[] searchRange(int[] nums, int target) {
        int first = findFirst(nums, target);
        if (first == -1) return new int[]{-1, -1};
        int last = findLast(nums, target);
        return new int[]{first, last};
    }
    
    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int first = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                first = mid;
                right = mid - 1; // Continue left
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return first;
    }
    
    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int last = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                last = mid;
                left = mid + 1; // Continue right
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return last;
    }
}
