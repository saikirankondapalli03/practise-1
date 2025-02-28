package com.educative.patterns.chapter1.window;

import java.util.*;

class LongestSubStringWithOutRepeatingCharacters {
    public static int findLongestSubstring(String str) {
        if (str.length() == 0) {
            return 0;
        }

        int n = str.length();
        int windowStart = 0, longest = 0, windowLength = 0, i = 0;

        Hashtable <Character, Integer> lastSeenAt = new Hashtable <Character, Integer> ();

        for (i = 0; i < n; i++) {
            if (!lastSeenAt.containsKey(str.charAt(i))) {
                lastSeenAt.put(str.charAt(i), i);
            } else { 
                if (lastSeenAt.get(str.charAt(i)) >= windowStart) {
                    windowLength = i - windowStart;
                    if (longest < windowLength) {
                        longest = windowLength;
                    }
                    windowStart = lastSeenAt.get(str.charAt(i)) + 1;
                }
                lastSeenAt.replace(str.charAt(i), i);
            }
        }

        if (longest < i - windowStart) {
            longest = i - windowStart;
        }

        return longest;
    }

    // Driver code
    public static void main(String[] arg) {
        String[] inputs = {
            "abcabcbb",
            "pwwkew",
            "bbbbb",
            "ababababa",
            "",
            "ABCDEFGHI",
            "ABCDEDCBA",
            "AAAABBBBCCCCDDDD"
        };
        for (int i = 0; i < inputs.length; i++) {
            int str = LongestSubStringWithOutRepeatingCharacters.findLongestSubstring(inputs[i]);
            System.out.print(i + 1);
            System.out.println("\tInput string: " + inputs[i]);
            System.out.println("\n\tLength of longest substring: " + str);
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}