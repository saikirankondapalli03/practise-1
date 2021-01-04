package com.educative.patterns.chapter4.mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Intervall {
	int start;
	int end;

	public Intervall(int start, int end) {
		this.start = start;
		this.end = end;
	}
};

class EmployeeInterval {
	Intervall interval; // Intervall representing employee's working hours
	int empIndex; // index of the list containing working hours of this employee
	int empIntIndex; // index of the Intervall in the employee list

	public EmployeeInterval() {
		
	}
	public Intervall getInterval() {
		return interval;
	}

	public void setInterval(Intervall interval) {
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

	public EmployeeInterval(Intervall Intervall, int employeeIndex, int IntervallIndex) {
		this.interval = Intervall;
		this.empIndex = employeeIndex;
		this.empIntIndex = IntervallIndex;
	}
};

class EmployeeFreeTime {

	
	public static List<Intervall> findEmployeeFreeTime(List<List<Intervall>> schedule) {
		List<Intervall> result = new ArrayList<>();
		// PriorityQueue to store one Intervall from each employee
		PriorityQueue<EmployeeInterval> minHeap = new PriorityQueue<>(
				(a, b) -> Integer.compare(a.interval.start, b.interval.start));

		// insert the first Intervall of each employee to the queue
		// new Intervall(1, 3), new Intervall(5, 6)
		// new Intervall(2, 3), new Intervall(6, 8)
		for (int i = 0; i < schedule.size(); i++)
			minHeap.offer(new EmployeeInterval(schedule.get(i).get(0), i, 0));

		Intervall previousIntervall = minHeap.peek().interval;
		while (!minHeap.isEmpty()) {
			EmployeeInterval currInter = minHeap.poll();
			// if previousIntervall is not overlapping with the next Intervall, insert a
			// free Intervall
			if (currInter.interval.start > previousIntervall.end) {
				result.add(new Intervall(previousIntervall.end, currInter.interval.start));
				previousIntervall = currInter.interval;
			} else { // overlapping Intervalls, update the previousIntervall if needed
				if (previousIntervall.end < currInter.interval.end)
					previousIntervall = currInter.interval;
			}

			// if there are more Intervalls available for the same employee, add their next Intervall
			int currEmployeeIndex = currInter.empIndex;
			int currIntervalIndex = currInter.empIntIndex;
			if (schedule.get(currEmployeeIndex).size() > currIntervalIndex + 1) {
				EmployeeInterval e= new EmployeeInterval();
				e.setEmployeeIndex(currEmployeeIndex);
				e.setIntervalIndex(currIntervalIndex + 1);
				e.setInterval(schedule.get(currEmployeeIndex).get(currIntervalIndex + 1));
				minHeap.offer(e);
			}
		}

		return result;
	}

	public static void main(String[] args) {

		List<List<Intervall>> input = new ArrayList<>();
		input = new ArrayList<>();
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(1, 3), new Intervall(9, 12))));
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(2, 4))));
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(6, 8))));
		List<Intervall> result = EmployeeFreeTime.findEmployeeFreeTime(input);

 		System.out.print("Free Intervalls: ");
		
		input = new ArrayList<>();
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(1, 3))));
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(2, 4))));
		input.add(new ArrayList<Intervall>(Arrays.asList(new Intervall(3, 5), new Intervall(7, 9))));
		System.out.print("Free Intervalls: ");
		for (Intervall Intervall : result)
			System.out.print("[" + Intervall.start + ", " + Intervall.end + "] ");
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
