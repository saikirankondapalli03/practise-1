# 2D Grid Traversal Problems - Template & Visualization

## Problem Pattern Recognition

Grid traversal problems typically involve:
- **2D matrix/grid** as input
- **Connected components** exploration
- **DFS/BFS** for traversal
- **Boundary checking** for valid moves
- **State modification** (marking visited cells)

---

## Common Problem Types

| Problem | Goal | Modification Strategy |
|---------|------|----------------------|
| **Number of Islands** | Count connected components | Mark visited as '0' |
| **Flood Fill** | Change color of connected region | Change to new color |
| **Max Area of Island** | Find largest connected component | Mark visited, track size |
| **Surrounded Regions** | Capture surrounded regions | Mark boundary-connected as safe |
| **Word Search** | Find word path in grid | Backtrack after exploration |

---

## Universal Template

```java
public class GridTraversalTemplate {
    // Standard 4-directional movement
    private static final int[][] DIRECTIONS = {
        {0, 1}, {0, -1}, {1, 0}, {-1, 0}  // right, left, down, up
    };
    
    public ReturnType solveProblem(DataType[][] grid) {
        // 1. Input validation
        if (grid == null || grid.length == 0) return defaultValue;
        
        // 2. Initialize result variables
        ResultType result = initializeResult();
        
        // 3. Iterate through each cell
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                
                // 4. Check if cell meets starting condition
                if (isValidStartingPoint(grid, row, col)) {
                    
                    // 5. Perform DFS/BFS traversal
                    ComponentResult componentResult = traverse(grid, row, col);
                    
                    // 6. Update global result
                    updateResult(result, componentResult);
                }
            }
        }
        
        return result;
    }
    
    private ComponentResult traverse(DataType[][] grid, int row, int col) {
        // Base case: boundary check + validity check
        if (!isValidCell(grid, row, col)) {
            return baseCase();
        }
        
        // Mark current cell (prevent revisiting)
        markAsVisited(grid, row, col);
        
        // Initialize component result
        ComponentResult result = processCurrentCell(grid, row, col);
        
        // Explore all 4 directions
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            ComponentResult childResult = traverse(grid, newRow, newCol);
            combineResults(result, childResult);
        }
        
        // Optional: backtrack (for problems like Word Search)
        // restoreCell(grid, row, col);
        
        return result;
    }
    
    private boolean isValidCell(DataType[][] grid, int row, int col) {
        return row >= 0 && row < grid.length && 
               col >= 0 && col < grid[0].length && 
               meetsTraversalCondition(grid, row, col);
    }
}
```

---

## Step-by-Step Visualization

### Example: Number of Islands

**Initial Grid:**
```
1 1 0 0 0
1 1 0 0 0  
0 0 1 0 0
0 0 0 1 1
```

**Step 1:** Start at (0,0), find '1' → Island #1 discovered
```
[X] 1 0 0 0    ← Start DFS here
 1  1 0 0 0  
 0  0 1 0 0
 0  0 0 1 1
```

**Step 2:** DFS marks all connected '1's as '0'
```
0 0 0 0 0    ← Island #1 completely marked
0 0 0 0 0  
0 0 1 0 0
0 0 0 1 1
```

**Step 3:** Continue scanning, find '1' at (2,2) → Island #2
```
0 0 0 0 0    
0 0 0 0 0  
0 0 [X] 0 0  ← Start DFS here
0 0  0  1 1
```

**Step 4:** Mark island #2
```
0 0 0 0 0    
0 0 0 0 0  
0 0 0 0 0    ← Island #2 marked
0 0 0 1 1
```

**Step 5:** Find '1' at (3,3) → Island #3
```
0 0 0 0 0    
0 0 0 0 0  
0 0 0 0 0    
0 0 0 [X] 1  ← Start DFS here
```

**Final Result:** 3 islands found

---

## Problem-Specific Adaptations

### 1. Number of Islands
```java
// Condition: cell == '1' (unvisited land)
// Action: mark as '0', increment counter
// Result: total count
```

### 2. Flood Fill
```java
// Condition: cell == originalColor
// Action: change to newColor
// Result: modified grid
```

### 3. Max Area of Island
```java
// Condition: cell == '1'
// Action: mark as '0', count area
// Result: maximum area found
```

### 4. Word Search (with Backtracking)
```java
// Condition: cell == word[index]
// Action: mark as visited, recurse
// Backtrack: restore original value
// Result: boolean (word found)
```

---

## Key Decision Points

### When to use DFS vs BFS?
- **DFS**: Simpler recursion, good for most problems
- **BFS**: When you need shortest path or level-by-level processing

### How to mark visited cells?
- **Destructive**: Modify original grid (Number of Islands)
- **Non-destructive**: Use separate visited array (Word Search)
- **Temporary**: Mark and unmark for backtracking

### Boundary checking patterns:
```java
// Standard bounds check
if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)

// Combined with condition check  
if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || 
    grid[row][col] != targetValue)
```

---

## Time & Space Complexity

- **Time**: O(M × N) - visit each cell once
- **Space**: 
  - O(M × N) - recursion stack in worst case
  - O(1) - if using iterative approach with explicit stack

---

## Common Pitfalls

1. **Forgetting boundary checks** → ArrayIndexOutOfBounds
2. **Not marking visited cells** → Infinite recursion
3. **Wrong base case conditions** → Incorrect results
4. **Modifying grid when backtracking needed** → Lost original state
5. **Using wrong data types** → Type mismatch errors

---

## Practice Problems

1. **Beginner**: Number of Islands, Flood Fill
2. **Intermediate**: Max Area of Island, Surrounded Regions  
3. **Advanced**: Word Search, Word Search II
4. **Expert**: Shortest Path in Binary Matrix, Robot Room Cleaner