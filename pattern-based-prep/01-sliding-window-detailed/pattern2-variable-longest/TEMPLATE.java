/**
 * Pattern 2: Variable Size - Find Longest Valid Window
 * Use Case: Find maximum length subarray/substring meeting condition
 * Time: O(n), Space: O(k) where k is unique elements
 */
public class VariableLongestTemplate {
    
    // Longest substring with at most K distinct characters
    public int longestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Expand window
            char rightChar = s.charAt(right);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
            
            // Shrink window if invalid
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
    
    // Longest substring without repeating characters
    public int lengthOfLongestSubstring(String s) {
        Set<Character> seen = new HashSet<>();
        int left = 0, maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Shrink window until no duplicates
            while (seen.contains(s.charAt(right))) {
                seen.remove(s.charAt(left));
                left++;
            }
            
            // Add current character and update max
            seen.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
    
    // Generic template for "longest valid window"
    public int longestValidWindow(int[] arr, /* condition parameters */) {
        int left = 0, maxLength = 0;
        // State variables for tracking condition
        
        for (int right = 0; right < arr.length; right++) {
            // Expand window - update state
            
            // Shrink window while invalid
            while (/* window is invalid */) {
                // Update state for left element
                left++;
            }
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}