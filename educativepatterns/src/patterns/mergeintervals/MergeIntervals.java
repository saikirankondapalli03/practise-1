package patterns.mergeintervals;

import java.util.*;

class Interval {
	int start;
	int end;

	public Interval(int start, int end) {
		this.start = start;
		this.end = end;
	}
};

class MergeIntervals {

	public static List<Interval> merge1(List<Interval> intervals) {
		if (intervals.size() < 2)
			return intervals;

		// sort the intervals by start time
		Collections.sort(intervals, (a, b) -> Integer.compare(a.start, b.start));

		List<Interval> mergedIntervals = new LinkedList<Interval>();
		Iterator<Interval> intervalItr = intervals.iterator();
		Interval interval = intervalItr.next();
		int start = interval.start;
		int end = interval.end;

		//1,4
		//2,5
		//3,9
		
		//1,3
		//5,9
		//6,10
		while (intervalItr.hasNext()) {
			interval = intervalItr.next();
			if (interval.start <= end) { // overlapping intervals, adjust the 'end'
				end = Math.max(interval.end, end);
			} else { // non-overlapping interval, add the previous interval and reset
				mergedIntervals.add(new Interval(start, end));
				start = interval.start;
				end = interval.end;
			}
		}
		// add the last interval
		mergedIntervals.add(new Interval(start, end));

		return mergedIntervals;
	}

	public static void main(String[] args) {
		List<Interval> input = new ArrayList<Interval>();
		input.add(new Interval(1, 2));
		input.add(new Interval(3, 5));
		input.add(new Interval(6, 8));
		System.out.print("Merged intervals: ");
		for (Interval interval : MergeIntervals.merge(input))
			System.out.print("[" + interval.start + "," + interval.end + "] ");
		System.out.println();

		input = new ArrayList<Interval>();
		input.add(new Interval(6, 7));
		input.add(new Interval(2, 4));
		input.add(new Interval(5, 9));
		System.out.print("Merged intervals: ");
		for (Interval interval : MergeIntervals.merge(input))
			System.out.print("[" + interval.start + "," + interval.end + "] ");
		System.out.println();

		input = new ArrayList<Interval>();
		input.add(new Interval(1, 4));
		input.add(new Interval(2, 6));
		input.add(new Interval(3, 5));
		System.out.print("Merged intervals: ");
		for (Interval interval : MergeIntervals.merge(input))
			System.out.print("[" + interval.start + "," + interval.end + "] ");
		System.out.println();
	}


	public static List<Interval> merge(List<Interval> intervals) {
		if (intervals.size() < 2) return intervals;

		// Sub-problem 3: Sort by start time
		Collections.sort(intervals, (a, b) -> a.start - b.start);

		List<Interval> result = new ArrayList<>();

		// Start with first interval
		int currentStart = intervals.get(0).start;
		int currentEnd = intervals.get(0).end;

		for (int i = 1; i < intervals.size(); i++) {
			Interval next = intervals.get(i);

			// Sub-problem 1: Check if they overlap
			if (next.start <= currentEnd) {
				// Sub-problem 2: Merge them
				currentEnd = Math.max(currentEnd, next.end);
			} else {
				// No overlap - save current and start new
				result.add(new Interval(currentStart, currentEnd));
				currentStart = next.start;
				currentEnd = next.end;
			}
		}

		// Don't forget the last interval
		result.add(new Interval(currentStart, currentEnd));
		return result;
	}
}
