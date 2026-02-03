package com.fidelity_dexian.com;

/**
 * Coding Challenge 1 from playbook: swap the first character of each pair of strings.
 * Example: "Coffee", "Donuts" → "Doffee", "Conuts"
 */
public class SwapFirstLetters {

    /**
     * Swaps the first character of each pair of strings.
     * Pairs: (0,1), (2,3), ... Odd-length: last string unchanged.
     */
    public static String[] swapFirstLettersInPairs(String[] words) {
        if (words == null || words.length < 2) {
            return words;
        }
        String[] result = words.clone();
        for (int i = 0; i + 1 < result.length; i += 2) {
            String a = result[i];
            String b = result[i + 1];
            if (a.isEmpty() && b.isEmpty()) continue;
            char firstA = a.isEmpty() ? '\0' : a.charAt(0);
            char firstB = b.isEmpty() ? '\0' : b.charAt(0);
            result[i] = (b.isEmpty() ? "" : firstB + a.substring(1));
            result[i + 1] = (a.isEmpty() ? "" : firstA + b.substring(1));
        }
        return result;
    }

    /**
     * Alternative: swap using char arrays.
     */
    public static String[] swapFirstLettersInPairsCharArray(String[] words) {
        if (words == null || words.length < 2) {
            return words;
        }
        String[] result = words.clone();
        for (int i = 0; i + 1 < result.length; i += 2) {
            char[] arr1 = result[i].toCharArray();
            char[] arr2 = result[i + 1].toCharArray();
            if (arr1.length == 0 && arr2.length == 0) continue;
            char t = arr1.length > 0 ? arr1[0] : '\0';
            if (arr2.length > 0) arr1[0] = arr2[0];
            if (arr1.length > 0) arr2[0] = t;
            result[i] = new String(arr1);
            result[i + 1] = new String(arr2);
        }
        return result;
    }

    public static void main(String[] args) {
        // Example from playbook
        String[] words = { "Coffee", "Donuts" };
        String[] swapped = swapFirstLettersInPairs(words);
        System.out.println(String.join(", ", swapped)); // Doffee, Conuts

        // 4–5 strings
        String[] words2 = { "Coffee", "Donuts", "Apple", "Banana" };
        String[] swapped2 = swapFirstLettersInPairs(words2);
        System.out.println(String.join(", ", swapped2)); // Doffee, Conuts, Bapple, Aanana
    }
}
