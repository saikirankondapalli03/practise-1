# Same Direction - In-Place Removal

## 🎯 Pattern Description
Fast/slow pointers moving in same direction for in-place array modifications. Use for 'remove', 'in-place' operations.

## 📝 Template Approach
**Strategy:** slow = write position, fast = read position, copy valid elements to slow
**Time Complexity:** O(n)
**Space Complexity:** O(1)

## 📚 Problems (2)

### RemoveDuplicates.java
**Difficulty:** Easy
**Description:** Remove duplicates from sorted array in-place
**Key Concept:** Keep unique elements at front, slow tracks next unique position

### RemoveElement.java
**Difficulty:** Easy
**Description:** Remove all instances of a value in-place
**Key Concept:** Copy non-target elements to front using slow pointer

## 🚀 Learning Order
**Start with Easy:**
1. RemoveDuplicates.java
1. RemoveElement.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Practice pointer movement logic
- Handle edge cases (empty array, single element)

## 🔗 Related Patterns
- Check other two pointers patterns for variations
- Some problems may combine with sliding window techniques
