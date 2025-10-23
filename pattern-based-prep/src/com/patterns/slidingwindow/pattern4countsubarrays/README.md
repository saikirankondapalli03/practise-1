# Count Subarrays Meeting Condition

## 🎯 Pattern Description
Count number of valid subarrays/substrings. Use when problem asks to 'count subarrays', 'number of subarrays'.

## 📝 Template Approach
**Strategy:** For each right position, count valid subarrays ending at right
**Time Complexity:** O(n)
**Space Complexity:** O(1) or O(k)

## 📚 Problems (2)

### SubarrayProductLessThanK.java
**Difficulty:** Medium
**Description:** Count subarrays with product less than k
**Key Concept:** Shrink when product >= k, count = right - left + 1

### CountNumberSubArraysGivenSum.java
**Difficulty:** Medium
**Description:** Count subarrays with sum equal to k
**Key Concept:** Use prefix sum and HashMap to count occurrences

## 🚀 Learning Order
**Then Medium:**
1. SubarrayProductLessThanK.java
2. CountNumberSubArraysGivenSum.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Focus on when to expand vs when to shrink the window
- Practice coding the template from memory

## 🔗 Related Patterns
- Check other sliding window patterns for variations
- Some problems may combine multiple patterns
