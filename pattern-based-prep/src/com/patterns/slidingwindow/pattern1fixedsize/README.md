# Fixed Size Sliding Window

## 🎯 Pattern Description
Problems where you know the exact window size (k). Use when problem mentions 'size k', 'window of k elements'.

## 📝 Template Approach
**Strategy:** Fixed window: expand to size k, then slide (remove first, add next)
**Time Complexity:** O(n)
**Space Complexity:** O(1)

## 📚 Problems (4)

### AverageOfSubarrayOfSizeK.java
**Difficulty:** Easy
**Description:** Find average of all subarrays of size k
**Key Concept:** Basic sliding window - calculate sum, slide window

### MaxSumSubArrayOfSizeK.java
**Difficulty:** Easy
**Description:** Find maximum sum of subarray of size k
**Key Concept:** Classic sliding window - track maximum sum

### MaximumSlidingWindow.java
**Difficulty:** Hard
**Description:** Find maximum element in each sliding window of size k
**Key Concept:** Use deque to maintain decreasing order of elements

### MaximumCardsSum.java
**Difficulty:** Medium
**Description:** Maximum sum by picking k cards from either end
**Key Concept:** Fixed window with two-pointer approach

## 🚀 Learning Order
**Start with Easy:**
1. AverageOfSubarrayOfSizeK.java
1. MaxSumSubArrayOfSizeK.java

**Then Medium:**
1. MaximumCardsSum.java

**Finally Hard:**
1. MaximumSlidingWindow.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Focus on when to expand vs when to shrink the window
- Practice coding the template from memory

## 🔗 Related Patterns
- Check other sliding window patterns for variations
- Some problems may combine multiple patterns
