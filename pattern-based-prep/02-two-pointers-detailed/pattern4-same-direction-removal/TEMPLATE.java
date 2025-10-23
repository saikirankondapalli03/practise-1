/**
 * Pattern 4: Same Direction - Removal
 * Use Case: Remove elements in-place, fast/slow pointer technique
 * Time: O(n), Space: O(1)
 */
public class SameDirectionRemovalTemplate {
    
    // Remove duplicates from sorted array
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        
        int slow = 0; // Points to position for next unique element
        
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1; // Length of unique elements
    }
    
    // Remove all instances of a value
    public int removeElement(int[] nums, int val) {
        int slow = 0; // Points to position for next valid element
        
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow; // New length
    }
    
    // Move all zeros to end while maintaining relative order
    public void moveZeroes(int[] nums) {
        int slow = 0; // Points to position for next non-zero
        
        // Move all non-zeros to front
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        
        // Fill remaining positions with zeros
        while (slow < nums.length) {
            nums[slow] = 0;
            slow++;
        }
    }
    
    // Move zeros to end with swapping (maintains non-zero order)
    public void moveZeroesSwap(int[] nums) {
        int slow = 0; // Points to next position for non-zero
        
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // Swap only if positions are different
                if (slow != fast) {
                    int temp = nums[slow];
                    nums[slow] = nums[fast];
                    nums[fast] = temp;
                }
                slow++;
            }
        }
    }
    
    // Remove duplicates allowing at most k occurrences
    public int removeDuplicatesK(int[] nums, int k) {
        if (nums.length <= k) return nums.length;
        
        int slow = k; // Start from position k
        
        for (int fast = k; fast < nums.length; fast++) {
            // Keep element if it's different from k positions back
            if (nums[fast] != nums[slow - k]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
    
    // Generic removal template
    public int removeElements(int[] nums, /* condition parameters */) {
        int slow = 0; // Points to position for next valid element
        
        for (int fast = 0; fast < nums.length; fast++) {
            if (/* element should be kept */) {
                nums[slow] = nums[fast];
                slow++;
            }
            // Elements that don't meet condition are "removed"
        }
        return slow; // New length after removal
    }
    
    // Two-pass approach for complex conditions
    public int removeComplexCondition(int[] nums) {
        // Pass 1: Mark elements to remove (if needed)
        
        // Pass 2: Use fast/slow pointers to compact
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (/* should keep element */) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}