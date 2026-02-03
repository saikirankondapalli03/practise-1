package fidelity_prep.day1;

/**
 * Problem 2: Container With Most Water
 * LeetCode: 11. Container With Most Water
 * 
 * Pattern: Two Pointers (Opposite Direction)
 * Difficulty: Medium
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a TWO POINTERS problem because:
 * - We're comparing pairs of elements
 * - We can eliminate possibilities by moving pointers
 * - The array represents heights at positions
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to find two lines that form the container with maximum area
 * 
 * 2. Area = min(height[left], height[right]) * (right - left)
 * 
 * 3. Key insight: When should I move which pointer?
 *    - If height[left] < height[right]: move left (why?)
 *    - If height[right] < height[left]: move right (why?)
 *    - Think: which move can potentially give us a larger area?
 * 
 * 4. Draw example: [1,8,6,2,5,4,8,3,7]
 *    - Start: left=0 (height=1), right=8 (height=7), area = 1*8 = 8
 *    - What happens if we move right? What if we move left?
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem2_ContainerWithMostWater {
    
    // TODO: Implement maxArea method
    // Given array of heights, find maximum area of water container
    // Area = min(height[i], height[j]) * (j - i)
    
    public int maxArea(int[] height) {
        // YOUR CODE HERE
        // Think about: which pointer to move and why?
        
        return 0;
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem2_ContainerWithMostWater solution = new Problem2_ContainerWithMostWater();
        
        // Test case 1
        int result1 = solution.maxArea(new int[]{1,8,6,2,5,4,8,3,7});
        System.out.println("Test 1: " + result1); // Expected: 49
        
        // Test case 2
        int result2 = solution.maxArea(new int[]{1,1});
        System.out.println("Test 2: " + result2); // Expected: 1
        
        // Test case 3
        int result3 = solution.maxArea(new int[]{1,2,1});
        System.out.println("Test 3: " + result3); // Expected: 2
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // Calculate current area
            int width = right - left;
            int currentArea = Math.min(height[left], height[right]) * width;
            maxArea = Math.max(maxArea, currentArea);
            
            // Move the pointer with smaller height
            // Why? Because the area is limited by the smaller height
            // Moving the larger height pointer can only decrease area
            // Moving the smaller height pointer might find a taller line
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    Time Complexity: O(n) - single pass
    Space Complexity: O(1) - only using pointers
    */
}
