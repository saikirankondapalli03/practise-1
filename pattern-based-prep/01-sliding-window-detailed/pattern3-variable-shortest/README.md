# Variable Size - Find Shortest Valid Window

## 🎯 Pattern Description
Find minimum length subarray/substring meeting a condition. Use when problem asks for 'shortest', 'minimum length'.

## 📝 Template Approach
**Strategy:** Expand until valid, then shrink while maintaining validity
**Time Complexity:** O(n)
**Space Complexity:** O(k) for tracking state

## 📚 Problems (4)

### MinimumWindowSubstring.java
**Difficulty:** Hard
**Description:** Minimum window substring containing all characters of pattern
**Key Concept:** Expand until all chars covered, shrink while maintaining coverage

### MinSizeSubArraySum.java
**Difficulty:** Medium
**Description:** Minimum length subarray with sum >= target
**Key Concept:** Expand until sum >= target, shrink while maintaining sum

### MinSizeSubArraySum2.java
**Difficulty:** Medium
**Description:** Variant of minimum subarray sum problem
**Key Concept:** Same concept with different implementation

### MinimumWindowSubstring2.java
**Difficulty:** Hard
**Description:** Another variant of minimum window substring
**Key Concept:** Alternative implementation of minimum window

## 🚀 Learning Order
**Then Medium:**
1. MinSizeSubArraySum.java
2. MinSizeSubArraySum2.java

**Finally Hard:**
1. MinimumWindowSubstring.java
2. MinimumWindowSubstring2.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Focus on when to expand vs when to shrink the window
- Practice coding the template from memory

## 🔗 Related Patterns
- Check other sliding window patterns for variations
- Some problems may combine multiple patterns
