package com.fidelity_dexian.com;

import java.util.Arrays;
import java.util.List;

/**
 * Coding Challenge 9 from playbook / LeetCode 539 - Minimum Time Difference.
 * Given a list of 24-hour clock times in "HH:mm" format,
 * return the minimum difference in minutes between any two time-points.
 * Time is circular: e.g. "23:59" and "00:00" differ by 1 minute.
 */
public class MinimumTimeDifference {

    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] minutes = new int[n];

        // Convert each "HH:mm" to minutes since midnight
        for (int i = 0; i < n; i++) {
            String t = timePoints.get(i);
            int h = Integer.parseInt(t.substring(0, 2));
            int m = Integer.parseInt(t.substring(3, 5));
            minutes[i] = h * 60 + m;
        }

        Arrays.sort(minutes);

        int minDiff = Integer.MAX_VALUE;

        // Consecutive differences in sorted order
        for (int i = 1; i < n; i++) {
            minDiff = Math.min(minDiff, minutes[i] - minutes[i - 1]);
        }

        // Wrap-around: last time to first time next day
        int wrapAround = 24 * 60 - minutes[n - 1] + minutes[0];
        minDiff = Math.min(minDiff, wrapAround);

        return minDiff;
    }

    public static void main(String[] args) {
        MinimumTimeDifference sol = new MinimumTimeDifference();

        System.out.println(sol.findMinDifference(List.of("23:59", "00:00")));           // 1
        System.out.println(sol.findMinDifference(List.of("00:00", "23:59", "00:00")));   // 0
        System.out.println(sol.findMinDifference(List.of("01:00", "03:00", "12:00")));  // 120
        System.out.println(sol.findMinDifference(List.of("12:00", "12:01", "12:02")));   // 1
    }
}
