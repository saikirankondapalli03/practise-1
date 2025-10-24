# Backtracking Patterns

## 🎯 Core Concept
Explore all possible solutions by making choices and undoing them. **Strategy:** Choose → Explore → Unchoose

**Time Complexity:** O(b^d) where b=branching factor, d=depth  
**Space Complexity:** O(d) for recursion stack

## 📝 Template Structure
```java
void backtrack(parameters) {
    if (condition) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (choice in choices) {
        if (valid choice) {
            path.add(choice);           // CHOOSE
            backtrack(modified_params); // EXPLORE
            path.remove(path.size()-1); // UNCHOOSE
        }
    }
}
```

## 🔄 Three Main Patterns

| Pattern | Base Case | When to Add | Loop | Duplicate Prevention |
|---------|-----------|-------------|------|---------------------|
| **Permutations** | `size == n` | At base case | `0 to n-1` | `used[]` array |
| **Combinations** | `size == k` | At base case | `start to n` | `start` index |
| **Subsets** | None | Every call | `start to n-1` | `start` index |

### Quick Templates

**Permutations (order matters):**
```java
if (path.size() == nums.length) { result.add(new ArrayList<>(path)); return; }
for (int i = 0; i < nums.length; i++) {
    if (used[i]) continue;
    path.add(nums[i]); used[i] = true;
    backtrack(); 
    path.remove(path.size()-1); used[i] = false;
}
```

**Combinations (choose k from n):**
```java
if (path.size() == k) { result.add(new ArrayList<>(path)); return; }
for (int i = start; i <= n; i++) {
    path.add(i);
    backtrack(i + 1); // i+1 prevents duplicates
    path.remove(path.size()-1);
}
```

**Subsets (all sizes):**
```java
result.add(new ArrayList<>(path)); // Add every time
for (int i = start; i < nums.length; i++) {
    path.add(nums[i]);
    backtrack(i + 1);
    path.remove(path.size()-1);
}
```

## 🎪 Interview Recognition
- **"All arrangements"** → Permutations (`used[]`)
- **"Choose k from n"** → Combinations (`start` index)
- **"All subsets"** → Subsets (add every call)

## 📚 Problems by Pattern

### Pattern 1: Combinations (2 problems)
**Use Case:** Generate all valid combinations with constraints

#### AllBrackets.java
**Difficulty:** Medium
**Description:** Generate all valid parentheses combinations
**Key Concept:** Track open/close bracket counts

### Pattern 2: Permutations (1 problem)
**Use Case:** Generate all possible arrangements

#### AllBackTracks.java
**Difficulty:** Medium
**Description:** General permutation problems
**Key Concept:** Swap elements and backtrack

### Pattern 3: Subsets (2 problems)
**Use Case:** Find subsets meeting specific criteria

#### SubsetSum.java
**Difficulty:** Medium
**Description:** Find subsets with target sum
**Key Concept:** Include/exclude each element

#### SubsetSumBackTrack.java
**Difficulty:** Medium
**Description:** Backtracking approach for subset sum
**Key Concept:** Prune invalid branches early

### Pattern 4: Path Finding (3 problems)
**Use Case:** Find paths in trees/graphs with conditions

#### FindAllTreePaths.java
**Difficulty:** Easy
**Description:** Find all root-to-leaf paths
**Key Concept:** DFS with path tracking

#### CountAllPathSum.java
**Difficulty:** Medium
**Description:** Count paths with target sum
**Key Concept:** Track running sum during traversal

#### TreeNode.java
**Difficulty:** -
**Description:** Tree node utility class
**Key Concept:** Helper class for tree problems

### Pattern 5: N-Queens (3 problems)
**Use Case:** Placement problems with conflict constraints

#### NQueens.java
**Difficulty:** Hard
**Description:** Classic N-Queens solution
**Key Concept:** Check diagonal and column conflicts

#### NQueens2.java
**Difficulty:** Hard
**Description:** Alternative N-Queens implementation
**Key Concept:** Optimized conflict checking

#### NQueensGeeksForGeeks.java
**Difficulty:** Hard
**Description:** GeeksforGeeks approach
**Key Concept:** Different implementation style

### Pattern 6: Word Problems (2 problems)
**Use Case:** String/word generation and search

#### PhoneNumber.java
**Difficulty:** Medium
**Description:** Generate phone number letter combinations
**Key Concept:** Map digits to letters, generate all combinations

#### Boggle.java
**Difficulty:** Hard
**Description:** Word search in grid
**Key Concept:** DFS with visited tracking

## 🚀 Learning Path
**Easy:** FindAllTreePaths → **Medium:** AllBrackets, SubsetSum, PhoneNumber → **Hard:** NQueens, Boggle