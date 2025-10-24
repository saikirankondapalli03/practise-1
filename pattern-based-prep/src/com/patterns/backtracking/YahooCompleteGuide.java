package com.patterns.backtracking;

import java.util.*;

/**
 * COMPLETE Yahoo Interview Guide - Everything from COMPLETE_INTERVIEW_GUIDE_WITH_COMPLEXITY
 * All patterns, algorithms, and complexity analysis for tomorrow's interview
 */
public class YahooCompleteGuide {
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    // ========== 1. TWO POINTERS ==========
    
    /**
     * 1A. Two Sum (sorted array)
     * Time: O(n) - single pass with two pointers
     * Space: O(1) - only using pointer variables
     */
    public static int[] twoSum(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) return new int[]{left, right};
            else if (sum < target) left++;
            else right--;
        }
        return new int[]{-1, -1};
    }
    
    /**
     * 1B. Remove Duplicates
     * Time: O(n) - single pass through array
     * Space: O(1) - in-place modification with slow/fast pointers
     */
    public static int removeDuplicates(int[] nums) {
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                nums[++slow] = nums[fast];
            }
        }
        return slow + 1;
    }
    
    /**
     * 1C. Palindrome Check
     * Time: O(n) - single pass with two pointers
     * Space: O(1) - only pointer variables
     */
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++; right--;
        }
        return true;
    }
    
    /**
     * 1D. Container With Most Water
     * Time: O(n) - single pass with two pointers
     * Space: O(1) - only storing max area and pointers
     */
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxWater = 0;
        
        while (left < right) {
            int water = Math.min(height[left], height[right]) * (right - left);
            maxWater = Math.max(maxWater, water);
            
            if (height[left] < height[right]) left++;
            else right--;
        }
        return maxWater;
    }
    
    // ========== 2. SLIDING WINDOW ==========
    
    /**
     * 2A. Fixed Size Window - Maximum Sum Subarray
     * Time: O(n) - slide window across array once
     * Space: O(1) - only storing window sum and maximum
     */
    public static int maxSumSubarray(int[] arr, int k) {
        int maxSum = 0, windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += arr[i];
        maxSum = windowSum;
        
        for (int i = k; i < arr.length; i++) {
            windowSum += arr[i] - arr[i-k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }
    
    /**
     * 2B. Longest Substring Without Repeating Characters
     * Time: O(n) - each character visited at most twice
     * Space: O(min(m,n)) - HashSet size limited by charset or string length
     */
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0, maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left++));
            }
            set.add(s.charAt(right));
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Time: O(|s| + |t|), Space: O(|s| + |t|)
// Variant C: Minimum window substring
    public static String findMinimumWindowSubstring(String sourceString, String targetString) {
        // Step 1: Count what characters we need to find
        Map<Character, Integer> charactersNeeded = new HashMap<>();
        for (char ch : targetString.toCharArray()) {
            charactersNeeded.put(ch, charactersNeeded.getOrDefault(ch, 0) + 1);
        }

        // Step 2: Initialize sliding window variables
        int windowStart = 0, windowEnd = 0;
        int satisfiedCharacterTypes = 0;  // How many char types have enough count
        int totalCharacterTypesNeeded = charactersNeeded.size();

        // Step 3: Track the best (minimum) window found so far
        int bestWindowStart = 0, bestWindowLength = Integer.MAX_VALUE;

        // Step 4: Count characters in current window
        Map<Character, Integer> charactersInCurrentWindow = new HashMap<>();

        // Step 5: Sliding window algorithm
        while (windowEnd < sourceString.length()) {

            // EXPAND: Add character from right side
            char characterEnteringWindow = sourceString.charAt(windowEnd);
            charactersInCurrentWindow.put(characterEnteringWindow,
                    charactersInCurrentWindow.getOrDefault(characterEnteringWindow, 0) + 1);

            // Check if this character type now has enough count
            if (charactersNeeded.containsKey(characterEnteringWindow) &&
                    charactersInCurrentWindow.get(characterEnteringWindow).equals(
                            charactersNeeded.get(characterEnteringWindow))) {
                satisfiedCharacterTypes++;
            }

            windowEnd++;  // Move right boundary

            // CONTRACT: Try to shrink window from left while it's still valid
            while (satisfiedCharacterTypes == totalCharacterTypesNeeded) {

                // Update best window if current is smaller
                int currentWindowLength = windowEnd - windowStart;
                if (currentWindowLength < bestWindowLength) {
                    bestWindowStart = windowStart;
                    bestWindowLength = currentWindowLength;
                }

                // Remove character from left side
                char characterLeavingWindow = sourceString.charAt(windowStart);
                charactersInCurrentWindow.put(characterLeavingWindow,
                        charactersInCurrentWindow.get(characterLeavingWindow) - 1);

                // Check if removing this character breaks the requirement
                if (charactersNeeded.containsKey(characterLeavingWindow) &&
                        charactersInCurrentWindow.get(characterLeavingWindow) <
                                charactersNeeded.get(characterLeavingWindow)) {
                    satisfiedCharacterTypes--;
                }

                windowStart++;  // Move left boundary
            }
        }

        // Step 6: Return result
        if (bestWindowLength == Integer.MAX_VALUE) {
            return "";  // No valid window found
        } else {
            return sourceString.substring(bestWindowStart, bestWindowStart + bestWindowLength);
        }
    }


    // ========== 3. BINARY SEARCH ==========
    
    /**
     * 3A. Basic Binary Search
     * Time: O(log n) - halve search space each iteration
     * Space: O(1) - only using pointer variables
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    /**
     * 3B. Find First Occurrence
     * Time: O(log n) - binary search with left bias
     * Space: O(1) - constant extra variables
     */
    public static int findFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching left
            } else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return result;
    }
    
    /**
     * 3C. Search in Rotated Sorted Array
     * Time: O(log n) - modified binary search, still halves space
     * Space: O(1) - no extra data structures needed
     */
    public static int searchRotated(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            
            if (nums[left] <= nums[mid]) { // Left half is sorted
                if (target >= nums[left] && target < nums[mid]) right = mid - 1;
                else left = mid + 1;
            } else { // Right half is sorted
                if (target > nums[mid] && target <= nums[right]) left = mid + 1;
                else right = mid - 1;
            }
        }
        return -1;
    }
    
    // ========== 4. DFS/BFS ==========
    
    /**
     * 4A. Number of Islands
     * Time: O(m×n) - visit each cell at most once across all DFS calls
     * Space: O(m×n) - recursion stack depth in worst case
     */
    public static int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfsIsland(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }
    
    private static void dfsIsland(char[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') return;
        grid[row][col] = '0';
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] dir : dirs) {
            dfsIsland(grid, row + dir[0], col + dir[1]);
        }
    }
    
    /**
     * 4B. Flood Fill
     * Time: O(m×n) - DFS visits each connected cell once
     * Space: O(m×n) - recursion stack for DFS calls
     */
    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int originalColor = image[sr][sc];
        if (originalColor != newColor) {
            dfsFloodFill(image, sr, sc, originalColor, newColor);
        }
        return image;
    }
    
    private static void dfsFloodFill(int[][] image, int row, int col, int originalColor, int newColor) {
        if (row < 0 || row >= image.length || col < 0 || col >= image[0].length || 
            image[row][col] != originalColor) return;
        
        image[row][col] = newColor;
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] dir : dirs) {
            dfsFloodFill(image, row + dir[0], col + dir[1], originalColor, newColor);
        }
    }
    
    /**
     * 4C. BFS Shortest Path
     * Time: O(m×n) - visit each cell once in BFS
     * Space: O(m×n) - queue and visited array storage
     */
    public static int shortestPath(int[][] grid, int[] start, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        
        queue.offer(new int[]{start[0], start[1], 0});
        visited[start[0]][start[1]] = true;
        
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int row = curr[0], col = curr[1], dist = curr[2];
            
            if (row == end[0] && col == end[1]) return dist;
            
            for (int[] dir : dirs) {
                int newRow = row + dir[0], newCol = col + dir[1];
                if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length &&
                    !visited[newRow][newCol] && grid[newRow][newCol] == 1) {
                    queue.offer(new int[]{newRow, newCol, dist + 1});
                    visited[newRow][newCol] = true;
                }
            }
        }
        return -1;
    }
    
    // ========== 5. BACKTRACKING ==========
    
    /**
     * 5A. Permutations (No reuse)
     * Time: O(n! × n) - n! permutations, each takes O(n) time to construct
     * Space: O(n) - recursion depth and path storage
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermute(nums, new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }
    
    private static void backtrackPermute(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            
            path.add(nums[i]);      // Choose
            used[i] = true;
            backtrackPermute(nums, path, used, result);  // Recurse
            path.remove(path.size() - 1);       // Unchoose
            used[i] = false;
        }
    }
    
    /**
     * 5B. Combinations (No reuse)
     * Time: O(C(n,k) × k) - C(n,k) combinations, each takes O(k) to build
     * Space: O(k) - recursion depth limited by combination size
     */
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombine(n, k, 1, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackCombine(int n, int k, int start, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i <= n; i++) {
            path.add(i);
            backtrackCombine(n, k, i + 1, path, result); // i+1 = no reuse
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * 5C. Subsets (No reuse)
     * Time: O(2^n × n) - 2^n subsets, each takes O(n) to build
     * Space: O(n) - recursion depth
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackSubsets(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path)); // Add current subset
        
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrackSubsets(nums, i + 1, path, result); // i+1 = no reuse
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * 5D. Combination Sum (WITH reuse)
     * Time: O(2^target) - exponential based on target value
     * Space: O(target) - recursion depth limited by target
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombSum(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackCombSum(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (target < 0) return;
        
        for (int i = start; i < candidates.length; i++) {
            path.add(candidates[i]);
            backtrackCombSum(candidates, target - candidates[i], i, path, result); // i = allow reuse
            path.remove(path.size() - 1);
        }
    }
    
    /**
     * 5E. Generate Parentheses
     * Time: O(4^n / √n) - Catalan number, approximately 4^n combinations
     * Space: O(n) - recursion depth limited by string length
     */
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrackParenthesis(n, 0, 0, "", result);
        return result;
    }
    
    private static void backtrackParenthesis(int n, int open, int close, String path, List<String> result) {
        if (path.length() == 2 * n) {
            result.add(path);
            return;
        }
        
        if (open < n) {
            backtrackParenthesis(n, open + 1, close, path + "(", result);
        }
        if (close < open) {
            backtrackParenthesis(n, open, close + 1, path + ")", result);
        }
    }
    
    /**
     * 5F. Word Search (Matrix backtracking)
     * Time: O(4^(m×n)) - worst case explores all paths
     * Space: O(m×n) - recursion stack depth
     */
    public static boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfsWordSearch(board, word, i, j, 0)) return true;
            }
        }
        return false;
    }
    
    private static boolean dfsWordSearch(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || 
            board[row][col] != word.charAt(index)) return false;
        
        char temp = board[row][col];
        board[row][col] = '#'; // CHOOSE: Mark as visited
        
        // RECURSE: Try all 4 directions
        boolean found = dfsWordSearch(board, word, row + 1, col, index + 1) ||
                       dfsWordSearch(board, word, row - 1, col, index + 1) ||
                       dfsWordSearch(board, word, row, col + 1, index + 1) ||
                       dfsWordSearch(board, word, row, col - 1, index + 1);
        
        board[row][col] = temp; // UNCHOOSE: Backtrack
        return found;
    }
    
    // ========== 6. DYNAMIC PROGRAMMING ==========
    
    /**
     * 6A. Maximum Subarray - Kadane's Algorithm
     * Time: O(n) - single pass through array
     * Space: O(1) - only two variables for tracking current and global max
     */
    public static int maxSubArray(int[] nums) {
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }
    
    /**
     * 6B. Climbing Stairs
     * Time: O(n) - compute each step once
     * Space: O(1) - optimized to use only two variables
     */
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
    
    /**
     * 6C. Coin Change
     * Time: O(amount × coins) - nested loops to fill DP table
     * Space: O(amount) - DP array of size amount+1
     */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    /**
     * 6D. Unique Paths (2D DP)
     * Time: O(m×n) - fill entire DP table
     * Space: O(m×n) - 2D DP array storage
     */
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;
        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
    
    // ========== 7. ADVANCED PATTERNS ==========
    
    /**
     * 7A. Valid Parentheses
     * Time: O(n) - single pass through string
     * Space: O(n) - stack can hold up to n/2 opening brackets
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
    
    /**
     * 7B. Trapping Rain Water
     * Time: O(n) - single pass with two pointers
     * Space: O(1) - only pointer and height variables
     */
    public static int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
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
    
    /**
     * 7C. Reverse Linked List
     * Time: O(n) - visit each node exactly once
     * Space: O(1) - only pointer variables, no recursion
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    
    // ========== INTERVIEW STRATEGY & COMPLEXITY GUIDE ==========
    
    public static void printComplexityGuide() {
        System.out.println("=== COMPLETE COMPLEXITY REFERENCE ===");
        System.out.println();
        
        System.out.println("🔍 TWO POINTERS:");
        System.out.println("• Two Sum: O(n) time, O(1) space");
        System.out.println("• Remove Duplicates: O(n) time, O(1) space");
        System.out.println("• Container Water: O(n) time, O(1) space");
        System.out.println("• Palindrome Check: O(n) time, O(1) space");
        System.out.println();
        
        System.out.println("🪟 SLIDING WINDOW:");
        System.out.println("• Fixed Window: O(n) time, O(1) space");
        System.out.println("• Longest Substring: O(n) time, O(min(m,n)) space");
        System.out.println("• Min Window Substring: O(|s|+|t|) time, O(|s|+|t|) space");
        System.out.println();
        
        System.out.println("🔍 BINARY SEARCH:");
        System.out.println("• Basic Search: O(log n) time, O(1) space");
        System.out.println("• Find First: O(log n) time, O(1) space");
        System.out.println("• Rotated Array: O(log n) time, O(1) space");
        System.out.println();
        
        System.out.println("🌊 DFS/BFS:");
        System.out.println("• Number of Islands: O(m×n) time, O(m×n) space");
        System.out.println("• Flood Fill: O(m×n) time, O(m×n) space");
        System.out.println("• BFS Shortest Path: O(m×n) time, O(m×n) space");
        System.out.println();
        
        System.out.println("🔄 BACKTRACKING:");
        System.out.println("• Permutations: O(n! × n) time, O(n) space");
        System.out.println("• Combinations: O(C(n,k) × k) time, O(k) space");
        System.out.println("• Subsets: O(2^n × n) time, O(n) space");
        System.out.println("• Combination Sum: O(2^target) time, O(target) space");
        System.out.println("• Generate Parentheses: O(4^n/√n) time, O(n) space");
        System.out.println("• Word Search: O(4^(m×n)) time, O(m×n) space");
        System.out.println();
        
        System.out.println("💎 DYNAMIC PROGRAMMING:");
        System.out.println("• Max Subarray: O(n) time, O(1) space");
        System.out.println("• Climbing Stairs: O(n) time, O(1) space");
        System.out.println("• Coin Change: O(amount × coins) time, O(amount) space");
        System.out.println("• Unique Paths: O(m×n) time, O(m×n) space");
        System.out.println();
        
        System.out.println("🚀 ADVANCED:");
        System.out.println("• Valid Parentheses: O(n) time, O(n) space");
        System.out.println("• Trapping Rain Water: O(n) time, O(1) space");
        System.out.println("• Reverse Linked List: O(n) time, O(1) space");
    }
    
    public static void printInterviewStrategy() {
        System.out.println();
        System.out.println("=== YAHOO INTERVIEW STRATEGY ===");
        System.out.println();
        
        System.out.println("📋 8-STEP APPROACH:");
        System.out.println("1. CLARIFY (30s): Ask about edge cases, constraints");
        System.out.println("2. EXAMPLE (1m): Walk through sample input/output");
        System.out.println("3. APPROACH (2m): Identify pattern and explain strategy");
        System.out.println("4. CODE (10m): Implement while thinking aloud");
        System.out.println("5. TEST (2m): Check with edge cases");
        System.out.println("6. COMPLEXITY (1m): State time/space complexity");
        System.out.println("7. OPTIMIZE (if time): Discuss improvements");
        System.out.println("8. QUESTIONS: Ask about the role/team");
        System.out.println();
        
        System.out.println("🗣️ WHAT TO SAY:");
        System.out.println("• \"This is a [pattern] problem because...\"");
        System.out.println("• \"Time complexity is O(n) because we visit each element once\"");
        System.out.println("• \"Space complexity is O(1) because we use constant extra space\"");
        System.out.println("• \"Let me trace through an example: [1,2,3]...\"");
        System.out.println();
        
        System.out.println("🎯 PATTERN RECOGNITION:");
        System.out.println("• Array pairs/sums → Two Pointers");
        System.out.println("• Subarrays/substrings → Sliding Window");
        System.out.println("• Matrix traversal → DFS/BFS");
        System.out.println("• Sorted array search → Binary Search");
        System.out.println("• All combinations → Backtracking");
        System.out.println("• Optimization problems → Dynamic Programming");
        System.out.println();
        
        System.out.println("🔄 BACKTRACKING RULES:");
        System.out.println("• Use i for reuse allowed (Combination Sum)");
        System.out.println("• Use i+1 for no reuse (Combinations, Subsets)");
        System.out.println("• Choose → Recurse → Unchoose pattern");
        System.out.println("• Mark visited in matrix, restore after recursion");
    }
    
    public static void main(String[] args) {
        System.out.println("🚀 COMPLETE YAHOO INTERVIEW GUIDE 🚀");
        System.out.println("✅ ALL patterns from COMPLETE_INTERVIEW_GUIDE_WITH_COMPLEXITY");
        System.out.println("✅ Every algorithm with detailed complexity analysis");
        System.out.println("✅ Interview strategy and talking points");
        System.out.println("✅ Top 50 most frequent questions covered");
        System.out.println("✅ Ready for tomorrow's coding assessment!");
        
        // Quick functionality tests
        System.out.println("\n=== FUNCTIONALITY TESTS ===");
        System.out.println("Two Sum: " + Arrays.toString(twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println("Valid Parentheses: " + isValid("()[]{}"));
        System.out.println("Max Subarray: " + maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println("Climbing Stairs: " + climbStairs(5));
        System.out.println("Generate Parentheses n=2: " + generateParenthesis(2));
        System.out.println("Combinations C(4,2): " + combine(4, 2));
        
        // Print guides
        printComplexityGuide();
        printInterviewStrategy();
        
        System.out.println();
        System.out.println("🎯 YAHOO INTERVIEW MASTERY ACHIEVED! 🎯");
        System.out.println("💪 All patterns mastered with complexity analysis!");
        System.out.println("🚀 Ready to crush tomorrow's coding assessment!");
        System.out.println();
        System.out.println("📋 FINAL CHECKLIST:");
        System.out.println("✅ Two Pointers, Sliding Window, Binary Search");
        System.out.println("✅ DFS/BFS, Backtracking, Dynamic Programming");
        System.out.println("✅ Advanced patterns: Trapping Water, Word Search");
        System.out.println("✅ All complexities explained and memorized");
        System.out.println("✅ Interview strategy and talking points ready");
        System.out.println();
        System.out.println("🏆 YOU'VE GOT THIS! 🏆");
    }
}