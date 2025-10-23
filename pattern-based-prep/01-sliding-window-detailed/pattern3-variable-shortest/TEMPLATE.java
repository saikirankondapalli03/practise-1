/**
 * Pattern 3: Variable Size - Find Shortest Valid Window
 * Use Case: Find minimum length subarray/substring meeting condition
 * Time: O(n), Space: O(k) where k is unique elements
 */
public class VariableShortestTemplate {
    
    // Minimum window substring containing all characters of pattern
    public String minWindow(String s, String t) {
        Map<Character, Integer> required = new HashMap<>();
        for (char c : t.toCharArray()) {
            required.put(c, required.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, minLength = Integer.MAX_VALUE, minStart = 0;
        int formed = 0; // Number of unique chars in window with desired frequency
        Map<Character, Integer> windowCounts = new HashMap<>();
        
        for (int right = 0; right < s.length(); right++) {
            // Expand window
            char rightChar = s.charAt(right);
            windowCounts.put(rightChar, windowCounts.getOrDefault(rightChar, 0) + 1);
            
            if (required.containsKey(rightChar) && 
                windowCounts.get(rightChar).intValue() == required.get(rightChar).intValue()) {
                formed++;
            }
            
            // Shrink window while valid
            while (formed == required.size()) {
                // Update minimum window
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    minStart = left;
                }
                
                // Try to shrink from left
                char leftChar = s.charAt(left);
                windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
                if (required.containsKey(leftChar) && 
                    windowCounts.get(leftChar) < required.get(leftChar)) {
                    formed--;
                }
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }
    
    // Minimum subarray with sum >= target
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0, minLength = Integer.MAX_VALUE;
        int currentSum = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window
            currentSum += nums[right];
            
            // Shrink window while valid
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                currentSum -= nums[left];
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    // Generic template for "shortest valid window"
    public int shortestValidWindow(int[] arr, /* condition parameters */) {
        int left = 0, minLength = Integer.MAX_VALUE;
        // State variables for tracking condition
        
        for (int right = 0; right < arr.length; right++) {
            // Expand window - update state
            
            // Shrink window while valid
            while (/* window is valid */) {
                minLength = Math.min(minLength, right - left + 1);
                // Update state for left element
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}