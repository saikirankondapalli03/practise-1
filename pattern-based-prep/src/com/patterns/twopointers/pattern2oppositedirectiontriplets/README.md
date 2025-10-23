# Opposite Direction - Triplets

## 🎯 Pattern Description
Fix one element, use two pointers on remaining array for triplet problems. Use for 'three sum', 'triplet' problems.

## 📝 Template Approach
**Strategy:** for i: fix nums[i], use two pointers on nums[i+1:] to find pairs
**Time Complexity:** O(n²)
**Space Complexity:** O(1) excluding result

## 📚 Problems (3)

### TripletSumToZero.java
**Difficulty:** Medium
**Description:** Find all unique triplets that sum to zero
**Key Concept:** Fix first element, two pointers for remaining, handle duplicates

### TripletSumCloseToTarget.java
**Difficulty:** Medium
**Description:** Find triplet with sum closest to target
**Key Concept:** Track closest sum while using two pointers approach

### TripletWithSmallerSum.java
**Difficulty:** Medium
**Description:** Count triplets with sum smaller than target
**Key Concept:** When sum < target, all pairs (left, left+1..right) are valid

## 🚀 Learning Order
**Then Medium:**
1. TripletSumToZero.java
2. TripletSumCloseToTarget.java
3. TripletWithSmallerSum.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Practice pointer movement logic
- Handle edge cases (empty array, single element)

## 🔗 Related Patterns
- Check other two pointers patterns for variations
- Some problems may combine with sliding window techniques
