/*
package com.patterns.backtracking;

import java.util.*;

*/
/**
 * Complete Coding Interview Cheat Sheet
 * All essential patterns with working implementations
 *//*

public class CompleteInterviewGuide {
    
    // ListNode definition for linked list problems
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

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
    // ========== CORE PATTERNS WITH VARIANTS ==========
    
    // 1. TWO POINTERS
    // Time: O(n), Space: O(1)
    // Variant A: Two Sum (sorted array)
    public static int[] twoSum(int[] arr, int target) {
    // "I'll use two pointers since the array is sorted"
    int windowStart = 0, windowEnd = arr.length - 1;
    
    while (windowStart < windowEnd) {
        int currentSum = arr[windowStart] + arr[windowEnd];
        
        if (currentSum == target) {
            return new int[]{windowStart, windowEnd}; // "Found the pair!"
        } else if (currentSum < target) {
            windowStart++; // "Need larger sum, move left pointer right"
        } else {
            windowEnd--; // "Need smaller sum, move right pointer left"
        }
    }
    return new int[]{-1, -1}; // "No valid pair found"
}

    
    // Time: O(n), Space: O(1)
    // Variant B: Remove duplicates
    public static int removeDuplicates(int[] nums) {
    // "I'll use slow-fast pointers to remove duplicates in-place"
    int writeIndex = 0;
    
    for (int readIndex = 1; readIndex < nums.length; readIndex++) {
        if (nums[readIndex] != nums[writeIndex]) {
            // "Found a new unique element, write it to the next position"
            nums[++writeIndex] = nums[readIndex];
        }
        // "Skip duplicates by just moving read pointer"
    }
    return writeIndex + 1; // "Return length of unique array"
}

    
    // Time: O(n), Space: O(1)
    // Variant C: Palindrome check
    public static boolean isPalindrome(String s) {
    // "I'll use two pointers from both ends moving inward"
    int leftPointer = 0, rightPointer = s.length() - 1;
    
    while (leftPointer < rightPointer) {
        if (s.charAt(leftPointer) != s.charAt(rightPointer)) {
            return false; // "Characters don't match, not a palindrome"
        }
        leftPointer++; rightPointer--; // "Move both pointers inward"
    }
    return true; // "All characters matched, it's a palindrome"
}

    
// 2. SLIDING WINDOW
// Time: O(n), Space: O(1)
// Variant A: Fixed size window
public static int maxSumSubarray(int[] arr, int windowSize) {
    // "I'll use sliding window technique for fixed size subarray"
    int maxSum = 0, currentWindowSum = 0;

    // "First, calculate sum of initial window"
    for (int i = 0; i < windowSize; i++) {
        currentWindowSum += arr[i];
    }
    maxSum = currentWindowSum;

    // "Now slide the window: remove left element, add right element"
    for (int windowEnd = windowSize; windowEnd < arr.length; windowEnd++) {
        int windowStart = windowEnd - windowSize;
        currentWindowSum += arr[windowEnd] - arr[windowStart]; // "Slide operation"
        maxSum = Math.max(maxSum, currentWindowSum); // "Track maximum"
    }
    return maxSum;
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



// 3. BINARY SEARCH
// Time: O(log n), Space: O(1)
// Variant A: Basic binary search
int binarySearch(int[] arr, int target) {
    // "I'll use binary search since array is sorted"
    int leftBound = 0, rightBound = arr.length - 1;
    
    while (leftBound <= rightBound) {
        int midPoint = leftBound + (rightBound - leftBound) / 2; // "Avoid overflow"
        
        if (arr[midPoint] == target) {
            return midPoint; // "Found the target!"
        } else if (arr[midPoint] < target) {
            leftBound = midPoint + 1; // "Target is in right half"
        } else {
            rightBound = midPoint - 1; // "Target is in left half"
        }
    }
    return -1; // "Target not found"
}

// Time: O(log n), Space: O(1)
// Variant B: Find first occurrence
int findFirst(int[] arr, int target) {
    // "I need to find leftmost occurrence, so I'll keep searching left even after finding target"
    int leftBound = 0, rightBound = arr.length - 1, firstIndex = -1;
    
    while (leftBound <= rightBound) {
        int midPoint = leftBound + (rightBound - leftBound) / 2;
        
        if (arr[midPoint] == target) {
            firstIndex = midPoint; // "Save this position"
            rightBound = midPoint - 1; // "But keep searching left for earlier occurrence"
        } else if (arr[midPoint] < target) {
            leftBound = midPoint + 1;
        } else {
            rightBound = midPoint - 1;
        }
    }
    return firstIndex;
}

// Time: O(log n), Space: O(1)
// Variant C: Search in rotated array
int searchRotated(int[] rotatedArray, int target) {
    // "I'll use modified binary search for rotated sorted array"
    int leftBound = 0, rightBound = rotatedArray.length - 1;
    
    while (leftBound <= rightBound) {
        int midPoint = leftBound + (rightBound - leftBound) / 2;
        
        if (rotatedArray[midPoint] == target) {
            return midPoint; // "Found target at middle!"
        }
        
        // "Determine which half is properly sorted"
        if (rotatedArray[leftBound] <= rotatedArray[midPoint]) {
            // "Left half is sorted normally"
            if (target >= rotatedArray[leftBound] && target < rotatedArray[midPoint]) {
                rightBound = midPoint - 1; // "Target is in left sorted half"
            } else {
                leftBound = midPoint + 1; // "Target must be in right half"
            }
        } else {
            // "Right half is sorted normally"
            if (target > rotatedArray[midPoint] && target <= rotatedArray[rightBound]) {
                leftBound = midPoint + 1; // "Target is in right sorted half"
            } else {
                rightBound = midPoint - 1; // "Target must be in left half"
            }
        }
    }
    return -1; // "Target not found in rotated array"
}

// 4. DFS (Matrix/Graph)
// Time: O(m*n), Space: O(m*n) for recursion stack
// Variant A: Count islands
int numIslands(char[][] grid) {
    // "I'll iterate through each cell and start DFS when I find land"
    int islandCount = 0;
    
    for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[0].length; col++) {
            if (grid[row][col] == '1') {
                // "Found unvisited land, start DFS to mark entire island"
                dfsMarkIsland(grid, row, col);
                islandCount++; // "Increment island count"
            }
        }
    }
    return islandCount;
}

void dfsMarkIsland(char[][] grid, int row, int col) {
    // "Check boundaries and if current cell is water or already visited"
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') {
        return;
    }
    
    grid[row][col] = '0'; // "Mark as visited by changing to water"
    
    // "Explore all 4 directions: up, down, left, right"
    dfsMarkIsland(grid, row + 1, col); // Down
    dfsMarkIsland(grid, row - 1, col); // Up
    dfsMarkIsland(grid, row, col + 1); // Right
    dfsMarkIsland(grid, row, col - 1); // Left
}

// Time: O(m*n), Space: O(m*n) for recursion stack
// Variant B: Flood fill
int[][] floodFill(int[][] image, int startRow, int startCol, int newColor) {
    // "I'll use DFS to fill connected pixels of same color"
    int originalColor = image[startRow][startCol];
    
    if (originalColor != newColor) {
        // "Only fill if colors are different to avoid infinite loop"
        dfsFloodFill(image, startRow, startCol, originalColor, newColor);
    }
    return image;
}

void dfsFloodFill(int[][] image, int row, int col, int originalColor, int newColor) {
    // "Check boundaries and if pixel has different color"
    if (row < 0 || row >= image.length || col < 0 || col >= image[0].length || 
        image[row][col] != originalColor) {
        return;
    }
    
    image[row][col] = newColor; // "Fill current pixel with new color"
    
    // "Recursively fill all 4 connected neighbors"
    dfsFloodFill(image, row + 1, col, originalColor, newColor); // Down
    dfsFloodFill(image, row - 1, col, originalColor, newColor); // Up
    dfsFloodFill(image, row, col + 1, originalColor, newColor); // Right
    dfsFloodFill(image, row, col - 1, originalColor, newColor); // Left
}

// 5. BFS (Shortest Path)
// Time: O(m*n), Space: O(m*n) for queue and visited array
int shortestPath(int[][] grid, int[] startPoint, int[] endPoint) {
    // "I'll use BFS since it guarantees shortest path in unweighted graph"
    Queue<int[]> explorationQueue = new LinkedList<>();
    boolean[][] visitedCells = new boolean[grid.length][grid[0].length];
    
    // "Start BFS from source: [row, col, distance]"
    explorationQueue.offer(new int[]{startPoint[0], startPoint[1], 0});
    visitedCells[startPoint[0]][startPoint[1]] = true;
    
    // "Define 4 directions: right, left, down, up"
    int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    
    while (!explorationQueue.isEmpty()) {
        int[] currentCell = explorationQueue.poll();
        int currentRow = currentCell[0], currentCol = currentCell[1], currentDistance = currentCell[2];
        
        // "Check if we reached the destination"
        if (currentRow == endPoint[0] && currentCol == endPoint[1]) {
            return currentDistance; // "Found shortest path!"
        }
        
        // "Explore all 4 neighbors"
        for (int[] direction : directions) {
            int neighborRow = currentRow + direction[0];
            int neighborCol = currentCol + direction[1];
            
            // "Check if neighbor is valid: in bounds, not visited, and walkable"
            if (neighborRow >= 0 && neighborRow < grid.length && 
                neighborCol >= 0 && neighborCol < grid[0].length &&
                !visitedCells[neighborRow][neighborCol] && grid[neighborRow][neighborCol] == 1) {
                
                explorationQueue.offer(new int[]{neighborRow, neighborCol, currentDistance + 1});
                visitedCells[neighborRow][neighborCol] = true; // "Mark as visited"
            }
        }
    }
    return -1; // "No path found"
}

// 6. BACKTRACKING
// Time: O(n! * n), Space: O(n) for recursion stack
// Variant A: Permutations (No reuse)
void permute(int[] nums, List<Integer> currentPath, boolean[] usedElements, List<List<Integer>> allPermutations) {
    // "Base case: if path length equals array length, we have a complete permutation"
    if (currentPath.size() == nums.length) {
        allPermutations.add(new ArrayList<>(currentPath)); // "Add copy to results"
        return;
    }
    
    // "Try each unused element at current position"
    for (int i = 0; i < nums.length; i++) {
        if (usedElements[i]) continue; // "Skip if already used"
        
        // "CHOOSE: Add element to path"
        currentPath.add(nums[i]);
        usedElements[i] = true;
        
        // "RECURSE: Generate permutations with this choice"
        permute(nums, currentPath, usedElements, allPermutations);
        
        // "UNCHOOSE: Backtrack by removing element"
        currentPath.remove(currentPath.size() - 1);
        usedElements[i] = false;
    }
}

// Time: O(C(n,k) * k), Space: O(k) for recursion stack
// Variant B: Combinations (No reuse)
void combine(int n, int k, int startIndex, List<Integer> currentCombination, List<List<Integer>> allCombinations) {
    // "Base case: if we have k elements, we have a complete combination"
    if (currentCombination.size() == k) {
        allCombinations.add(new ArrayList<>(currentCombination));
        return;
    }
    
    // "Try each number from startIndex to n"
    for (int currentNumber = startIndex; currentNumber <= n; currentNumber++) {
        // "CHOOSE: Add current number"
        currentCombination.add(currentNumber);
        
        // "RECURSE: Generate combinations starting from next number (no reuse)"
        combine(n, k, currentNumber + 1, currentCombination, allCombinations);
        
        // "UNCHOOSE: Remove current number for backtracking"
        currentCombination.remove(currentCombination.size() - 1);
    }
}

// Time: O(2^n * n), Space: O(n) for recursion stack
// Variant C: Subsets (No reuse)
void subsets(int[] nums, int startIndex, List<Integer> currentSubset, List<List<Integer>> allSubsets) {
    // "Add current subset to results (including empty subset)"
    allSubsets.add(new ArrayList<>(currentSubset));
    
    // "Try adding each remaining element"
    for (int i = startIndex; i < nums.length; i++) {
        // "CHOOSE: Include current element"
        currentSubset.add(nums[i]);
        
        // "RECURSE: Generate subsets starting from next index (no reuse)"
        subsets(nums, i + 1, currentSubset, allSubsets);
        
        // "UNCHOOSE: Remove current element for backtracking"
        currentSubset.remove(currentSubset.size() - 1);
    }
}

// Time: O(2^target), Space: O(target) for recursion stack
// Variant D: Combination Sum (WITH reuse)
void combinationSum(int[] candidates, int remainingTarget, int startIndex, List<Integer> currentCombination, List<List<Integer>> validCombinations) {
    // "Base case: found valid combination"
    if (remainingTarget == 0) {
        validCombinations.add(new ArrayList<>(currentCombination));
        return;
    }
    // "Pruning: if target becomes negative, no valid solution"
    if (remainingTarget < 0) return;
    
    // "Try each candidate starting from startIndex"
    for (int i = startIndex; i < candidates.length; i++) {
        // "CHOOSE: Add current candidate"
        currentCombination.add(candidates[i]);
        
        // "RECURSE: Same index allows reuse of current element"
        combinationSum(candidates, remainingTarget - candidates[i], i, currentCombination, validCombinations);
        
        // "UNCHOOSE: Remove for backtracking"
        currentCombination.remove(currentCombination.size() - 1);
    }
}

// Time: O(4^n), Space: O(n) for recursion stack
// Variant E: Generate Parentheses
void generateParenthesis(int totalPairs, int openCount, int closeCount, String currentString, List<String> validParentheses) {
    // "Base case: generated string of length 2n"
    if (currentString.length() == 2 * totalPairs) {
        validParentheses.add(currentString); // "Valid parentheses combination"
        return;
    }
    
    // "Add opening parenthesis if we haven't used all n"
    if (openCount < totalPairs) {
        generateParenthesis(totalPairs, openCount + 1, closeCount, currentString + "(", validParentheses);
    }
    
    // "Add closing parenthesis if it won't make string invalid"
    if (closeCount < openCount) {
        generateParenthesis(totalPairs, openCount, closeCount + 1, currentString + ")", validParentheses);
    }
}

// Time: O(4^(m*n)), Space: O(m*n) for recursion stack
// Variant F: Word Search (Matrix backtracking)
boolean exist(char[][] board, String targetWord) {
    // "Try starting the search from each cell in the matrix"
    for (int startRow = 0; startRow < board.length; startRow++) {
        for (int startCol = 0; startCol < board[0].length; startCol++) {
            if (dfsWordSearch(board, targetWord, startRow, startCol, 0)) {
                return true; // "Found the word starting from this position"
            }
        }
    }
    return false; // "Word not found in matrix"
}

boolean dfsWordSearch(char[][] board, String word, int currentRow, int currentCol, int charIndex) {
    // "Base case: matched entire word"
    if (charIndex == word.length()) return true;
    
    // "Check boundaries and character match"
    if (currentRow < 0 || currentRow >= board.length || currentCol < 0 || currentCol >= board[0].length || 
        board[currentRow][currentCol] != word.charAt(charIndex)) {
        return false;
    }
    
    // "CHOOSE: Mark current cell as visited"
    char originalChar = board[currentRow][currentCol];
    board[currentRow][currentCol] = '#';
    
    // "RECURSE: Try all 4 directions for next character"
    boolean wordFound = dfsWordSearch(board, word, currentRow + 1, currentCol, charIndex + 1) || // Down
                       dfsWordSearch(board, word, currentRow - 1, currentCol, charIndex + 1) || // Up
                       dfsWordSearch(board, word, currentRow, currentCol + 1, charIndex + 1) || // Right
                       dfsWordSearch(board, word, currentRow, currentCol - 1, charIndex + 1);   // Left
    
    // "UNCHOOSE: Restore original character for backtracking"
    board[currentRow][currentCol] = originalChar;
    
    return wordFound;
}

// EXAMPLE CALLER - Shows how to use the word search function
boolean wordSearchExample() {
    // "Example: search for 'ABCCED' in this 3x4 matrix"
    char[][] sampleBoard = {
        {'A','B','C','E'}, 
        {'S','F','C','S'}, 
        {'A','D','E','E'}
    };
    String targetWord = "ABCCED";
    
    // "This should return true as the word exists: A(0,0)->B(0,1)->C(0,2)->C(1,2)->E(2,2)->D(2,1)"
    return exist(sampleBoard, targetWord);
}

*/
/*
FLOW EXAMPLE for "ABCCED":
1. Try (0,0)='A' -> matches word[0]
2. Mark '#', try 4 directions for 'B'
3. Find 'B' at (0,1), mark '#', look for 'C'
4. Find 'C' at (0,2), continue path...
5. Complete: A->B->C->C->E->D found!
6. Backtrack: restore all '#' to original chars
*//*


// Time: O(n!), Space: O(n) for recursion stack
// Variant G: N-Queens
void solveNQueens(int boardSize, int currentRow, int[] queenPositions, List<List<String>> allSolutions) {
    // "Base case: placed all queens successfully"
    if (currentRow == boardSize) {
        allSolutions.add(buildBoard(queenPositions, boardSize));
        return;
    }
    
    // "Try placing queen in each column of current row"
    for (int col = 0; col < boardSize; col++) {
        if (isValidQueenPlacement(queenPositions, currentRow, col)) {
            // "CHOOSE: Place queen at this position"
            queenPositions[currentRow] = col;
            
            // "RECURSE: Try to place queens in remaining rows"
            solveNQueens(boardSize, currentRow + 1, queenPositions, allSolutions);
            
            // "UNCHOOSE: Not needed here as position will be overwritten"
        }
    }
}

boolean isValidQueenPlacement(int[] queenPositions, int currentRow, int currentCol) {
    // "Check if placing queen at (currentRow, currentCol) conflicts with previous queens"
    for (int previousRow = 0; previousRow < currentRow; previousRow++) {
        int previousCol = queenPositions[previousRow];
        
        // "Check same column or diagonal attack"
        if (previousCol == currentCol || 
            Math.abs(previousCol - currentCol) == Math.abs(previousRow - currentRow)) {
            return false; // "Conflict found"
        }
    }
    return true; // "Safe to place queen here"
}
boolean isValidQueen(int[] queens, int row, int col) {
    for (int i = 0; i < row; i++) {
        if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
            return false; // Same column or diagonal
        }
    }
    return true;
}
List<String> buildBoard(int[] queenPositions, int boardSize) {
    // "Convert queen positions array to visual board representation"
    List<String> boardVisualization = new ArrayList<>();
    
    for (int row = 0; row < boardSize; row++) {
        StringBuilder rowString = new StringBuilder();
        for (int col = 0; col < boardSize; col++) {
            // "Place 'Q' where queen is positioned, '.' elsewhere"
            if (queenPositions[row] == col) {
                rowString.append('Q'); // "Queen position"
            } else {
                rowString.append('.'); // "Empty cell"
            }
        }
        boardVisualization.add(rowString.toString());
    }
    
    return boardVisualization;
}

// 7. DYNAMIC PROGRAMMING
// Time: O(n), Space: O(1)
// Variant A: Fibonacci/Climbing stairs
int climbStairs(int n) {
    // "Base cases: 1 step = 1 way, 2 steps = 2 ways"
    if (n <= 2) return n;
    
    // "I'll use bottom-up DP with space optimization"
    int twoStepsBack = 1, oneStepBack = 2;
    
    for (int currentStep = 3; currentStep <= n; currentStep++) {
        int waysToCurrentStep = oneStepBack + twoStepsBack; // "Sum of previous two"
        // "Slide the window forward"
        twoStepsBack = oneStepBack;
        oneStepBack = waysToCurrentStep;
    }
    return oneStepBack;
}

// Time: O(amount * coins), Space: O(amount)
// Variant B: Coin change
int coinChange(int[] availableCoins, int targetAmount) {
    // "I'll use DP array where dp[i] = minimum coins needed for amount i"
    int[] minCoinsNeeded = new int[targetAmount + 1];
    Arrays.fill(minCoinsNeeded, targetAmount + 1); // "Initialize with impossible value"
    minCoinsNeeded[0] = 0; // "Base case: 0 coins needed for amount 0"
    
    // "For each amount from 1 to target"
    for (int currentAmount = 1; currentAmount <= targetAmount; currentAmount++) {
        // "Try each coin denomination"
        for (int coinValue : availableCoins) {
            if (coinValue <= currentAmount) {
                // "Update minimum: either current value or using this coin + remaining amount"
                minCoinsNeeded[currentAmount] = Math.min(minCoinsNeeded[currentAmount], 
                                                        minCoinsNeeded[currentAmount - coinValue] + 1);
            }
        }
    }
    
    // "Return result: -1 if impossible, otherwise minimum coins needed"
    return minCoinsNeeded[targetAmount] > targetAmount ? -1 : minCoinsNeeded[targetAmount];
}

// Time: O(m*n), Space: O(m*n)
// Variant C: 2D DP - Unique paths
int uniquePaths(int rows, int cols) {
    // "I'll use 2D DP where dp[i][j] = number of ways to reach cell (i,j)"
    int[][] pathsToCell = new int[rows][cols];
    
    // "Initialize first row: only one way to reach any cell in first row"
    for (int col = 0; col < cols; col++) {
        pathsToCell[0][col] = 1;
    }
    
    // "Initialize first column: only one way to reach any cell in first column"
    for (int row = 0; row < rows; row++) {
        pathsToCell[row][0] = 1;
    }
    
    // "Fill the DP table: paths to current cell = paths from top + paths from left"
    for (int row = 1; row < rows; row++) {
        for (int col = 1; col < cols; col++) {
            pathsToCell[row][col] = pathsToCell[row-1][col] + pathsToCell[row][col-1];
        }
    }
    
    // "Return paths to bottom-right corner"
    return pathsToCell[rows-1][cols-1];
}

// ========== ADDITIONAL CORE PATTERNS ==========

// 8. BUY AND SELL STOCK
// Time: O(n), Space: O(1)
// Variant A: Single transaction (buy once, sell once)
int maxProfit(int[] prices) {
    // "I'll track minimum price seen so far and maximum profit"
    int lowestPriceSoFar = Integer.MAX_VALUE;
    int maxProfitSoFar = 0;
    
    for (int currentPrice : prices) {
        if (currentPrice < lowestPriceSoFar) {
            lowestPriceSoFar = currentPrice; // "Found new minimum buy price"
        } else {
            int profitIfSoldToday = currentPrice - lowestPriceSoFar;
            maxProfitSoFar = Math.max(maxProfitSoFar, profitIfSoldToday); // "Update max profit"
        }
    }
    return maxProfitSoFar;
}

// Time: O(n), Space: O(1)
// Variant B: Multiple transactions (buy/sell multiple times)
int maxProfitMultiple(int[] prices) {
    // "I'll use greedy approach: buy before every price increase, sell before every decrease"
    int totalProfit = 0;
    
    for (int day = 1; day < prices.length; day++) {
        // "If price increased from yesterday, capture that profit"
        if (prices[day] > prices[day-1]) {
            totalProfit += prices[day] - prices[day-1]; // "Add the daily profit"
        }
        // "If price decreased or stayed same, do nothing (don't trade)"
    }
    
    return totalProfit; // "Return total profit from all profitable trades"
}

// 9. GROUP ANAGRAMS
// Time: O(n * k log k), Space: O(n * k) where k is max string length
List<List<String>> groupAnagrams(String[] inputStrings) {
    // "I'll use HashMap where key is sorted string and value is list of anagrams"
    Map<String, List<String>> anagramGroups = new HashMap<>();
    
    for (String currentString : inputStrings) {
        // "Create key by sorting characters of current string"
        char[] characters = currentString.toCharArray();
        Arrays.sort(characters);
        String sortedKey = new String(characters);
        
        // "Add current string to its anagram group"
        anagramGroups.putIfAbsent(sortedKey, new ArrayList<>());
        anagramGroups.get(sortedKey).add(currentString);
    }
    
    // "Return all anagram groups as list of lists"
    return new ArrayList<>(anagramGroups.values());
}

// Time: O(n * k), Space: O(n * k) - Alternative using character count
List<List<String>> groupAnagramsCount(String[] inputStrings) {
    // "Alternative approach: use character frequency as key instead of sorting"
    Map<String, List<String>> anagramGroups = new HashMap<>();
    
    for (String currentString : inputStrings) {
        String frequencyKey = getCharacterFrequency(currentString);
        anagramGroups.putIfAbsent(frequencyKey, new ArrayList<>());
        anagramGroups.get(frequencyKey).add(currentString);
    }
    
    return new ArrayList<>(anagramGroups.values());
}

String getCharacterFrequency(String str) {
    // "Count frequency of each character (a-z)"
    int[] charCount = new int[26];
    for (char c : str.toCharArray()) {
        charCount[c - 'a']++; // "Increment count for this character"
    }
    
    // "Build unique key from character frequencies"
    StringBuilder frequencyKey = new StringBuilder();
    for (int i = 0; i < 26; i++) {
        frequencyKey.append('#').append(charCount[i]); // "Format: #count for each letter"
    }
    return frequencyKey.toString();
}

// ========== SENIOR LEVEL PATTERNS ==========

// 10. MEDIAN OF TWO SORTED ARRAYS (Hard - Heap Approach)
// Time: O((m+n) log(m+n)), Space: O(m+n)
double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
    // "I'll use two heaps: maxHeap for smaller half, minHeap for larger half"
    PriorityQueue<Integer> maxHeapForSmallerHalf = new PriorityQueue<>((a, b) -> b - a);
    PriorityQueue<Integer> minHeapForLargerHalf = new PriorityQueue<>();
    
    // "Add all numbers from both arrays while maintaining heap balance"
    for (int num : firstArray) {
        addNumberToHeaps(num, maxHeapForSmallerHalf, minHeapForLargerHalf);
    }
    for (int num : secondArray) {
        addNumberToHeaps(num, maxHeapForSmallerHalf, minHeapForLargerHalf);
    }
    
    // "Calculate median based on heap sizes"
    if (maxHeapForSmallerHalf.size() == minHeapForLargerHalf.size()) {
        // "Even total count: median is average of two middle elements"
        return (maxHeapForSmallerHalf.peek() + minHeapForLargerHalf.peek()) / 2.0;
    } else {
        // "Odd total count: median is the top of larger heap"
        return maxHeapForSmallerHalf.peek();
    }
}

void addNumberToHeaps(int num, PriorityQueue<Integer> maxHeap, PriorityQueue<Integer> minHeap) {
    // "Always add to maxHeap first, then balance"
    maxHeap.offer(num);
    minHeap.offer(maxHeap.poll()); // "Move largest from maxHeap to minHeap"
    
    // "Ensure maxHeap has equal or one more element than minHeap"
    if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll()); // "Move smallest from minHeap back to maxHeap"
    }
}

// 11. TRAPPING RAIN WATER (Hard)
// Time: O(n), Space: O(1)
int trap(int[] elevationMap) {
    // "I'll use two pointers approach with left and right max tracking"
    int leftPointer = 0, rightPointer = elevationMap.length - 1;
    int leftMaxHeight = 0, rightMaxHeight = 0, totalWaterTrapped = 0;
    
    while (leftPointer < rightPointer) {
        // "Process the side with smaller height (water level limited by smaller side)"
        if (elevationMap[leftPointer] < elevationMap[rightPointer]) {
            // "Process left side"
            if (elevationMap[leftPointer] >= leftMaxHeight) {
                leftMaxHeight = elevationMap[leftPointer]; // "Update left max"
            } else {
                // "Current height is lower than left max, so water can be trapped"
                totalWaterTrapped += leftMaxHeight - elevationMap[leftPointer];
            }
            leftPointer++; // "Move left pointer inward"
        } else {
            // "Process right side"
            if (elevationMap[rightPointer] >= rightMaxHeight) {
                rightMaxHeight = elevationMap[rightPointer]; // "Update right max"
            } else {
                // "Current height is lower than right max, so water can be trapped"
                totalWaterTrapped += rightMaxHeight - elevationMap[rightPointer];
            }
            rightPointer--; // "Move right pointer inward"
        }
    }
    
    return totalWaterTrapped;
}


// ========== INTERVIEW STRATEGY ==========

*/
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
*//*


// ========== WHAT TO SAY FOR EACH PROBLEM TYPE ==========

*/
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
*//*


// ========== BACKTRACKING DECISION TREE ==========

*/
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
*//*


// ========== YAHOO INTERVIEW ESSENTIALS ==========

*/
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
*//*


// ========== TOP 50 MUST-KNOW QUESTIONS (FREQUENCY RANKED) ==========

*/
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
*//*


// ========== FINAL REVISION FOR YAHOO (2:30pm CUTOFF) ==========

*/
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
*//*


// ========== YAHOO INTERVIEW CONFIDENCE ==========

*/
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
