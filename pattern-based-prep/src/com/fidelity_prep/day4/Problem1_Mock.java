package fidelity_prep.day4;

import java.util.*;

/**
 * MOCK INTERVIEW PROBLEM 1
 * Time: 45 minutes
 * 
 * Problem: Group Anagrams
 * LeetCode: 49. Group Anagrams
 * Difficulty: Medium
 * 
 * ============================================
 * YOUR APPROACH (10 min - write here)
 * ============================================
 * 
 * Pattern Recognition:
 * 
 * 
 * Logical Steps:
 * 1.
 * 2.
 * 3.
 * 
 * Edge Cases:
 * 
 * 
 * Complexity:
 * Time: 
 * Space: 
 * 
 * ============================================
 * CODE (25 min)
 * ============================================
 */

public class Problem1_Mock {
    public List<List<String>> groupAnagrams(String[] strs) {
        // YOUR CODE HERE
        
        return new ArrayList<>();
    }
    
    // Test cases
    public static void main(String[] args) {
        Problem1_Mock solution = new Problem1_Mock();
        
        String[] input1 = {"eat","tea","tan","ate","nat","bat"};
        System.out.println(solution.groupAnagrams(input1));
        // Expected: [["bat"],["nat","tan"],["ate","eat","tea"]]
    }
    
    // ============================================
    // SOLUTION (Check after 45 min)
    // ============================================
    /*
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    Time: O(n * k log k) where k is max string length
    Space: O(n * k)
    */
}
