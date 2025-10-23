# Merge Intervals Patterns

## 🎯 Pattern Description
Problems involving overlapping intervals that require sorting, merging, or finding intersections. Use when dealing with time ranges, scheduling conflicts, or resource allocation.

## 📝 Template Approach
**Strategy:** Sort intervals by start time, then process based on overlap conditions
**Time Complexity:** O(n log n) for sorting + O(n) for processing
**Space Complexity:** O(n) for result storage

## 📚 Problems by Pattern

### Pattern 1: Basic Merge/Overlap Detection (2 problems)
**Use Case:** Merge overlapping intervals or detect conflicts

#### MergeIntervals.java
**Difficulty:** Medium
**Description:** Merge all overlapping intervals
**Key Concept:** Sort by start, merge when current.start ≤ previous.end

#### ConflictingAppointments.java
**Difficulty:** Easy
**Description:** Check if any appointments conflict
**Key Concept:** Sort by start, conflict when current.start < previous.end

### Pattern 2: Insert/Modify Intervals (1 problem)
**Use Case:** Insert new interval and handle overlaps

#### InsertInterval.java
**Difficulty:** Medium
**Description:** Insert new interval into sorted list and merge
**Key Concept:** Add before, merge overlapping, add remaining

### Pattern 3: Two-Pointer Intersection (1 problem)
**Use Case:** Find intersections between two sorted interval arrays

#### IntervalsIntersection.java
**Difficulty:** Medium
**Description:** Find intersections between two sorted arrays
**Key Concept:** Use two pointers, intersection = max(starts) to min(ends)

### Pattern 4: Active Intervals with Heap (2 problems)
**Use Case:** Track concurrent intervals using priority queue

#### MinimumMeetingRooms.java
**Difficulty:** Hard
**Description:** Find minimum meeting rooms needed
**Key Concept:** Sort by start, use min-heap by end time, track max active

#### MaximumCPULoad.java
**Difficulty:** Hard
**Description:** Find maximum CPU load at any time
**Key Concept:** Sort by start, heap by end time, track cumulative load

### Pattern 5: Multi-List Merge with Heap (1 problem)
**Use Case:** Merge multiple sorted lists to find gaps

#### EmployeeInterval.java
**Difficulty:** Hard
**Description:** Find common free time across all employees
**Key Concept:** Use heap to merge multiple sorted lists, find gaps

## 🚀 Learning Order
**Start with Easy:**
1. ConflictingAppointments.java

**Then Medium:**
1. MergeIntervals.java
2. InsertInterval.java
3. IntervalsIntersection.java

**Finally Hard:**
1. MinimumMeetingRooms.java
2. MaximumCPULoad.java
3. EmployeeInterval.java

## 💡 Key Insights
- Always sort intervals by start time first
- Overlap condition: current.start ≤ previous.end (merge) vs current.start < previous.end (conflict)
- Use heap when tracking active/concurrent intervals
- Event-based processing: create start/end events for point-in-time calculations
- Two-pointer technique for intersection problems

## 🔗 Related Patterns
- Heap/Priority Queue (for active interval tracking)
- Two Pointers (for intersection problems)
- Sorting algorithms (preprocessing step)