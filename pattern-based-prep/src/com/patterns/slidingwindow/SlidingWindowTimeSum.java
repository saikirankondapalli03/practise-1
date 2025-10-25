import java.util.*;

public class SlidingWindowTimeSum {
    
    public static List<Integer> getWindowSums(List<int[]> events, int k) {
        List<Integer> result = new ArrayList<>();
        
        // Get all unique seconds to define window start points
        Set<Integer> seconds = new TreeSet<>();
        for (int[] event : events) {
            seconds.add(event[0]);
        }
        
        // For each possible window start
        for (int start : seconds) {
            int windowEnd = start + k - 1;
            int sum = 0;
            
            // Sum counts for events within this window
            for (int[] event : events) {
                int sec = event[0];
                int count = event[1];
                if (sec >= start && sec <= windowEnd) {
                    sum += count;
                }
            }
            result.add(sum);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        List<int[]> events = Arrays.asList(
            new int[]{1, 3},
            new int[]{6, 5},
            new int[]{7, 3}, 
            new int[]{8, 9},
            new int[]{12, 1},
            new int[]{15, 3}
        );
        
        int k = 5;
        List<Integer> sums = getWindowSums(events, k);
        
        System.out.println("Window sums: " + sums);
        
        // Manual verification:
        System.out.println("Window [1-5]: events at 1 -> 3");
        System.out.println("Window [6-10]: events at 6,7,8 -> 5+3+9 = 17"); 
        System.out.println("Window [7-11]: events at 7,8 -> 3+9 = 12");
        System.out.println("Window [8-12]: events at 8,12 -> 9+1 = 10");
        System.out.println("Window [12-16]: events at 12,15 -> 1+3 = 4");
        System.out.println("Window [15-19]: events at 15 -> 3");
    }
}