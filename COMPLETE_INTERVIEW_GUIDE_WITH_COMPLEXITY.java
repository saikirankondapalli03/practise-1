// ========================================
// COMPLETE CODING INTERVIEW CHEAT SHEET
// ========================================

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
// Variant A: Permutations
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
// Variant B: Combinations
void combine(int n, int k, int start, List<Integer> path, List<List<Integer>> result) {
    if (path.size() == k) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int i = start; i <= n; i++) {
        path.add(i);
        combine(n, k, i + 1, path, result);
        path.remove(path.size() - 1);
    }
}

// Time: O(2^n * n), Space: O(n) for recursion stack
// Variant C: Subsets
void subsets(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
    result.add(new ArrayList<>(path)); // Add current subset
    
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        subsets(nums, i + 1, path, result);
        path.remove(path.size() - 1);
    }
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

// ========== ADDITIONAL PATTERNS ==========

// Time: O(n), Space: O(1)
// 8. FAST & SLOW POINTERS (Cycle Detection)
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}

// Time: O(n log n), Space: O(n)
// 9. MERGE INTERVALS
int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> result = new ArrayList<>();
    
    for (int[] interval : intervals) {
        if (result.isEmpty() || result.get(result.size() - 1)[1] < interval[0]) {
            result.add(interval);
        } else {
            result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], interval[1]);
        }
    }
    return result.toArray(new int[result.size()][]);
}

// Time: O(n log k), Space: O(k)
// 10. TOP K ELEMENTS (Heap)
int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) count.put(num, count.getOrDefault(num, 0) + 1);
    
    PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> count.get(a) - count.get(b));
    
    for (int num : count.keySet()) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();
    }
    
    int[] result = new int[k];
    for (int i = 0; i < k; i++) result[i] = heap.poll();
    return result;
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

// ========== CONFIDENCE BOOSTERS ==========

/*
✅ You understood DFS rainwater trapping (complex!)
✅ You can break down problems step by step
✅ You ask good clarifying questions
✅ You grasp algorithmic concepts quickly
✅ You know time/space complexity for all major patterns

REMEMBER:
- Start with what you know, then optimize
- Think out loud - show your process
- Always mention time/space complexity
- It's okay to start with brute force

YOU'VE GOT THIS! 🚀
*/