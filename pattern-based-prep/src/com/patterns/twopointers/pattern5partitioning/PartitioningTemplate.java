/*
package com.patterns.twopointers.pattern5partitioning;

import java.util.*;

/**
 * Pattern 5: Partitioning (Three Pointers)
 * Use Case: Partition array into 3 sections, Dutch flag algorithm
 * Time: O(n), Space: O(1)
 */

/*
public class PartitioningTemplate {
    
    // Dutch Flag - Sort array of 0s, 1s, and 2s
    public void sortColors(int[] nums) {
        int left = 0;    // Boundary for 0s (next position for 0)
        int right = nums.length - 1; // Boundary for 2s (next position for 2)
        int current = 0; // Current element being processed
        
        while (current <= right) {
            if (nums[current] == 0) {
                swap(nums, current, left);
                left++;
                current++; // Safe to move current (we know nums[left] was 0 or 1)
            } else if (nums[current] == 2) {
                swap(nums, current, right);
                right--;
                // Don't increment current - need to check the swapped element
            } else { // nums[current] == 1
                current++; // 1 is in correct position
            }
        }
    }
    
    // Partition array around pivot (QuickSort partition)
    public int partition(int[] nums, int pivot) {
        int left = 0;    // Elements < pivot
        int right = nums.length - 1; // Elements > pivot
        int current = 0;
        
        while (current <= right) {
            if (nums[current] < pivot) {
                swap(nums, current, left);
                left++;
                current++;
            } else if (nums[current] > pivot) {
                swap(nums, current, right);
                right--;
                // Don't increment current
            } else { // nums[current] == pivot
                current++;
            }
        }
        return left; // First position of pivot elements
    }
    
    // Partition array: negatives, zeros, positives
    public void partitionBySign(int[] nums) {
        int negEnd = 0;  // End of negative section
        int posStart = nums.length - 1; // Start of positive section
        int current = 0;
        
        while (current <= posStart) {
            if (nums[current] < 0) {
                swap(nums, current, negEnd);
                negEnd++;
                current++;
            } else if (nums[current] > 0) {
                swap(nums, current, posStart);
                posStart--;
                // Don't increment current
            } else { // nums[current] == 0
                current++;
            }
        }
    }
    
    // Generic three-way partition template
    public void threeWayPartition(int[] nums, int low, int high) {
        int left = 0;    // Elements < low
        int right = nums.length - 1; // Elements > high
        int current = 0;
        
        while (current <= right) {
            if (nums[current] < low) {
                swap(nums, current, left);
                left++;
                current++;
            } else if (nums[current] > high) {
                swap(nums, current, right);
                right--;
                // Don't increment current
            } else { // low <= nums[current] <= high
                current++;
            }
        }
    }
    
    // Move even numbers to left, odd to right
    public void partitionEvenOdd(int[] nums) {
        int left = 0;    // Next position for even
        int right = nums.length - 1; // Next position for odd
        
        while (left < right) {
            if (nums[left] % 2 == 0) {
                left++; // Even number in correct position
            } else {
                // Odd number, swap with right
                swap(nums, left, right);
                right--;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // Key insight: When swapping with right pointer, don't increment current
    // because we need to process the swapped element
}


*/
