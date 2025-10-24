# Max-Min Heap Combination - Visual Template

## Core Concept Visualization

```
┌─────────────────┐    FILTER     ┌─────────────────┐    OPTIMIZE    ┌─────────────┐
│   All Items     │ ──────────────▶│  Eligible Items │ ──────────────▶│   Result    │
│                 │   (Criteria)   │                 │  (Best/Worst)  │             │
└─────────────────┘                └─────────────────┘                └─────────────┘
        │                                   │                               ▲
        │                                   │                               │
        ▼                                   ▼                               │
┌─────────────────┐                ┌─────────────────┐                      │
│   Filter Heap   │                │  Optimize Heap  │──────────────────────┘
│  (Min/Max by    │                │  (Max/Min by    │
│   constraint)   │                │   objective)    │
└─────────────────┘                └─────────────────┘
```

## Template Flow Diagram

```
START
  │
  ▼
┌─────────────────────────────────┐
│ Initialize Two Heaps:           │
│ • filterHeap (by constraint)    │
│ • optimizeHeap (by objective)   │
└─────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────┐
│ Put all items in filterHeap     │
└─────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────┐
│ MAIN LOOP:                      │
│ While (hasMoreWork)             │
└─────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────┐
│ STEP 1: Move Eligible Items     │
│ filterHeap ──▶ optimizeHeap     │
│ (while meets criteria)          │
└─────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────┐
│ STEP 2: Check Available         │
│ if (optimizeHeap.isEmpty())     │
│    break                        │
└─────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────┐
│ STEP 3: Pick Optimal            │
│ bestItem = optimizeHeap.poll()  │
│ processItem(bestItem)           │
└─────────────────────────────────┘
  │
  ▼
END
```

## Example: IPO Problem Visualization

### Initial State
```
Money: $5

Filter Heap (Min by Capital):     Optimize Heap (Max by Profit):
┌─────────────────────────────┐   ┌─────────────────────────────┐
│ Project A: Need $2 → $3     │   │                             │
│ Project B: Need $4 → $8     │   │         EMPTY               │
│ Project C: Need $6 → $10    │   │                             │
└─────────────────────────────┘   └─────────────────────────────┘
```

### Round 1: Move Affordable Projects
```
Money: $5

Filter Heap:                      Optimize Heap:
┌─────────────────────────────┐   ┌─────────────────────────────┐
│ Project C: Need $6 → $10    │   │ Project B: Need $4 → $8     │
│                             │   │ Project A: Need $2 → $3     │
│         (Can't afford)      │   │      (Can afford both)      │
└─────────────────────────────┘   └─────────────────────────────┘
                                           │
                                           ▼
                                    Pick Project B ($8 profit)
```

### After Round 1
```
Money: $5 + $8 = $13

Filter Heap:                      Optimize Heap:
┌─────────────────────────────┐   ┌─────────────────────────────┐
│                             │   │ Project A: Need $2 → $3     │
│           EMPTY             │   │                             │
│                             │   │                             │
└─────────────────────────────┘   └─────────────────────────────┘

Project C moves: Need $6 ≤ $13 ✓
```

### Round 2: Final Selection
```
Money: $13

Optimize Heap:
┌─────────────────────────────┐
│ Project C: Need $6 → $10    │
│ Project A: Need $2 → $3     │
└─────────────────────────────┘
         │
         ▼
  Pick Project C ($10 profit)

Final Money: $13 + $10 = $23
```

## Mental Model

Think of it as a **two-stage pipeline**:

1. **Stage 1 (Filter Heap)**: "Waiting room" - items that don't meet criteria yet
2. **Stage 2 (Optimize Heap)**: "Ready room" - items that meet criteria, sorted by preference
3. **Flow**: Items move from waiting → ready as conditions change
4. **Selection**: Always pick the best from ready room

## Key Visual Patterns

### Pattern 1: One-Way Flow
```
Filter Heap ──────▶ Optimize Heap ──────▶ Result
   (Wait)              (Ready)           (Process)
```

### Pattern 2: Dynamic Movement
```
Time 1: [A,B,C] ──▶ []
Time 2: [C] ──▶ [A,B]     (A,B became eligible)
Time 3: [] ──▶ [A,C]      (B processed, C became eligible)
```

### Pattern 3: Different Sorting
```
Filter:   Sort by CONSTRAINT  (when can I use this?)
Optimize: Sort by OBJECTIVE   (which should I pick?)
```

This visualization helps you **see the pattern** rather than memorize code!