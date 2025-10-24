// ========================================
// COMPLETE CODING INTERVIEW CHEAT SHEET
// ========================================

import java.util.*;

// Note: This is a reference guide, not a complete class
// Add proper class structure and ListNode definition when using

// 🔍 PROBLEM RECOGNITION:
// Array pair/sum → Two Pointers
// Subarray/substring → Sliding Window  
// Matrix traversal → DFS/BFS
// Sorted array search → Binary Search
// All combinations → Backtracking
// Optimization → Dynamic Programming

// ========== CORE PATTERNS WITH VARIANTS ==========

// 1. TWO POINTERS
// Time: O(n), Space: O(1)
// Variant A: Two Sum (sorted array)
int[] twoSum(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int sum = arr[left] + arr[right];
        if (sum == target) return new int[]{left, right};
        else if (sum < target) left++;
        else right--;
    }
    return new int[]{-1, -1};
}

// Time: O(n), Space: O(1)
// Variant B: Remove duplicates
int removeDuplicates(int[] nums) {
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            nums[++slow] = nums[fast];
        }
    }
    return slow + 1;
}

// Time: O(n), Space: O(1)
// Variant C: Palindrome check
boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) return false;
        left++; right--;
    }
    return true;
}

// 2. SLIDING WINDOW
// Time: O(n), Space: O(1)
// Variant A: Fixed size window
int maxSumSubarray(int[] arr, int k) {
    int maxSum = 0, windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += arr[i];
    maxSum = windowSum;
    
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i] - arr[i-k];
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}

// Time: O(n), Space: O(min(m,n)) where m is charset size
// Variant B: Variable size window
int longestSubstringWithoutRepeating(String s) {
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
String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>();
    for (char c : t.toCharArray()) need.put(c, need.getOrDefault(c, 0) + 1);
    
    int left = 0, right = 0, valid = 0, start = 0, len = Integer.MAX_VALUE;
    Map<Character, Integer> window = new HashMap<>();
    
    while (right < s.length()) {
        char c = s.charAt(right++);
        if (need.containsKey(c)) {
            window.put(c, window.getOrDefault(c, 0) + 1);
            if (window.get(c).equals(need.get(c))) valid++;
        }
        
        while (valid == need.size()) {
            if (right - left < len) {
                start = left;
                len = right - left;
            }
            char d = s.charAt(left++);
            if (need.containsKey(d)) {
                if (window.get(d).equals(need.get(d))) valid--;
                window.put(d, window.get(d) - 1);
            }
        }
    }
    return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
}

// 3. BINARY SEARCH
// Time: O(log n), Space: O(1)
// Variant A: Basic binary search
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}

// Time: O(log n), Space: O(1)
// Variant B: Find first occurrence
int findFirst(int[] arr, int target) {
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

// Time: O(log n), Space: O(1)
// Variant C: Search in rotated array
int searchRotated(int[] nums, int target) {
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

// 4. DFS (Matrix/Graph)
// Time: O(m*n), Space: O(m*n) for recursion stack
// Variant A: Count islands
int numIslands(char[][] grid) {
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
void dfsIsland(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') return;
    grid[row][col] = '0'; // Mark as visited
    int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    for (int[] dir : dirs) {
        dfsIsland(grid, row + dir[0], col + dir[1]);
    }
}

// Time: O(m*n), Space: O(m*n) for recursion stack
// Variant B: Flood fill
int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    int originalColor = image[sr][sc];
    if (originalColor != newColor) {
        dfsFloodFill(image, sr, sc, originalColor, newColor);
    }
    return image;
}
void dfsFloodFill(int[][] image, int row, int col, int originalColor, int newColor) {
    if (row < 0 || row >= image.length || col < 0 || col >= image[0].length || 
        image[row][col] != originalColor) return;
    
    image[row][col] = newColor;
    int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    for (int[] dir : dirs) {
        dfsFloodFill(image, row + dir[0], col + dir[1], originalColor, newColor);
    }
}

// 5. BFS (Shortest Path)
// Time: O(m*n), Space: O(m*n) for queue and visited array
int shortestPath(int[][] grid, int[] start, int[] end) {
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[grid.length][grid[0].length];
    
    queue.offer(new int[]{start[0], start[1], 0}); // row, col, distance
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

// 6. BACKTRACKING
// Time: O(n! * n), Space: O(n) for recursion stack
// Variant A: Permutations (No reuse)
void permute(int[] nums, List<Integer> path, boolean[] used, List<List<Integer>> result) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        
        path.add(nums[i]);      // Choose
        used[i] = true;
        permute(nums, path, used, result);  // Recurse
        path.remove(path.size() - 1);       // Unchoose
        used[i] = false;
    }
}

// Time: O(C(n,k) * k), Space: O(k) for recursion stack
// Variant B: Combinations (No reuse)
void combine(int n, int k, int start, List<Integer> path, List<List<Integer>> result) {
    if (path.size() == k) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int i = start; i <= n; i++) {
        path.add(i);
        combine(n, k, i + 1, path, result); // i+1 = no reuse
        path.remove(path.size() - 1);
    }
}

// Time: O(2^n * n), Space: O(n) for recursion stack
// Variant C: Subsets (No reuse)
void subsets(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
    result.add(new ArrayList<>(path)); // Add current subset
    
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        subsets(nums, i + 1, path, result); // i+1 = no reuse
        path.remove(path.size() - 1);
    }
}

// Time: O(2^target), Space: O(target) for recursion stack
// Variant D: Combination Sum (WITH reuse)
void combinationSum(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> result) {
    if (target == 0) {
        result.add(new ArrayList<>(path));
        return;
    }
    if (target < 0) return;
    
    for (int i = start; i < candidates.length; i++) {
        path.add(candidates[i]);
        combinationSum(candidates, target - candidates[i], i, path, result); // i = allow reuse
        path.remove(path.size() - 1);
    }
}

// Time: O(4^n), Space: O(n) for recursion stack
// Variant E: Generate Parentheses
void generateParenthesis(int n, int open, int close, String path, List<String> result) {
    if (path.length() == 2 * n) {
        result.add(path);
        return;
    }
    
    if (open < n) {
        generateParenthesis(n, open + 1, close, path + "(", result);
    }
    if (close < open) {
        generateParenthesis(n, open, close + 1, path + ")", result);
    }
}

// Time: O(4^(m*n)), Space: O(m*n) for recursion stack
// Variant F: Word Search (Matrix backtracking)
boolean exist(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfsWordSearch(board, word, i, j, 0)) return true;
        }
    }
    return false;
}
boolean dfsWordSearch(char[][] board, String word, int row, int col, int index) {
    if (index == word.length()) return true; // Found complete word
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

// EXAMPLE CALLER
boolean wordSearchExample() {
    char[][] board = {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}};
    String word = "ABCCED";
    return exist(board, word);
}

/*
FLOW EXAMPLE for "ABCCED":
1. Try (0,0)='A' -> matches word[0]
2. Mark '#', try 4 directions for 'B'
3. Find 'B' at (0,1), mark '#', look for 'C'
4. Find 'C' at (0,2), continue path...
5. Complete: A->B->C->C->E->D found!
6. Backtrack: restore all '#' to original chars
*/

// Time: O(n!), Space: O(n) for recursion stack
// Variant G: N-Queens
void solveNQueens(int n, int row, int[] queens, List<List<String>> result) {
    if (row == n) {
        result.add(buildBoard(queens, n));
        return;
    }
    
    for (int col = 0; col < n; col++) {
        if (isValidQueen(queens, row, col)) {
            queens[row] = col;  // Choose
            solveNQueens(n, row + 1, queens, result);  // Recurse
            // No need to unchoose, will be overwritten
        }
    }
}
boolean isValidQueen(int[] queens, int row, int col) {
    for (int i = 0; i < row; i++) {
        if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
            return false; // Same column or diagonal
        }
    }
    return true;
}
List<String> buildBoard(int[] queens, int n) {
    List<String> board = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < n; j++) {
            sb.append(queens[i] == j ? 'Q' : '.');
        }
        board.add(sb.toString());
    }
    return board;
}

// 7. DYNAMIC PROGRAMMING
// Time: O(n), Space: O(1)
// Variant A: Fibonacci/Climbing stairs
int climbStairs(int n) {
    if (n <= 2) return n;
    int prev2 = 1, prev1 = 2;
    
    for (int i = 3; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}

// Time: O(amount * coins), Space: O(amount)
// Variant B: Coin change
int coinChange(int[] coins, int amount) {
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

// Time: O(m*n), Space: O(m*n)
// Variant C: 2D DP - Unique paths
int uniquePaths(int m, int n) {
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

// ========== SENIOR LEVEL PATTERNS ==========

// 8. MEDIAN OF TWO SORTED ARRAYS (Hard - Heap Approach)
// Time: O((m+n) log(m+n)), Space: O(m+n)
double findMedianSortedArrays(int[] nums1, int[] nums2) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums1) addNumber(num, maxHeap, minHeap);
    for (int num : nums2) addNumber(num, maxHeap, minHeap);
    
    if (maxHeap.size() == minHeap.size()) {
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    } else {
        return maxHeap.peek();
    }
}
void addNumber(int num, PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap) {
    maxHeap.offer(num);
    minHeap.offer(maxHeap.poll());
    if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll());
    }
}

// 9. TRAPPING RAIN WATER (Hard)
// Time: O(n), Space: O(1)
int trap(int[] height) {
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

// 10. LRU CACHE (Design Pattern)
// Time: O(1) for get/put, Space: O(capacity)
class LRUCache {
    class Node {
        int key, val;
        Node prev, next;
        Node(int key, int val) { this.key = key; this.val = val; }
    }
    
    private Map<Integer, Node> map = new HashMap<>();
    private Node head = new Node(0, 0);
    private Node tail = new Node(0, 0);
    private int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(tail.prev);
        }
        insert(new Node(key, value));
    }
    
    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void insert(Node node) {
        map.put(node.key, node);
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }
}


// ========== INTERVIEW STRATEGY ==========

/*
STEP 1: CLARIFY (30 seconds)
- "Can the array be empty?"
- "Are there negative numbers?"
- "Should I handle duplicates?"

STEP 2: EXAMPLE (1 minute)
- "Let me trace through [1,2,3] with target 5..."
- Draw it out if needed

STEP 3: APPROACH (2 minutes)
- "This looks like a two-pointer problem because..."
- "I'll use DFS to explore connected components"
- "Binary search works since the array is sorted"

STEP 4: CODE (10 minutes)
- Write clean, readable code
- Use meaningful variable names
- Add comments for complex parts

STEP 5: TEST (2 minutes)
- "Let me test with edge cases: empty array, single element..."
- Walk through your code with the example

STEP 6: COMPLEXITY ANALYSIS (1 minute)
- "The time complexity is O(n) because we visit each element once"
- "The space complexity is O(1) since we only use constant extra space"

STEP 7: OPTIMIZE (if time)
- "I can reduce space from O(n) to O(1) by..."
*/

// ========== WHAT TO SAY FOR EACH PROBLEM TYPE ==========

/*
🔍 WHEN YOU SEE... → SAY THIS:

ARRAYS:
• "Find pair with sum X" → "I'll use two pointers - O(n) time, O(1) space"
• "Maximum subarray of size K" → "I'll use sliding window - O(n) time, O(1) space"
• "Remove duplicates" → "I'll use slow/fast pointers - O(n) time, O(1) space"

MATRIX:
• "Count islands" → "I'll use DFS - O(m*n) time, O(m*n) space for recursion"
• "Shortest path" → "I'll use BFS - O(m*n) time, O(m*n) space"
• "Fill region" → "I'll use DFS flood fill - O(m*n) time, O(m*n) space"

SEARCH:
• "Search in sorted array" → "I'll use binary search - O(log n) time, O(1) space"
• "Find first occurrence" → "I'll use modified binary search - O(log n) time, O(1) space"

COMBINATIONS:
• "All permutations" → "I'll use backtracking - O(n!) time, O(n) space"
• "Generate combinations" → "I'll use backtracking - O(C(n,k)) time, O(k) space"
• "Generate subsets" → "I'll use backtracking - O(2^n) time, O(n) space"

OPTIMIZATION:
• "Fibonacci" → "I'll use DP - O(n) time, O(1) space with optimization"
• "Coin change" → "I'll use DP - O(amount * coins) time, O(amount) space"
• "Grid paths" → "I'll use 2D DP - O(m*n) time, O(m*n) space"

OTHER:
• "Detect cycle" → "I'll use fast/slow pointers - O(n) time, O(1) space"
• "Merge intervals" → "I'll sort first - O(n log n) time, O(n) space"
• "Top K elements" → "I'll use min-heap - O(n log k) time, O(k) space"
*/

// ========== BACKTRACKING DECISION TREE ==========

/*
🎯 BACKTRACKING PATTERN RECOGNITION:

"Generate all..." → Backtracking
"Find all combinations..." → Backtracking
"Count ways to..." → Usually DP, but can be backtracking
"Place N items..." → Backtracking (N-Queens)
"Word search in matrix..." → DFS Backtracking

🔄 REUSE vs NO-REUSE DECISION:

USE i (SAME INDEX = REUSE ALLOWED):
• "Elements can be used multiple times"
• "Unlimited use of each element"
• Combination Sum: combinationSum(candidates, target, i, path, result)

USE i+1 (NEXT INDEX = NO REUSE):
• "Each element used at most once"
• "No duplicates in result"
• Combinations: combine(n, k, i + 1, path, result)
• Subsets: subsets(nums, i + 1, path, result)

🗣️ WHAT TO SAY:
• "This is a backtracking problem because we need to generate all possibilities"
• "I'll use the choose-recurse-unchoose pattern"
• "Since elements can be reused, I'll pass the same index i"
• "Since each element is used once, I'll pass i+1"
*/

// ========== YAHOO INTERVIEW ESSENTIALS ==========

/*
🟣 YAHOO FOCUS AREAS:
• String manipulation and parsing
• Array algorithms and optimization
• Tree and graph traversal
• System design basics
• Clean, readable code
• Edge case handling

🔥 MUST PRACTICE FOR TOMORROW (2:30pm cutoff):
1. Two Sum - HashMap O(n)
2. Valid Parentheses - Stack O(n)
3. Longest Substring Without Repeating - Sliding window O(n)
4. Maximum Subarray - Kadane's O(n)
5. Merge Two Sorted Lists - Two pointers O(n)
6. Binary Tree Level Order - BFS O(n)
7. Number of Islands - DFS O(m*n)
8. 3Sum - Two pointers O(n²)
9. Search in Rotated Array - Binary search O(log n)
10. Climbing Stairs - DP O(n)
*/

// ========== TOP 50 MUST-KNOW QUESTIONS (FREQUENCY RANKED) ==========

/*
🔥 ULTRA HIGH FREQUENCY (Asked 80%+ of interviews)
1. Two Sum - HashMap O(n)
2. Valid Parentheses - Stack O(n)
3. Merge Two Sorted Lists - Two pointers O(n)
4. Maximum Subarray - Kadane's O(n)
5. Climbing Stairs - DP O(n)
6. Best Time to Buy/Sell Stock - One pass O(n)
7. Reverse Linked List - Iterative O(n)
8. Contains Duplicate - HashSet O(n)
9. Maximum Depth Binary Tree - DFS O(n)
10. Valid Palindrome - Two pointers O(n)

🔥 HIGH FREQUENCY (Asked 60-80% of interviews)
11. 3Sum - Two pointers O(n²)
12. Container With Most Water - Two pointers O(n)
13. Longest Substring Without Repeating - Sliding window O(n)
14. Add Two Numbers - Linked list O(n)
15. Group Anagrams - HashMap O(n*k log k)
16. Product Array Except Self - Prefix/suffix O(n)
17. Merge Intervals - Sorting O(n log n)
18. Rotate Array - Array manipulation O(n)
19. Number of Islands - DFS O(m*n)
20. Binary Tree Level Order - BFS O(n)

🔥 MEDIUM FREQUENCY (Asked 40-60% of interviews)
21. Search Rotated Sorted Array - Binary search O(log n)
22. Find Minimum in Rotated Array - Binary search O(log n)
23. Validate Binary Search Tree - DFS O(n)
24. Symmetric Tree - DFS O(n)
25. Path Sum - DFS O(n)
26. Minimum Window Substring - Sliding window O(n)
27. Spiral Matrix - Matrix traversal O(m*n)
28. Jump Game - Greedy O(n)
29. Unique Paths - 2D DP O(m*n)
30. Coin Change - DP O(amount * coins)

🔥 IMPORTANT BUT LESS FREQUENT (Asked 20-40%)
31. Trapping Rain Water - Two pointers O(n)
32. Longest Palindromic Substring - Expand centers O(n²)
33. Generate Parentheses - Backtracking O(4^n/√n)
34. Permutations - Backtracking O(n! * n)
35. Subsets - Backtracking O(2^n * n)
36. Word Break - DP O(n²)
37. Course Schedule - Topological sort O(V+E)
38. Clone Graph - DFS O(V+E)
39. LRU Cache - HashMap + DLL O(1)
40. Serialize/Deserialize Binary Tree - DFS O(n)

🔥 ADVANCED (Asked in senior/staff interviews)
41. Median Two Sorted Arrays - Binary search O(log(min(m,n)))
42. Regular Expression Matching - DP O(m*n)
43. Wildcard Pattern Matching - DP O(m*n)
44. Edit Distance - DP O(m*n)
45. Largest Rectangle Histogram - Stack O(n)
46. Maximal Rectangle - Stack O(m*n)
47. Word Ladder - BFS O(M²*N)
48. Alien Dictionary - Topological sort O(C)
49. Critical Connections Network - Tarjan O(V+E)
50. Sliding Window Maximum - Deque O(n)
*/

// ========== FINAL REVISION FOR YAHOO (2:30pm CUTOFF) ==========

/*
🕰️ MEDITATION PREP CHECKLIST (2:30pm - 3:00pm):

✅ TOP 5 PATTERNS TO MEMORIZE:
□ Two Pointers: left=0, right=n-1, move based on condition
□ Sliding Window: expand right, shrink left when invalid
□ DFS: mark visited, recurse neighbors, backtrack
□ Binary Search: mid = left + (right-left)/2
□ HashMap: O(1) lookup, perfect for Two Sum variants

✅ MUST-KNOW IMPLEMENTATIONS:
□ Two Sum: HashMap, O(n) time
□ Valid Parentheses: Stack, push '(' pop ')'
□ Max Subarray: Kadane's, reset sum if negative
□ Merge Lists: Dummy node, compare values
□ Tree Level Order: Queue BFS, size = queue.size()
□ Backtracking: Choose → Recurse → Unchoose pattern
□ Combination Sum: Use i for reuse, i+1 for no reuse

✅ COMPLEXITY CHEAT SHEET:
• O(1): HashMap get/put, array access
• O(log n): Binary search, heap operations
• O(n): Single pass, DFS/BFS
• O(n log n): Sorting, heap with n elements
• O(n²): Nested loops, brute force

✅ WHAT TO SAY:
• "This is a [pattern] problem because..."
• "Time complexity is O(n) because we visit each element once"
• "Space complexity is O(1) because we use constant extra space"
• "Let me trace through an example: [1,2,3]..."

🧘 MEDITATION FOCUS (2:30-3:00pm):
• Breathe deeply, visualize success
• Review the 8-step interview script
• Trust your preparation - you know this!
• Remember: Think out loud, start simple, optimize later
*/

// ========== YAHOO INTERVIEW CONFIDENCE ==========

/*
✅ You have mastered all core patterns
✅ You know the top 10 most frequent questions
✅ You can explain time/space complexity clearly
✅ You have a structured 8-step approach
✅ You understand Yahoo's focus areas

REMEMBER FOR TOMORROW:
- Clarify the problem first (30 seconds)
- Work through an example (1 minute)
- Identify the pattern ("This is a two-pointer problem because...")
- Code step by step while thinking out loud
- Test with edge cases
- Always mention complexity

🕰️ TIMELINE FOR SUCCESS:
• Now - 2:30pm: Practice top 10 questions
• 2:30pm - 3:00pm: Meditate and visualize success
• 3:00pm: CRUSH THE INTERVIEW!

🎯 YOU'RE READY FOR YAHOO! 🚀

• 10 Core patterns: MASTERED
• Essential questions: PRACTICED
• Interview strategy: LOCKED IN
• Confidence level: MAXIMUM

Trust your preparation. You've got this! 💪
*/