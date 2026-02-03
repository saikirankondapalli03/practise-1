package fidelity_prep.day1;

import java.util.*;

/**
 * Problem 4: Minimum Window Substring
 * LeetCode: 76. Minimum Window Substring
 * 
 * Pattern: Sliding Window (Variable Size - Minimum)
 * Difficulty: Hard
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a SLIDING WINDOW problem because:
 * - We're looking for a substring (contiguous)
 * - We need to find minimum valid window
 * - We expand and contract the window
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find the minimum window in s that contains all characters of t
 * 
 * 2. Approach:
 *    - Use sliding window with two pointers
 *    - Expand window (move right) until we have all characters
 *    - Once we have all characters, try to shrink (move left) to minimize
 *    - Track the minimum window found
 * 
 * 3. How to track if we have all characters?
 *    - Use HashMap to count characters needed from t
 *    - Use another HashMap to count characters in current window
 *    - Or use a counter: when counter == t.length(), we have valid window
 * 
 * 4. Example: s = "ADOBECODEBANC", t = "ABC"
 *    - Need: A=1, B=1, C=1
 *    - Expand: "ADOBEC" -> has all (A, B, C)
 *    - Shrink: Try "DOBEC" -> still has all? Check...
 *    - Continue shrinking until invalid, then expand again
 * 
 * 5. Key logic:
 *    - When window is valid: try to shrink from left
 *    - When window is invalid: expand from right
 *    - Update minimum window whenever we find a valid one
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem4_MinimumWindowSubstring {
    
    // TODO: Implement minWindow method
    // Find minimum window substring that contains all characters of t
    
    public String minWindow(String s, String t) {
        // YOUR CODE HERE
        // Think about:
        // - How to track characters needed?
        // - How to know when window is valid?
        // - When to expand vs shrink?
        // - How to track minimum window?
        
        return "";
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem4_MinimumWindowSubstring solution = new Problem4_MinimumWindowSubstring();
        
        // Test case 1
        String result1 = solution.minWindow("ADOBECODEBANC", "ABC");
        System.out.println("Test 1: " + result1); // Expected: "BANC"
        
        // Test case 2
        String result2 = solution.minWindow("a", "a");
        System.out.println("Test 2: " + result2); // Expected: "a"
        
        // Test case 3
        String result3 = solution.minWindow("a", "aa");
        System.out.println("Test 3: " + result3); // Expected: ""
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }
        
        // Count characters needed from t
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        // Track characters in current window
        Map<Character, Integer> window = new HashMap<>();
        
        int left = 0, right = 0;
        int valid = 0; // Number of character types that satisfy requirement
        int needCount = need.size();
        
        int start = 0, minLen = Integer.MAX_VALUE;
        
        while (right < s.length()) {
            // Expand window
            char c = s.charAt(right);
            right++;
            
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }
            
            // Try to shrink window
            while (valid == needCount) {
                // Update minimum window
                if (right - left < minLen) {
                    start = left;
                    minLen = right - left;
                }
                
                // Remove left character
                char d = s.charAt(left);
                left++;
                
                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
    
    Time Complexity: O(|s| + |t|)
    Space Complexity: O(|s| + |t|)
    */
}
