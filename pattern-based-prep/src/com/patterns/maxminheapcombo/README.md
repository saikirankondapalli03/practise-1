# Max-Min Heap Combination Pattern

## When to Use This Pattern

**Core Concept**: When you need to **filter by one criteria** and **optimize by another criteria**.

### Recognition Signals

Ask yourself these questions:
1. **Do I need to filter items by some criteria?** → Min/Max heap for filtering
2. **Among filtered items, do I need the best/worst?** → Opposite heap for optimization  
3. **Does the filtering criteria change over time?** → Items move between heaps

### Common Problem Phrases
- "Find the **best** among **eligible** items"
- "**Maximize/minimize** something with **constraints**"
- "Process items in **order** but **optimize** selection"
- "**Dynamic** filtering with **greedy** selection"

## Standard Template

```java
PriorityQueue<T> filterHeap = new PriorityQueue<>(filterComparator);
PriorityQueue<T> optimizeHeap = new PriorityQueue<>(optimizeComparator);

// Initialize - usually put everything in filter heap
for (item : allItems) {
    filterHeap.add(item);
}

// Main processing loop
while (hasMoreWork) {
    // Move eligible items from filter to optimize heap
    while (!filterHeap.isEmpty() && meetsFilterCriteria(filterHeap.peek())) {
        optimizeHeap.add(filterHeap.poll());
    }
    
    // If no eligible items, break or handle
    if (optimizeHeap.isEmpty()) {
        break;
    }
    
    // Pick optimal item and process
    T bestItem = optimizeHeap.poll();
    processItem(bestItem);
}
```

## Common Use Cases

### 1. IPO/Maximize Capital
- **Filter**: Can I afford this project? (min-heap by capital needed)
- **Optimize**: Which gives max profit? (max-heap by profit)

### 2. Find Median from Data Stream  
- **Filter**: Smaller half vs larger half
- **Optimize**: Max of smaller half, min of larger half

### 3. Meeting Rooms with Priorities
- **Filter**: Which meetings can start now? (min-heap by start time)
- **Optimize**: Which has highest priority? (max-heap by priority)

### 4. Task Scheduler with Dependencies
- **Filter**: Which tasks are ready? (min-heap by dependency count)
- **Optimize**: Which has highest priority? (max-heap by priority)

## Key Insights

- This pattern appears in **15-20% of heap problems**
- Strong signal for **greedy + heap** solutions
- Items **flow** from filter heap → optimize heap as conditions change
- Usually involves **two different sorting criteria**

## Implementation Tips

1. **Choose heap types carefully**: 
   - Filter heap: Sort by constraint (usually min-heap)
   - Optimize heap: Sort by objective (usually max-heap for maximization)

2. **Movement direction**: Items typically flow from filter → optimize (rarely backwards)

3. **Edge cases**: Always check if optimize heap is empty before polling

4. **Time complexity**: Usually O(n log n) due to heap operations