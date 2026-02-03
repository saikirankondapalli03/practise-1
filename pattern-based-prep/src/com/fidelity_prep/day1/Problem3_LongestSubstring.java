package fidelity_prep.day1;

import java.util.*;

/**
 * Problem 3: Longest Substring Without Repeating Characters
 * LeetCode: 3. Longest Substring Without Repeating Characters
 * 
 * Pattern: Sliding Window (Variable Size)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a SLIDING WINDOW problem because:
 * - We're looking for a substring (contiguous)
 * - We need to maintain a condition (no repeating chars)
 * - Window size can vary
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find the longest substring with all unique characters
 * 
 * 2. Sliding window approach:
 *    - Expand window by moving right pointer
 *    - When we see a duplicate, shrink window from left
 *    - Track maximum window size seen so far
 * 
 * 3. How to detect duplicates?
 *    - Use HashMap<Character, Integer> to track last seen index
 *    - Or use HashSet to track characters in current window
 * 
 * 4. Example: "abcabcbb"
 *    - Start: window = "a" (left=0, right=0)
 *    - Expand: "ab", "abc" (all unique)
 *    - Expand: "abca" -> duplicate 'a' at index 3
 *      -> Move left to index 1 (after first 'a')
 *    - Continue...
 * 
 * 5. Edge cases:
 *    - Empty string
 *    - All same characters
 *    - All unique characters
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem3_LongestSubstring {
    
    // TODO: Implement lengthOfLongestSubstring method
    // Find length of longest substring without repeating characters
    
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if (n == 0) return 0;

        // Assuming all ASCII characters
        int hashLen = 256;
        // Hash table: last occurrence index of each character
        int[] hash = new int[hashLen];
        Arrays.fill(hash, -1);

        int l = 0, r = 0, maxLen = 0;
        while (r < n) {
            // If current char is already in the substring, move left past its last occurrence
            if (hash[s.charAt(r)] >= l) {
                l = Math.max(hash[s.charAt(r)] + 1, l);
            }
            int len = r - l + 1;
            maxLen = Math.max(len, maxLen);
            hash[s.charAt(r)] = r;
            r++;
        }
        return maxLen;
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem3_LongestSubstring solution = new Problem3_LongestSubstring();
        
        // Test case 1
        int result1 = solution.lengthOfLongestSubstring("abcabcbb");
        System.out.println("Test 1: " + result1); // Expected: 3 ("abc")
        
        // Test case 2
        int result2 = solution.lengthOfLongestSubstring("bbbbb");
        System.out.println("Test 2: " + result2); // Expected: 1 ("b")
        
        // Test case 3
        int result3 = solution.lengthOfLongestSubstring("pwwkew");
        System.out.println("Test 3: " + result3); // Expected: 3 ("wke")
        
        // Test case 4
        int result4 = solution.lengthOfLongestSubstring(" ");
        System.out.println("Test 4: " + result4); // Expected: 1
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If we've seen this character and it's within our window
            if (charIndexMap.containsKey(currentChar) && 
                charIndexMap.get(currentChar) >= left) {
                // Move left pointer to after the last occurrence
                left = charIndexMap.get(currentChar) + 1;
            }
            
            // Update character's last seen index
            charIndexMap.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    Time Complexity: O(n) - single pass
    Space Complexity: O(min(n, m)) where m is character set size
    */
}
