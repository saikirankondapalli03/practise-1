# Variable Size - Find Longest Valid Window

## 🎯 Pattern Description
Find maximum length subarray/substring meeting a condition. Use when problem asks for 'longest', 'maximum length'.

## 📝 Template Approach
**Strategy:** Expand window with right pointer, shrink with left when invalid
**Time Complexity:** O(n)
**Space Complexity:** O(k) for tracking state

## 📚 Problems (7)

### LongestSubStringWithOutRepeatingCharacters.java
**Difficulty:** Medium
**Description:** Longest substring without repeating characters
**Key Concept:** Use HashSet to track characters, shrink when duplicate found

### LongestSubstringKDistinct.java
**Difficulty:** Medium
**Description:** Longest substring with at most k distinct characters
**Key Concept:** Use HashMap to count characters, shrink when > k distinct

### MaxFruitCountOf2Types.java
**Difficulty:** Medium
**Description:** Maximum fruits you can collect with at most 2 types
**Key Concept:** Same as k=2 distinct characters problem

### NoRepeatSubstring.java
**Difficulty:** Medium
**Description:** Length of longest substring without repeating characters
**Key Concept:** Variant of longest substring without repeating

### CharacterReplacement.java
**Difficulty:** Medium
**Description:** Longest substring with same character after k replacements
**Key Concept:** Track character frequency, allow k replacements

### ReplacingOnes.java
**Difficulty:** Medium
**Description:** Longest subarray of 1s after replacing k 0s
**Key Concept:** Count zeros in window, shrink when > k zeros

### LongestRepeatingCharacter.java
**Difficulty:** Medium
**Description:** Longest repeating character substring with k replacements
**Key Concept:** Track max frequency, window_size - max_freq <= k

## 🚀 Learning Order
**Then Medium:**
1. LongestSubStringWithOutRepeatingCharacters.java
2. LongestSubstringKDistinct.java
3. MaxFruitCountOf2Types.java
4. NoRepeatSubstring.java
5. CharacterReplacement.java
6. ReplacingOnes.java
7. LongestRepeatingCharacter.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Focus on when to expand vs when to shrink the window
- Practice coding the template from memory

## 🔗 Related Patterns
- Check other sliding window patterns for variations
- Some problems may combine multiple patterns
