# Space Complexity: O(min(M,N)) Explanation

## Why O(min(M,N)) for Iterative Approach?

The key insight: **Stack/Queue size depends on island "width" at any given moment**

## Visual Examples

### Example 1: Wide Grid (M=3, N=10)
```
Grid: 3 rows × 10 columns
1 1 1 1 1 1 1 1 1 1
1 0 0 0 0 0 0 0 0 1  
1 1 1 1 1 1 1 1 1 1

Stack progression during DFS:
Step 1: [(0,0)]                    ← 1 element
Step 2: [(0,1), (1,0), (2,0)]     ← 3 elements (limited by M=3)
Step 3: [(0,2), (1,0), (2,1)]     ← 3 elements max
...
Max stack size ≈ M = 3 (smaller dimension)
```

### Example 2: Tall Grid (M=10, N=3)  
```
Grid: 10 rows × 3 columns
1 1 1
1 0 1
1 0 1
1 0 1
1 0 1
1 0 1
1 0 1
1 0 1
1 0 1
1 1 1

Stack progression:
Step 1: [(0,0)]                    ← 1 element
Step 2: [(0,1), (1,0)]            ← 2 elements  
Step 3: [(0,2), (1,0), (2,0)]     ← 3 elements (limited by N=3)
...
Max stack size ≈ N = 3 (smaller dimension)
```

## The Pattern

**Stack size is bounded by the "narrower" dimension** because:
- DFS explores in all 4 directions
- At any level, we can have at most `width` of island in stack
- Width is limited by the smaller grid dimension

## Code Comparison

### Recursive (Current) - O(M×N) worst case
```java
private static int calculateIslandArea(int[][] grid, int row, int col) {
    // Each recursive call uses stack frame
    // Worst case: entire grid is snake-shaped island
    // Stack depth = M × N
    
    if (/* boundary check */) return 0;
    grid[row][col] = 0;
    int area = 1;
    
    // 4 recursive calls - each adds to call stack
    for (int[] dir : DIRECTIONS) {
        area += calculateIslandArea(grid, row + dir[0], col + dir[1]);
    }
    return area;
}
```

### Iterative - O(min(M,N)) average case
```java
private static int calculateIslandAreaIterative(int[][] grid, int startRow, int startCol) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startRow, startCol});
    
    int area = 0;
    
    while (!stack.isEmpty()) {
        int[] current = stack.pop();
        int row = current[0], col = current[1];
        
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
            grid[row][col] == 0) {
            continue;
        }
        
        grid[row][col] = 0;  // Mark visited
        area++;
        
        // Add 4 neighbors to stack (not recursive calls)
        stack.push(new int[]{row + 1, col});
        stack.push(new int[]{row - 1, col});
        stack.push(new int[]{row, col + 1});
        stack.push(new int[]{row, col - 1});
    }
    
    return area;
}
```

## Space Analysis Breakdown

| Approach | Best Case | Average Case | Worst Case |
|----------|-----------|--------------|------------|
| **Recursive** | O(1) | O(√(M×N)) | O(M×N) |
| **Iterative** | O(1) | O(min(M,N)) | O(M×N) |

## Why min(M,N)?

**Intuition**: Island "frontier" width is limited by grid's narrower dimension

### Concrete Example:
```
Grid: 4×100 (M=4, N=100)

Typical island shape:
1 1 1 1 1 1 1 1 1 1 ... (100 columns)
1 0 0 0 0 0 0 0 0 1 ...
1 0 0 0 0 0 0 0 0 1 ...  
1 1 1 1 1 1 1 1 1 1 ...

Stack at any moment contains roughly:
- Cells from current "frontier" 
- Frontier width ≤ 4 (limited by M=4)
- NOT 100 cells (even though N=100)

Therefore: Stack size ≈ O(4) = O(min(4,100)) = O(min(M,N))
```

## When Does O(M×N) Still Occur?

**Snake-shaped island** (worst case for both approaches):
```
1 1 1 1 1
0 0 0 0 1
1 1 1 1 1  
1 0 0 0 0
1 1 1 1 1

Stack must hold entire snake path = O(M×N)
```

## Key Takeaway

- **Recursive**: Always risks O(M×N) due to call stack depth
- **Iterative**: Usually O(min(M,N)) because stack holds "frontier width"
- **Both**: Still O(M×N) worst case for pathological inputs

The iterative approach is **more space-efficient on average** but not asymptotically better in worst case.