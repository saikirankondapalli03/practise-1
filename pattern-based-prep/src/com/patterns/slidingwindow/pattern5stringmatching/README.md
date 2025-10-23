# String Pattern Matching

## 🎯 Pattern Description
Find anagrams, permutations, or pattern matches in strings. Use for 'anagram', 'permutation', 'pattern matching'.

## 📝 Template Approach
**Strategy:** Use character frequency matching with sliding window
**Time Complexity:** O(n)
**Space Complexity:** O(k) for character frequency

## 📚 Problems (4)

### StringAnagrams.java
**Difficulty:** Medium
**Description:** Find all anagrams of pattern in string
**Key Concept:** Match character frequencies in fixed-size window

### StringPermutation.java
**Difficulty:** Medium
**Description:** Check if string contains permutation of pattern
**Key Concept:** Check if any window has same character frequency as pattern

### WordConcatenation.java
**Difficulty:** Hard
**Description:** Find substring that is concatenation of all words
**Key Concept:** Complex pattern matching with word boundaries

### RepeatedDNA.java
**Difficulty:** Medium
**Description:** Find repeated DNA sequences of length 10
**Key Concept:** Fixed window with HashSet to track seen sequences

## 🚀 Learning Order
**Then Medium:**
1. StringAnagrams.java
2. StringPermutation.java
3. RepeatedDNA.java

**Finally Hard:**
1. WordConcatenation.java

## 💡 Key Insights
- Read `TEMPLATE.java` first to understand the pattern
- Master the template before solving problems
- Focus on when to expand vs when to shrink the window
- Practice coding the template from memory

## 🔗 Related Patterns
- Check other sliding window patterns for variations
- Some problems may combine multiple patterns
