package com.patterns.mergeintervals.pattern6_multi_list_merge;

import com.patterns.mergeintervals.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class EmployeeInterval {
	Interval interval; // Interval representing employee's working hours
	int empIndex; // index of the list containing working hours of this employee
	int empIntIndex; // index of the Interval in the employee list

	public EmployeeInterval() {
		
	}
	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public int getEmployeeIndex() {
		return empIndex;
	}

	public void setEmployeeIndex(int employeeIndex) {
		this.empIndex = employeeIndex;
	}

	public int getIntervalIndex() {
		return empIntIndex;
	}

	public void setIntervalIndex(int intervalIndex) {
		this.empIntIndex = intervalIndex;
	}

	public EmployeeInterval(Interval Interval, int employeeIndex, int IntervalIndex) {
		this.interval = Interval;
		this.empIndex = employeeIndex;
		this.empIntIndex = IntervalIndex;
	}
};

class EmployeeFreeTime {

	
	public static List<Interval> findEmployeeFreeTime(List<List<Interval>> schedule) {
		// Step 1: Flatten all intervals into one list
		List<Interval> allIntervals = new ArrayList<>();
		for (List<Interval> empSchedule : schedule) {
			allIntervals.addAll(empSchedule);
		}
		// Step 2: Sort by start time
		allIntervals.sort((a, b) -> a.start - b.start);

		// Step 3: Merge overlapping intervals
		List<Interval> merged = new ArrayList<>();
		for (Interval interval : allIntervals) {
			// No overlap: current interval starts after last merged interval ends
			if (merged.isEmpty() || merged.get(merged.size() - 1).end < interval.start) {
				merged.add(interval); // Add as new interval
			} else {
				// Overlap: extend the last merged interval's end time
				merged.get(merged.size() - 1).end = Math.max(merged.get(merged.size() - 1).end, interval.end);
			}
		}

		// Step 4: Find gaps between merged intervals
		List<Interval> result = new ArrayList<>();
		for (int i = 1; i < merged.size(); i++) {
			result.add(new Interval(merged.get(i - 1).end, merged.get(i).start));
		}
		return result;
	}

	// Original Author's Approach: "Merge K Sorted Lists" using PriorityQueue
	public static List<Interval> findEmployeeFreeTimeOriginal(List<List<Interval>> schedule) {
		List<Interval> result = new ArrayList<>();
		// Min-heap to always get the earliest interval across all employees
		PriorityQueue<EmployeeInterval> minHeap = new PriorityQueue<>(
				(a, b) -> a.interval.start - b.interval.start);

		// Initialize: Add first interval from each employee to heap
		for (int i = 0; i < schedule.size(); i++) {
			minHeap.offer(new EmployeeInterval(schedule.get(i).get(0), i, 0));
		}

		// Track the last processed interval for merging
		Interval previousInterval = minHeap.peek().interval;
		
		while (!minHeap.isEmpty()) {
			EmployeeInterval current = minHeap.poll();
			
			// Check for gap (free time)
			if (current.interval.start > previousInterval.end) {
				result.add(new Interval(previousInterval.end, current.interval.start));
				previousInterval = current.interval;
			} else {
				// Merge overlapping intervals
				if (previousInterval.end < current.interval.end) {
					previousInterval = current.interval;
				}
			}

			// Add next interval from same employee if exists
			int empIndex = current.empIndex;
			int nextIntIndex = current.empIntIndex + 1;
			if (schedule.get(empIndex).size() > nextIntIndex) {
				minHeap.offer(new EmployeeInterval(
					schedule.get(empIndex).get(nextIntIndex), empIndex, nextIntIndex));
			}
		}
		return result;
	}

	public static void main(String[] args) {

		List<List<Interval>> input = new ArrayList<>();
		input = new ArrayList<>();
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(1, 3), new Interval(9, 12))));
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(2, 4))));
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(6, 8),new Interval(5, 9))));
		List<Interval> result = EmployeeFreeTime.findEmployeeFreeTime(input);

 		System.out.print("Free Intervals: ");
		
		input = new ArrayList<>();
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(1, 3))));
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(2, 4))));
		input.add(new ArrayList<Interval>(Arrays.asList(new Interval(3, 5), new Interval(7, 9))));
		System.out.print("Free Intervals: ");
		for (Interval Interval : result)
			System.out.print("[" + Interval.start + ", " + Interval.end + "] ");
		/*
		 * List<String> list = new ArrayList<>(); list.add("done"); list.add("far");
		 * list.add("away"); list.add("done");
		 * 
		 * List<String> unmodifiableList = list.stream()
		 * .collect(Collectors.collectingAndThen(Collectors.toList(),
		 * Collections::unmodifiableList)); CompletableFuture<String> future =
		 * CompletableFuture.supplyAsync(() -> { try { TimeUnit.SECONDS.sleep(5); }
		 * catch (InterruptedException e) { throw new IllegalStateException(e); } return
		 * "Hello World"; });
		 */
		// /System.out.println(unmodifiableList);
	}
}
