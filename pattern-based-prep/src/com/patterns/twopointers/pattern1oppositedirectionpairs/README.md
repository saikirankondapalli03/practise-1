# Opposite Direction - Pairs

## 🎯 Pattern Description
Two pointers starting from opposite ends to find pairs or process sorted arrays. Use for 'two sum', 'pair sum', sorted array problems.

## 📝 Template Approach
**Strategy:** left = 0, right = n-1, move pointers based on comparison with target
**Time Complexity:** O(n)
**Space Complexity:** O(1)

## 📚 Problems (2)

### PairWithTargetSum.java
**Difficulty:** Easy
**Description:** Find pair of numbers that sum to target in sorted array
**Key Concept:** Basic two pointers - move left if sum < target, right if sum > target

### SortedArraySquares.java
**Difficulty:** Easy
**Description:** Square elements of sorted array and return in sorted order
**Key Concept:** Handle negatives by comparing absolute values from both ends

## 🚀 Learning Order
**Start with Easy:**
1. PairWithTargetSum.java
1. SortedArraySquares.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Practice pointer movement logic
- Handle edge cases (empty array, single element)

## 🔗 Related Patterns
- Check other two pointers patterns for variations
- Some problems may combine with sliding window techniques
