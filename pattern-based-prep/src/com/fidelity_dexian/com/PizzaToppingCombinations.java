package com.fidelity_dexian.com;

import java.util.ArrayList;
import java.util.List;

/**
 * Pizza topping combinations: choose k from n options (order doesn't matter).
 * Example: 13 toppings, choose 5 → 1287 unique combinations.
 *
 * -----------------------------------------------------------------------------
 * BACKTRACKING TEMPLATE (choose k from n - combinations):
 * -----------------------------------------------------------------------------
 * 1. RESULT   : container for every valid solution (e.g. List<List<Integer>>).
 * 2. CURRENT  : partial solution being built (e.g. List<Integer> of chosen indices).
 * 3. BASE CASE: when current is complete (size == k) → add copy of current to result, return.
 * 4. CHOICES  : for each candidate from start to n (only from "start" so order doesn't matter).
 * 5. CHOOSE   : add candidate to current.
 * 6. RECURSE  : backtrack with next start (e.g. i + 1) so we don't reuse or reorder.
 * 7. UNCHOOSE : remove last element from current (backtrack).
 * -----------------------------------------------------------------------------
 * CORRELATION (this problem: choose 5 toppings from 13):
 *   RESULT   → list of all combinations (each is a list of 5 topping indices).
 *   CURRENT  → one combination being built (indices 1..n).
 *   BASE     → current.size() == 5 → add copy to result.
 *   CHOICES  → for i from start to 13 (start avoids duplicates and enforces order).
 *   CHOOSE   → current.add(i).
 *   RECURSE  → backtrack(..., start = i+1) so we never pick same set in different order.
 *   UNCHOOSE → current.remove(last) before trying next candidate.
 * -----------------------------------------------------------------------------
 * HOW TO EXPLAIN THE APPROACH (interview):
 * -----------------------------------------------------------------------------
 * 1. "We need all ways to choose k items from n where order doesn't matter —
 *     so it's combinations, not permutations."
 *
 * 2. "I'll use backtracking. I keep a 'current' list that holds the indices
 *     we've chosen so far. When current has size k, that's one valid combination —
 *     I add a copy to the result."
 *
 * 3. "To avoid duplicates and keep order consistent, I only consider candidates
 *     from a 'start' index onward. So after picking topping i, I recurse with
 *     start = i+1. That way we never get the same set in a different order."
 *
 * 4. "For each candidate from start to n: I add it to current, recurse, then
 *     remove it (unchoose) so we can try the next candidate. That's the classic
 *     choose — recurse — backtrack pattern."
 *
 * 5. "Time: we generate C(n,k) combinations; space: O(k) for the recursion stack
 *     and current list. If we only needed the count, we could use the formula
 *     C(n,k) = n!/(k!(n-k)!) instead."
 * -----------------------------------------------------------------------------
 */
public class PizzaToppingCombinations {

    public static List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();   // TEMPLATE: result container
        backtrack(result, new ArrayList<>(), n, k, 1);   // TEMPLATE: current = empty, start = 1
        return result;
    }

    private static void backtrack(List<List<Integer>> result,
                                  List<Integer> current,
                                  int n, int k, int start) {
        // ----- TEMPLATE: BASE CASE -----
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        // ----- TEMPLATE: CHOICES (candidates from start to n) -----
        for (int i = start; i <= n; i++) {
            current.add(i);                              // TEMPLATE: CHOOSE
            backtrack(result, current, n, k, i + 1);    // TEMPLATE: RECURSE (next start = i+1)
            current.remove(current.size() - 1);         // TEMPLATE: UNCHOOSE (backtrack)
        }
    }

    public static void main(String[] args) {
        int toppingOptions = 13;
        int choose = 5;
        List<List<Integer>> combos = combinations(toppingOptions, choose);
        System.out.println("Number of combinations: " + combos.size());  // 1287
        // Optional: print first few
        for (int i = 0; i < Math.min(5, combos.size()); i++) {
            System.out.println(combos.get(i));
        }
    }
}
