package com.fidelity_dexian.com;

/**
 * Coding Challenge 10 from playbook: Evaluate expression string with + and - only, using recursion.
 * Examples: "1+2+3+5" -> 11, "1-6++5-6" -> -6 (++ normalized to +).
 *
 * ---------------------------------------------------------------------------
 * APPROACH
 * ---------------------------------------------------------------------------
 * 1. Normalize the string first so we don't have consecutive operators:
 *    ++ -> +, +- -> -, -+ -> -, -- -> +
 *    This handles inputs like "1-6++5-6" so recursion sees single operators.
 *
 * 2. Recursive evaluation (eval(s, start, end)):
 *    - Base case: if there is no '+' or '-' in the range (after skipping leading sign),
 *      the range is a single number -> parse and return it.
 *    - Recursive case: find the LAST '+' or '-' in the range (the "top-level" operator).
 *      Split there: left = eval(start, lastOp), right = eval(lastOp+1, end).
 *      Return left + right or left - right.
 *
 * 3. Using the LAST operator ensures left-to-right evaluation:
 *    e.g. "1+2+3+5" splits as (1+2+3) + 5 -> (1+2)+3 and 5 -> ... = 11.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLE WALKTHROUGH: "1+2+3+5"
 * ---------------------------------------------------------------------------
 * After normalize: "1+2+3+5" (unchanged).
 *
 * eval("1+2+3+5", 0, 9):
 *   - Last '+' or '-' in range is at index 7 (the one before 5). So lastOp = 7.
 *   - Split: left = eval("1+2+3", 0, 7),  op = '+',  right = eval("5", 8, 9).
 *
 * eval("5", 8, 9): no operator in range -> return 5.  (base case)
 *
 * eval("1+2+3", 0, 7):
 *   - Last operator at index 5 (before 3). Split: left = eval("1+2", 0, 5), right = eval("3", 6, 7).
 *   - eval("3", ...) -> 3.
 *   - eval("1+2", 0, 5): last op at index 3. left = eval("1", ...) -> 1, right = eval("2", ...) -> 2. Return 1+2 = 3.
 *   - So eval("1+2+3", ...) = 3 + 3 = 6.
 *
 * Back to top: left = 6, right = 5, op = '+'. Result = 6 + 5 = 11.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLE: "1-6++5-6"
 * ---------------------------------------------------------------------------
 * After normalize: "1-6++5-6" -> "1-6+5-6" (++ becomes +).
 *
 * eval("1-6+5-6", 0, 9):
 *   - Last op at index 7 (the '-' before last 6). left = eval("1-6+5", 0, 7), right = eval("6", 8, 9) = 6.
 *   - eval("1-6+5", ...): last op at index 5 ('+'). left = eval("1-6", ...), right = eval("5", ...) = 5.
 *   - eval("1-6", ...): last op at index 2 ('-'). left = 1, right = 6. Return 1-6 = -5.
 *   - So eval("1-6+5", ...) = -5 + 5 = 0.
 *   - Top: left = 0, right = 6, op = '-'. Result = 0 - 6 = -6.
 *
 * ---------------------------------------------------------------------------
 * Time: O(n^2) in worst case (scan for last op each time). Can be O(n) with one pass.
 * Space: O(n) for recursion stack.
 */
public class EvaluateExpressionRecursive {

    /**
     * Normalize consecutive operators: ++ -> +, +- -> -, -+ -> -, -- -> +.
     */
    private static String normalize(String s) {
        if (s == null || s.isEmpty()) return s;
        String t = s.trim();
        while (true) {
            String u = t.replace("++", "+").replace("+-", "-").replace("-+", "-").replace("--", "+");
            if (u.equals(t)) break;
            t = u;
        }
        return t;
    }

    public int evaluate(String expression) {
        if (expression == null) return 0;
        String s = normalize(expression);
        if (s.isEmpty()) return 0;
        return eval(s, 0, s.length());
    }

    private int eval(String s, int start, int end) {
        while (start < end && (s.charAt(start) == '+' || s.charAt(start) == '-')) start++;
        if (start >= end) return 0;

        int lastOp = -1;
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            if (c == '+' || c == '-') lastOp = i;
        }

        if (lastOp == -1) {
            return Integer.parseInt(s.substring(start, end).trim());
        }

        int left = eval(s, start, lastOp);
        char op = s.charAt(lastOp);
        int right = eval(s, lastOp + 1, end);

        return op == '+' ? left + right : left - right;
    }

    public static void main(String[] args) {
        EvaluateExpressionRecursive sol = new EvaluateExpressionRecursive();

        System.out.println(sol.evaluate("1+2+3+5"));       // 11
        System.out.println(sol.evaluate("1-6+5-6"));      // -6
        System.out.println(sol.evaluate("1-6++5-6"));     // -6 (++ normalized to +)
        System.out.println(sol.evaluate("42"));            // 42
        System.out.println(sol.evaluate("10-2-3"));      // 5
        System.out.println(sol.evaluate("0"));             // 0
    }
}
