# Backtracking Patterns

## 🎯 Pattern Description
Problems that explore all possible solutions by making choices and undoing them. Use when you need to find all solutions or count possibilities.

## 📝 Template Approach
**Strategy:** Choose → Explore → Unchoose (backtrack)
**Time Complexity:** O(b^d) where b=branching factor, d=depth
**Space Complexity:** O(d) for recursion stack

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

## 🚀 Learning Order
**Start with Easy:**
1. FindAllTreePaths.java

**Then Medium:**
1. AllBrackets.java
2. SubsetSum.java
3. CountAllPathSum.java
4. PhoneNumber.java
5. AllBackTracks.java
6. SubsetSumBackTrack.java

**Finally Hard:**
1. NQueens.java
2. Boggle.java
3. NQueens2.java
4. NQueensGeeksForGeeks.java

## 💡 Key Insights
- Read template files (`backtracking-basic.txt`, `backtracking-aggregation.txt`) first
- Master the choose-explore-unchoose pattern
- Focus on base cases and pruning conditions
- Practice visualizing the recursion tree

## 🔗 Related Patterns
- Dynamic Programming (for optimization)
- DFS/BFS (for graph traversal)
- Tree traversal patterns