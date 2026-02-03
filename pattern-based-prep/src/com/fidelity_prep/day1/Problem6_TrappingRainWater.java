package fidelity_prep.day1;

/**
 * Problem 6: Trapping Rain Water
 * LeetCode: 42. Trapping Rain Water
 * 
 * Pattern: Two Pointers (with logic)
 * Difficulty: Hard
 * 
 * ============================================
 * STEP 1: PATTERN RECOGNITION (2 min)
 * ============================================
 * This is a TWO POINTERS problem because:
 * - We can process from both ends
 * - The water trapped depends on min of max heights on both sides
 * - We can use two pointers to track max heights
 * 
 * ============================================
 * STEP 2: LOGICAL BREAKDOWN (5-10 min)
 * ============================================
 * Write your logic here BEFORE coding:
 * 
 * 1. I need to calculate total water trapped between bars
 * 
 * 2. Key insight: Water trapped at index i = 
 *    min(max_height_left, max_height_right) - height[i]
 *    (if this is positive)
 * 
 * 3. Two pointer approach:
 *    - left = 0, right = length - 1
 *    - leftMax = max height seen from left
 *    - rightMax = max height seen from right
 *    - At each step, process the side with smaller max
 *    - Why? Because water is limited by the smaller side
 * 
 * 4. Example: [0,1,0,2,1,0,1,3,2,1,2,1]
 *    - left=0 (height=0), right=11 (height=1)
 *    - leftMax=0, rightMax=1
 *    - leftMax < rightMax, so process left
 *    - Water at index 0: min(0,1) - 0 = 0
 *    - Move left, update leftMax
 *    - Continue...
 * 
 * 5. Logic:
 *    - If leftMax < rightMax: process left side
 *      -> Water = leftMax - height[left] (if positive)
 *      -> Update leftMax, move left++
 *    - Else: process right side
 *      -> Water = rightMax - height[right] (if positive)
 *      -> Update rightMax, move right--
 * 
 * ============================================
 * STEP 3: CODE IMPLEMENTATION (15-20 min)
 * ============================================
 * Follow your logical steps above. Code here:
 */

public class Problem6_TrappingRainWater {
    
    // TODO: Implement trap method
    // Calculate total water trapped
    
    public int trap(int[] height) {
        // YOUR CODE HERE
        // Think about:
        // - How to track max heights from both sides?
        // - Which pointer to process and why?
        // - How to calculate water at each position?
        
        return 0;
    }
    
    // Test your solution
    public static void main(String[] args) {
        Problem6_TrappingRainWater solution = new Problem6_TrappingRainWater();
        
        // Test case 1
        int result1 = solution.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});
        System.out.println("Test 1: " + result1); // Expected: 6
        
        // Test case 2
        int result2 = solution.trap(new int[]{4,2,0,3,2,5});
        System.out.println("Test 2: " + result2); // Expected: 9
        
        // Test case 3
        int result3 = solution.trap(new int[]{3,0,2,0,4});
        System.out.println("Test 3: " + result3); // Expected: 7
    }
    
    // ============================================
    // STEP 4: SOLUTION (Check after you code)
    // ============================================
    /*
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                // Process right side
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
    
    Time Complexity: O(n) - single pass
    Space Complexity: O(1) - only using pointers
    */
}
