# DFS (Depth-First Search) Patterns

## 🎯 Pattern Description
Problems that explore nodes depth-first using recursion or stack. Use when you need to explore all paths, find specific paths, or calculate tree metrics.

## 📝 Template Approach
**Strategy:** Recursively explore left/right subtrees or all neighbors
**Time Complexity:** O(V + E) for graphs, O(n) for trees
**Space Complexity:** O(h) where h is height/depth

## 📚 Problems by Pattern

### Pattern 1: Path Sum (4 problems)
**Use Case:** Calculate sums along tree paths

#### TreePathSum.java
**Difficulty:** Easy
**Description:** Check if path exists with given sum
**Key Concept:** Subtract current value, check if remaining sum exists

#### SumOfPathNumbers.java
**Difficulty:** Medium
**Description:** Sum all root-to-leaf path numbers
**Key Concept:** Build number digit by digit, sum at leaves

#### MaximumPathSum.java
**Difficulty:** Hard
**Description:** Find maximum sum path in binary tree
**Key Concept:** Consider path through current node vs subtree paths

#### TreeNode.java
**Difficulty:** -
**Description:** Tree node utility class
**Key Concept:** Helper class for tree problems

### Pattern 2: Path Sequence (1 problem)
**Use Case:** Find paths matching specific sequences

#### PathWithGivenSequence.java
**Difficulty:** Medium
**Description:** Check if given sequence exists as root-to-leaf path
**Key Concept:** Match sequence elements with path nodes

### Pattern 3: Tree Metrics (1 problem)
**Use Case:** Calculate tree structural properties

#### TreeDiameter.java
**Difficulty:** Medium
**Description:** Find diameter (longest path) of binary tree
**Key Concept:** Max of left height + right height + 1 for each node

## 🚀 Learning Order
**Start with Easy:**
1. TreePathSum.java

**Then Medium:**
1. SumOfPathNumbers.java
2. PathWithGivenSequence.java
3. TreeDiameter.java

**Finally Hard:**
1. MaximumPathSum.java

## 💡 Key Insights
- Read template files (`dfsOnTree.txt`, `dfsOnGraphs.txt`) first
- Master recursive thinking and base cases
- Focus on what to return vs what to pass down
- Practice handling global vs local state

## 🔗 Related Patterns
- BFS (for level-order exploration)
- Backtracking (for path exploration with choices)
- Tree traversal patterns