package com.fidelity_dexian.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Coding Challenge 3 from playbook: most repeated number(s) in an array.
 * Array iteration - find the number(s) that appear most frequently.
 */
public class MostRepeatedNumbers {

    /**
     * Returns one number that appears most often. If tie, returns the first found.
     */
    public static int mostRepeated(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is null or empty");
        }
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : arr) {
            count.merge(num, 1, Integer::sum);
        }
        int maxCount = 0;
        int result = arr[0];
        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            if (e.getValue() > maxCount) {
                maxCount = e.getValue();
                result = e.getKey();
            }
        }
        return result;
    }

    /**
     * Returns all numbers that appear with the maximum frequency.
     */
    public static List<Integer> mostRepeatedNumbers(int[] arr) {
        if (arr == null || arr.length == 0) {
            return List.of();
        }
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : arr) {
            count.merge(num, 1, Integer::sum);
        }
        int maxCount = 0;
        for (int c : count.values()) {
            if (c > maxCount) maxCount = c;
        }
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            if (e.getValue() == maxCount) {
                result.add(e.getKey());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // [1, 2, 2, 3] → 2
        int[] a1 = { 1, 2, 2, 3 };
        System.out.println("mostRepeated([1,2,2,3]) = " + mostRepeated(a1));
        System.out.println("mostRepeatedNumbers([1,2,2,3]) = " + mostRepeatedNumbers(a1));

        // [1, 1, 2, 2, 3] → tie; one returns 1 or 2, all returns [1, 2]
        int[] a2 = { 1, 1, 2, 2, 3 };
        System.out.println("mostRepeated([1,1,2,2,3]) = " + mostRepeated(a2));
        System.out.println("mostRepeatedNumbers([1,1,2,2,3]) = " + mostRepeatedNumbers(a2));

        // [5] → 5
        int[] a3 = { 5 };
        System.out.println("mostRepeated([5]) = " + mostRepeated(a3));
        System.out.println("mostRepeatedNumbers([5]) = " + mostRepeatedNumbers(a3));

        // [4, 4, 4, 4] → 4
        int[] a4 = { 4, 4, 4, 4 };
        System.out.println("mostRepeated([4,4,4,4]) = " + mostRepeated(a4));
        System.out.println("mostRepeatedNumbers([4,4,4,4]) = " + mostRepeatedNumbers(a4));
    }
}
