# 🚀 Coding Interview Practice Guide

> **Your complete preparation toolkit for coding interviews**

## 📋 Table of Contents

- [Quick Start](#quick-start)
- [Interview Script](#interview-script)
- [Pattern Recognition](#pattern-recognition)
- [Practice Method](#practice-method)
- [Emergency Phrases](#emergency-phrases)
- [Resources](#resources)

---

## 🎯 Quick Start

### How to Use This Guide
1. **Read the problem** you want to practice
2. **Follow the 8-step script** below OUT LOUD
3. **Time each step** according to guidelines
4. **Practice 2-3 problems** before your interview

### Files in This Repository
- `COMPLETE_INTERVIEW_GUIDE_WITH_COMPLEXITY.java` - All patterns with complexity analysis
- `pattern-based-prep/src/com/patterns/` - Detailed implementations
  - `dfs/RainwaterTrapping.java` - DFS pattern example
  - `dp/UniquePaths.java` - Dynamic Programming example  
  - `bfs/ShortestPath.java` - BFS pattern example

---

## 🎪 Interview Script

### Step 1: Clarify the Problem *(30 seconds)*

**What to say:**
> "Let me make sure I understand the problem correctly..."

**Questions to ask:**
- Can the array be empty? Should I return -1 or 0?
- Are there negative numbers in the input?
- Should I handle duplicate values?
- What's the expected range of input size?
- Are there any constraints on time/space complexity?

**Example:**
> "So I need to find [restate problem]. Let me confirm - can the input be empty? Are there any constraints I should be aware of? Okay, got it."

### Step 2: Work Through Example *(1 minute)*

**What to say:**
> "Let me trace through an example to make sure I understand..."

**Process:**
1. Pick a simple example from the problem
2. Walk through it step by step
3. Show expected output
4. Identify edge cases

**Example:**
> "Let's say we have [1, 2, 3, 4] and target 5. I'd use two pointers - left at 1, right at 4. Sum is 5, which equals target, so I return [0, 3]."

### Step 3: Identify the Pattern *(30 seconds)*

**What to say:**
> "This looks like a [PATTERN] problem because..."

**Pattern phrases:**
- **Two Pointers:** "we need to find pairs in a sorted array"
- **Sliding Window:** "we're looking at subarrays of variable size"
- **DFS:** "we need to explore connected components"
- **BFS:** "we need the shortest path"
- **Binary Search:** "the array is sorted"
- **Backtracking:** "we need to generate all combinations"
- **Dynamic Programming:** "we have overlapping subproblems"

### Step 4: Explain Your Approach *(1 minute)*

**What to say:**
> "Here's my approach..."

**Structure:**
1. High-level strategy
2. Key data structures
3. Main algorithm steps
4. How you handle edge cases

### Step 5: Discuss Complexity *(30 seconds)*

**What to say:**
> "The time complexity is [X] because [reason]. The space complexity is [Y] because [reason]."

**Common phrases:**
- `O(n) time because we visit each element once`
- `O(log n) time because we eliminate half the search space each iteration`
- `O(1) space because we only use constant extra variables`
- `O(n) space because we store results in an array`

### Step 6: Code the Solution *(8-10 minutes)*

**What to say:**
> "Let me implement this step by step..."

**Best practices:**
- Think out loud as you code
- Use meaningful variable names
- Add comments for complex logic
- Handle edge cases explicitly

### Step 7: Test Your Solution *(2 minutes)*

**What to say:**
> "Let me test this with a few examples..."

**Testing approach:**
- Walk through your original example
- Test edge cases (empty input, single element, no solution)
- Trace through the code line by line

### Step 8: Optimize if Needed *(1-2 minutes)*

**What to say:**
> "Can I optimize this further?"

**Consider:**
- Can I reduce time complexity?
- Can I reduce space complexity?
- Are there any redundant operations?

---

## 🔄 Backtracking: Reuse vs No-Reuse Decision

### **🎯 The Critical Choice: `i` vs `i+1`**

**Use `i` (Same Index = Reuse) When:**
- Problem says: "elements can be used multiple times"
- Problem says: "unlimited use of each element" 
- Problem says: "making change with coins"
- **Example:** `backtrack(i, ...)` - allows picking same element again

**Use `i+1` (Next Index = No Reuse) When:**
- Problem says: "each element used at most once"
- Problem says: "choose k from n"
- Problem says: "no duplicates in result"
- **Example:** `backtrack(i+1, ...)` - moves to next element

### **📝 Problem Statement Analysis**

| **Problem Clue** | **Use** | **Reason** |
|------------------|---------|------------|
| "unlimited coins" | `i` | Can reuse same coin |
| "each number once" | `i+1` | No reuse allowed |
| "making change" | `i` | Need multiple of same denomination |
| "choose k from n" | `i+1` | Standard combination |
| "may contain duplicates" | `i+1` + skip | Handle input duplicates |

### **🚨 Special Case: Input Duplicates**
```java
// Skip duplicates at same recursion level
if (i > start && candidates[i] == candidates[i-1]) continue;
```

### **💡 Quick Decision:**
```
Can same element be used multiple times?
├─ YES → Use i (reuse allowed)
│   └─ Coin change, word break
└─ NO → Use i+1 (no reuse)
    └─ Combinations, subsets
```

### **🎪 What to Say:**
- **Reuse:** "Since elements can be reused, I'll pass same index `i`"
- **No Reuse:** "Since each element used once, I'll pass `i+1`"
- **Duplicates:** "I'll sort and skip duplicates at same level"

---

## 🔍 Pattern Recognition

| Problem Type | Pattern | Key Phrase |
|--------------|---------|------------|
| Array pair/sum | Two Pointers | "sorted array, find pairs" |
| Subarray/substring | Sliding Window | "contiguous elements, variable size" |
| Matrix traversal | DFS/BFS | "explore connected components" |
| Sorted array search | Binary Search | "eliminate half search space" |
| All combinations | Backtracking | "generate all possibilities" |
| Optimization | Dynamic Programming | "overlapping subproblems" |

---

## 🎯 Practice Method

### Tonight (30 minutes)
1. **Pick 2-3 problems** from different patterns
2. **Practice the script** OUT LOUD
3. **Time each step** strictly
4. **Record yourself** if possible

### Tomorrow Morning (15 minutes)
1. **Quick review** of this README
2. **Practice one problem** using the script
3. **Go into interview** with confidence

### Self-Practice Tips
- ✅ **Practice OUT LOUD** - Don't just think it, say it!
- ✅ **Time yourself** - Stick to the time limits
- ✅ **Record yourself** - Listen back for improvements
- ✅ **Simulate pressure** - Practice when tired/stressed

---

## 🆘 Emergency Phrases

### When Stuck
- "Let me step back and think about this problem differently..."
- "Can I start with a simpler version of this problem?"
- "What if I trace through a smaller example first?"
- "Let me consider what data structures might be helpful here..."
- "I think there might be a [pattern] approach that could work..."

### Confidence Builders
- "This is a classic [pattern] problem"
- "I've seen similar problems before"
- "The optimal approach here is..."
- "Let me think about the trade-offs..."

### Professional Phrases
- "I'll handle the edge case where..."
- "The time complexity is optimal because..."
- "I'm using this data structure because..."
- "Let me make sure this covers all scenarios..."

---

## 📚 Resources

### Core Files
- [`COMPLETE_INTERVIEW_GUIDE_WITH_COMPLEXITY.java`](./COMPLETE_INTERVIEW_GUIDE_WITH_COMPLEXITY.java) - Complete pattern reference
- [`pattern-based-prep/`](./pattern-based-prep/) - Detailed implementations

### Pattern Examples
- **DFS:** [RainwaterTrapping.java](./pattern-based-prep/src/com/patterns/dfs/RainwaterTrapping.java)
- **DP:** [UniquePaths.java](./pattern-based-prep/src/com/patterns/dp/UniquePaths.java)
- **BFS:** [ShortestPath.java](./pattern-based-prep/src/com/patterns/bfs/ShortestPath.java)

---

## 🎉 You're Ready!

### What You Have
- ✅ **10+ core patterns** with multiple variants
- ✅ **Time/Space complexity** for every approach
- ✅ **Problem recognition** shortcuts
- ✅ **Interview script** with exact phrases
- ✅ **3 detailed implementations** with explanations

### Final Reminders
- **Trust your preparation** - You're more ready than 90% of candidates
- **Think out loud** - Show your problem-solving process
- **Start with what you know** - Even brute force is better than silence
- **Ask questions** - Clarify requirements before coding

---

**Good luck at 3pm EST! You've got this! 🚀**

> *Remember: The interviewer wants to see your thought process, not just the final answer.*