/**
 * Pattern 2: Opposite Direction - Triplets
 * Use Case: Three sum problems, triplet finding with fixed first element
 * Time: O(n²), Space: O(1) excluding result
 */
public class OppositeDirectionTripletsTemplate {
    
    // Three sum - find all unique triplets that sum to zero
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums); // Must sort first
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1, right = nums.length - 1;
            int target = -nums[i]; // We want nums[left] + nums[right] = target
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for second and third elements
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
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
    
    // Three sum closest - find triplet closest to target
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];
                
                // Update closest sum if current is closer
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                if (currentSum < target) {
                    left++;
                } else if (currentSum > target) {
                    right--;
                } else {
                    return currentSum; // Exact match
                }
            }
        }
        return closestSum;
    }
    
    // Count triplets with sum smaller than target
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            
            while (left < right) {
                if (nums[i] + nums[left] + nums[right] < target) {
                    // All triplets (i, left, left+1), (i, left, left+2), ..., (i, left, right) are valid
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        return count;
    }
    
    // Generic triplet template
    public List<List<Integer>> findTriplets(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // Skip duplicates
            
            int left = i + 1, right = nums.length - 1;
            int remaining = target - nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == remaining) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < remaining) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}