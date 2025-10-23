# BFS (Breadth-First Search) Patterns

## 🎯 Pattern Description
Problems that explore nodes level by level using a queue. Use when you need shortest path, level-order processing, or minimum steps.

## 📝 Template Approach
**Strategy:** Use queue to process nodes level by level
**Time Complexity:** O(V + E) for graphs, O(n) for trees
**Space Complexity:** O(w) where w is maximum width

## 📚 Problems by Pattern

### Pattern 1: Level Order Traversal (6 problems)
**Use Case:** Process tree nodes level by level

#### LevelOrderTraversal.java
**Difficulty:** Easy
**Description:** Basic level-order traversal of binary tree
**Key Concept:** Queue-based BFS, process each level

#### ReverseLevelOrderTraversal.java
**Difficulty:** Easy
**Description:** Level-order traversal from bottom to top
**Key Concept:** Use stack or reverse the result

#### ZigzagTraversal.java
**Difficulty:** Medium
**Description:** Alternate left-to-right and right-to-left traversal
**Key Concept:** Use flag to alternate direction per level

#### LevelAverage.java
**Difficulty:** Easy
**Description:** Calculate average of each level in binary tree
**Key Concept:** Sum nodes at each level, divide by count

#### LevelOrderSuccessor.java
**Difficulty:** Medium
**Description:** Find level-order successor of given node
**Key Concept:** BFS until target found, return next node

#### TreeNode.java
**Difficulty:** -
**Description:** Tree node utility class
**Key Concept:** Helper class for tree problems

### Pattern 2: Tree Depth (2 problems)
**Use Case:** Find minimum or maximum depth using BFS

#### MaximumBinaryTreeDepthBFS.java
**Difficulty:** Easy
**Description:** Find maximum depth using BFS approach
**Key Concept:** Count levels during BFS traversal

#### MinimumBinaryTreeDepth.java
**Difficulty:** Easy
**Description:** Find minimum depth to leaf node
**Key Concept:** Return depth when first leaf is found

### Pattern 3: Tree Connections (2 problems)
**Use Case:** Connect nodes at same level

#### ConnectLevelOrderSiblings.java
**Difficulty:** Medium
**Description:** Connect nodes at same level with next pointer
**Key Concept:** Process level by level, connect adjacent nodes

#### ConnectAllSiblings.java
**Difficulty:** Medium
**Description:** Connect all nodes in level-order sequence
**Key Concept:** Connect each node to next in BFS order

### Pattern 4: Tree Views (1 problem)
**Use Case:** Get specific view of tree structure

#### RightViewTree.java
**Difficulty:** Medium
**Description:** Get right side view of binary tree
**Key Concept:** Last node at each level forms right view

## 🚀 Learning Order
**Start with Easy:**
1. LevelOrderTraversal.java
2. ReverseLevelOrderTraversal.java
3. LevelAverage.java
4. MaximumBinaryTreeDepthBFS.java
5. MinimumBinaryTreeDepth.java

**Then Medium:**
1. ZigzagTraversal.java
2. LevelOrderSuccessor.java
3. ConnectLevelOrderSiblings.java
4. ConnectAllSiblings.java
5. RightViewTree.java

## 💡 Key Insights
- Read template files (`bfsOnTree.txt`, `bfsOnGraphs.txt`, `bfsOnMatrix.txt`) first
- Master queue operations and level processing
- Focus on when to process entire level vs individual nodes
- Practice tracking level boundaries in queue

## 🔗 Related Patterns
- DFS (for depth-first exploration)
- Tree traversal patterns
- Graph algorithms (shortest path)