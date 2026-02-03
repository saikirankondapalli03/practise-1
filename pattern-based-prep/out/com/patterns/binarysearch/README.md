# Binary Search Patterns

## 🎯 Pattern Description
Problems that use divide-and-conquer on sorted data to find targets, boundaries, or optimal values. Use when data is sorted or has monotonic properties.

## 📝 Template Approach
**Strategy:** Divide search space in half based on condition
**Time Complexity:** O(log n)
**Space Complexity:** O(1) iterative, O(log n) recursive

## 📚 Problems by Pattern

### Pattern 1: Basic Search (2 problems)
**Use Case:** Find exact target in sorted arrays

#### BinarySearch.java
**Difficulty:** Easy
**Description:** Classic binary search for target value
**Key Concept:** Basic left/right pointer movement

#### SearchInfiniteSortedArray.java
**Difficulty:** Medium
**Description:** Search in infinite sorted array
**Key Concept:** Find bounds first, then binary search

### Pattern 2: Boundary Problems (5 problems)
**Use Case:** Find first/last occurrence or closest values

#### CeilingOfANumber.java
**Difficulty:** Medium
**Description:** Find smallest number >= target
**Key Concept:** Track potential answer, search for smaller ceiling

#### FloorOfANumber.java
**Difficulty:** Medium
**Description:** Find largest number <= target
**Key Concept:** Track potential answer, search for larger floor

#### NextLetter.java
**Difficulty:** Medium
**Description:** Find next letter greater than target
**Key Concept:** Circular array handling with modulo

#### FindRange.java
**Difficulty:** Medium
**Description:** Find first and last position of target
**Key Concept:** Two binary searches for start and end

#### MinimumDifference.java
**Difficulty:** Medium
**Description:** Find element with minimum difference to target
**Key Concept:** Check neighbors of binary search result

### Pattern 3: Rotated Arrays (4 problems)
**Use Case:** Search in rotated sorted arrays

#### SearchRotatedArray.java
**Difficulty:** Medium
**Description:** Search target in rotated sorted array
**Key Concept:** Identify which half is sorted, search accordingly

#### SearchRotatedWithDuplicate.java
**Difficulty:** Hard
**Description:** Search in rotated array with duplicates
**Key Concept:** Handle duplicates by shrinking search space

#### RotationCountOfRotatedArray.java
**Difficulty:** Medium
**Description:** Find rotation count in rotated array
**Key Concept:** Find minimum element index

#### RotationCountWithDuplicates.java
**Difficulty:** Hard
**Description:** Find rotation count with duplicates
**Key Concept:** Handle duplicates while finding minimum

### Pattern 4: Bitonic Problems (2 problems)
**Use Case:** Search in bitonic (mountain) arrays

#### MaxInBitonicArray.java
**Difficulty:** Easy
**Description:** Find maximum in bitonic array
**Key Concept:** Peak finding using slope comparison

#### SearchBitonicArray.java
**Difficulty:** Medium
**Description:** Search target in bitonic array
**Key Concept:** Find peak, then search both sides

### Pattern 5: Matrix Problems (1 problem)
**Use Case:** Search in 2D sorted structures

#### KthSmallestInSortedMatrix.java
**Difficulty:** Hard
**Description:** Find kth smallest element in sorted matrix
**Key Concept:** Binary search on value range, count elements

## 🚀 Learning Order
**Start with Easy:**
1. BinarySearch.java
2. MaxInBitonicArray.java

**Then Medium:**
1. CeilingOfANumber.java
2. FloorOfANumber.java
3. NextLetter.java
4. FindRange.java
5. MinimumDifference.java
6. SearchInfiniteSortedArray.java
7. SearchRotatedArray.java
8. RotationCountOfRotatedArray.java
9. SearchBitonicArray.java

**Finally Hard:**
1. SearchRotatedWithDuplicate.java
2. RotationCountWithDuplicates.java
3. KthSmallestInSortedMatrix.java

## 💡 Key Insights
- Read template file (`binarysearch.txt`) first
- Master the feasible() function concept
- Focus on loop invariants and boundary conditions
- Practice identifying monotonic properties

## 🔗 Related Patterns
- Two Pointers (for sorted array problems)
- Divide and Conquer algorithms
- Search algorithms