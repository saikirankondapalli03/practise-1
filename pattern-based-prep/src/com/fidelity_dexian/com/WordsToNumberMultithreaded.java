package com.fidelity_dexian.com;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

/**
 * Coding Challenge 6 from playbook: convert string like "one thousand four fifty"
 * into 1450, using a multithreading concept.
 *
 * <h2>Approach</h2>
 * <ol>
 *   <li><b>Split on "thousand":</b> Input "one thousand four fifty" is split into
 *       two segments: "one" (before thousand) and "four fifty" (after thousand).</li>
 *   <li><b>Parse segments in parallel:</b> Two threads parse the two segments
 *       concurrently via an ExecutorService (fixed thread pool of size 2). Each
 *       segment is parsed by {@link #parseSegment(String)}.</li>
 *   <li><b>Combine:</b> Result = (parsed before-thousand) * 1000 + (parsed after-thousand).
 *       E.g. 1 * 1000 + 450 = 1450. Futures are used to wait for both tasks.</li>
 * </ol>
 *
 * <h3>Segment parsing (parseSegment)</h3>
 * <ul>
 *   <li>Tokenize the segment and map words to numbers (one=1, twenty=20, hundred=100, etc.).</li>
 *   <li><b>"hundred":</b> Multiply current value by 100 (e.g. "four hundred" → 4 then 4*100=400).</li>
 *   <li><b>Tens/teens (20–90, 10–19):</b> Add value to running total (e.g. "fifty" → +50).</li>
 *   <li><b>Ones (1–9):</b> If next word is "hundred", set current = digit (hundred will multiply).
 *       If next is tens/teens, treat digit as hundreds (e.g. "four fifty" → 400+50=450).
 *       Otherwise add digit to total.</li>
 * </ul>
 *
 * <h3>Millions</h3>
 * If the input contains " million ", split on it first. Each part before the last is a
 * million block (parsed and multiplied by 1_000_000); the last part may contain " thousand "
 * and is parsed via {@link #parseThousandPart(String, ExecutorService)}. All parts are
 * parsed in parallel, then summed. E.g. "one million two hundred thousand four fifty"
 * → 1_000_000 + 200_450 = 1_200_450.
 *
 * <h3>Time / space</h3>
 * O(n) over the string length; segments run in parallel (thread pool sized by number of parts).
 */
public class WordsToNumberMultithreaded {

    private static final Map<String, Integer> WORDS = new HashMap<>();

    static {
        WORDS.put("one", 1);
        WORDS.put("two", 2);
        WORDS.put("three", 3);
        WORDS.put("four", 4);
        WORDS.put("five", 5);
        WORDS.put("six", 6);
        WORDS.put("seven", 7);
        WORDS.put("eight", 8);
        WORDS.put("nine", 9);
        WORDS.put("ten", 10);
        WORDS.put("eleven", 11);
        WORDS.put("twelve", 12);
        WORDS.put("thirteen", 13);
        WORDS.put("fourteen", 14);
        WORDS.put("fifteen", 15);
        WORDS.put("sixteen", 16);
        WORDS.put("seventeen", 17);
        WORDS.put("eighteen", 18);
        WORDS.put("nineteen", 19);
        WORDS.put("twenty", 20);
        WORDS.put("thirty", 30);
        WORDS.put("forty", 40);
        WORDS.put("fifty", 50);
        WORDS.put("sixty", 60);
        WORDS.put("seventy", 70);
        WORDS.put("eighty", 80);
        WORDS.put("ninety", 90);
        WORDS.put("hundred", 100);
    }

    /**
     * Parses a segment like "one", "four fifty", or "four hundred fifty" into a number.
     * "four fifty" is interpreted as 450 (four hundred fifty).
     */
    public static int parseSegment(String segment) {
        if (segment == null || segment.isBlank()) return 0;
        String[] tokens = segment.trim().toLowerCase().split("\\s+");
        int current = 0;
        int total = 0;
        for (int i = 0; i < tokens.length; i++) {
            String w = tokens[i];
            if (!WORDS.containsKey(w)) continue;
            int v = WORDS.get(w);
            if (v == 100) {
                current = (current == 0 ? 1 : current) * 100;
            } else if (v >= 20) {
                // tens: twenty, thirty, ..., ninety
                total += current + v;
                current = 0;
            } else if (v >= 10) {
                // teens
                total += current + v;
                current = 0;
            } else {
                // 1-9: if next is "hundred", set current = v (hundred will multiply). 
                // If next is tens/teens (10-99), this digit is hundreds (e.g. "four fifty" -> 400+50).
                boolean nextIsHundred = false;
                boolean nextIsTensOrTeens = false;
                if (i + 1 < tokens.length) {
                    String next = tokens[i + 1];
                    if (WORDS.containsKey(next)) {
                        int nv = WORDS.get(next);
                        if (nv == 100) nextIsHundred = true;
                        else if (nv >= 10 && nv < 100) nextIsTensOrTeens = true;
                    }
                }
                if (nextIsHundred) {
                    current = v;  // "four hundred" -> hundred will do current *= 100
                } else if (nextIsTensOrTeens) {
                    current += v * 100;
                } else {
                    total += current + v;
                    current = 0;
                }
            }
        }
        total += current;
        return total;
    }

    /**
     * Parses a string that may contain " thousand " into a number.
     * E.g. "two hundred thousand four fifty" → 200_000 + 450 = 200_450.
     * Splits on " thousand ", parses both parts in parallel (uses given executor, or a fresh one if null).
     */
    public static long parseThousandPart(String input, ExecutorService executor) throws ExecutionException, InterruptedException {
        if (input == null || input.isBlank()) return 0;
        String normalized = input.trim().toLowerCase();
        String[] parts = normalized.split("\\s+thousand\\s*", 2);
        if (parts.length == 1) {
            return parseSegment(parts[0].trim());
        }
        String before = parts[0].trim();
        String after = parts[1].trim();
        ExecutorService pool = executor;
        boolean ownPool = false;
        if (pool == null) {
            pool = Executors.newFixedThreadPool(2);
            ownPool = true;
        }
        try {
            Future<Integer> left = pool.submit(() -> parseSegment(before));
            Future<Integer> right = pool.submit(() -> parseSegment(after));
            return (long) left.get() * 1000 + right.get();
        } finally {
            if (ownPool) pool.shutdown();
        }
    }

    /**
     * Entry point: "one thousand four fifty" -> 1450, "one million two hundred thousand four fifty" -> 1_200_450.
     * Splits on " million " first (if present), then each part is parsed; parts before the last are
     * million blocks (* 1_000_000), the last part may contain " thousand " and is parsed by parseThousandPart.
     * All parts run in parallel.
     */
    public static long convert(String input) throws ExecutionException, InterruptedException {
        if (input == null || input.isBlank()) return 0;
        String normalized = input.trim().toLowerCase();
        String[] byMillion = normalized.split("\\s+million\\s*", -1);
        int n = byMillion.length;
        if (n == 1) {
            // no "million" -> use thousand-only logic
            ExecutorService executor = Executors.newFixedThreadPool(2);
            try {
                return parseThousandPart(normalized, executor);
            } finally {
                executor.shutdown();
            }
        }
        // 2+ parts: part[0]..part[n-2] are million blocks, part[n-1] may contain " thousand "
        ExecutorService executor = Executors.newFixedThreadPool(Math.max(2, n));
        try {
            List<Future<Long>> futures = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                String segment = byMillion[i].trim();
                futures.add(executor.submit(() -> (long) parseSegment(segment) * 1_000_000));
            }
            String lastPart = byMillion[n - 1].trim();
            // use null so parseThousandPart creates its own pool (avoids deadlock on same executor)
            futures.add(executor.submit(() -> parseThousandPart(lastPart, null)));
            long result = 0;
            for (Future<Long> f : futures) {
                result += f.get();
            }
            return result;
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String input = "one thousand four fifty";
        long result = convert(input);
        System.out.println("\"" + input + "\" -> " + result);  // 1450

        System.out.println(convert("one thousand"));           // 1000
        System.out.println(convert("four fifty"));             // 450
        System.out.println(convert("four hundred fifty"));     // 450
        System.out.println(convert("twenty one"));            // 21

        // Millions
        System.out.println(convert("one million"));                                    // 1_000_000
        System.out.println(convert("one million two hundred thousand four fifty"));     // 1_200_450
        System.out.println(convert("two million three hundred thousand"));             // 2_300_000
    }
}
