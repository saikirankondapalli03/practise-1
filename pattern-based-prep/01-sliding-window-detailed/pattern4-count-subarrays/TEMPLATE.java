/**
 * Pattern 4: Count Subarrays Meeting Condition
 * Use Case: Count number of valid subarrays/substrings
 * Time: O(n), Space: O(1) or O(k)
 */
public class CountSubarraysTemplate {
    
    // Count subarrays with product less than k
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;
        
        int left = 0, count = 0;
        int product = 1;
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window
            product *= nums[right];
            
            // Shrink window while invalid
            while (product >= k) {
                product /= nums[left];
                left++;
            }
            
            // Count subarrays ending at right
            // All subarrays from left to right are valid
            count += right - left + 1;
        }
        
        return count;
    }
    
    // Count subarrays with sum equal to k
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1); // Empty subarray
        
        int count = 0, prefixSum = 0;
        
        for (int num : nums) {
            prefixSum += num;
            
            // Check if (prefixSum - k) exists
            if (prefixSumCount.containsKey(prefixSum - k)) {
                count += prefixSumCount.get(prefixSum - k);
            }
            
            // Add current prefix sum
            prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
        }
        
        return count;
    }
    
    // Count subarrays with at most k distinct elements
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }
    
    private int atMostK(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, result = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window
            count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);
            
            // Shrink window while invalid
            while (count.size() > k) {
                count.put(nums[left], count.get(nums[left]) - 1);
                if (count.get(nums[left]) == 0) {
                    count.remove(nums[left]);
                }
                left++;
            }
            
            // Count subarrays ending at right
            result += right - left + 1;
        }
        
        return result;
    }
    
    // Generic template for counting subarrays
    public int countValidSubarrays(int[] arr, /* condition parameters */) {
        int left = 0, count = 0;
        // State variables for tracking condition
        
        for (int right = 0; right < arr.length; right++) {
            // Expand window - update state
            
            // Shrink window while invalid
            while (/* window is invalid */) {
                // Update state for left element
                left++;
            }
            
            // Count subarrays ending at right
            count += right - left + 1;
        }
        
        return count;
    }
}