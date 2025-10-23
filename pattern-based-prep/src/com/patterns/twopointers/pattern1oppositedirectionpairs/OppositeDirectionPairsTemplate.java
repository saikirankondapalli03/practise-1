/*
package com.patterns.twopointers.pattern1oppositedirectionpairs;

/**
 * Pattern 1: Opposite Direction - Pairs
 * Use Case: Find pairs in sorted array, two sum problems
 * Time: O(n), Space: O(1)
 */
/*
public class OppositeDirectionPairsTemplate {
    
    // Basic two sum in sorted array
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++;  // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
        return new int[]{-1, -1}; // Not found
    }
    
    // Check if pair exists with given sum
    public boolean hasPairWithSum(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) return true;
            else if (sum < target) left++;
            else right--;
        }
        return false;
    }
    
    // Count pairs with sum less than target
    public int countPairs(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int count = 0;
        
        while (left < right) {
            if (arr[left] + arr[right] < target) {
                // All pairs (left, left+1), (left, left+2), ..., (left, right) are valid
                count += right - left;
                left++;
            } else {
                right--;
            }
        }
        return count;
    }
    
    // Squares of sorted array (handles negatives)
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0, right = n - 1;
        int index = n - 1; // Fill from end
        
        while (left <= right) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];
            
            if (leftSquare > rightSquare) {
                result[index] = leftSquare;
                left++;
            } else {
                result[index] = rightSquare;
                right--;
            }
            index--;
        }
        return result;
    }
}

*/
