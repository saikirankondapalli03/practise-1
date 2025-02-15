package com.educative.patterns.chapter4.mergeintervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class Meeting {
	int start;
	int end;

	public Meeting(int start, int end) {
		this.start = start;
		this.end = end;
	}
};

class MinimumMeetingRooms {

	public static int findMinimumMeetingRooms(List<Meeting> meetings) {
		if (meetings == null || meetings.size() == 0)
			return 0;

		// sort the meetings by start time
		Collections.sort(meetings, (a, b) -> Integer.compare(a.start, b.start));
		
		
		//Create a min-heap to store all the active meetings. 
		//This min-heap will also be used to find the active meeting with the smallest end time.
		PriorityQueue<Meeting> minHeap = new PriorityQueue<>(meetings.size(), (a, b) -> Integer.compare(a.end, b.end));
		
		int minRooms = 0;
		for (Meeting meeting : meetings) {
			System.out.println(meeting.start+"-"+meeting.end);
			// remove all meetings that have ended
			while (!minHeap.isEmpty() && meeting.start >= minHeap.peek().end)
				minHeap.poll();
			// add the current meeting into the minHeap
			minHeap.offer(meeting);
			// all active meeting are in the minHeap, so we need rooms for all of them.
			minRooms = Math.max(minRooms, minHeap.size());
		}
		return minRooms;
	}

	public static void main(String[] args) {
		List<Meeting> input = new ArrayList<Meeting>() {
			{
				add(new Meeting(2, 3));
				add(new Meeting(3, 4));
				add(new Meeting(3, 5));
				add(new Meeting(4, 5));
			}
		};
		int result = MinimumMeetingRooms.findMinimumMeetingRooms(input);
		System.out.println("Minimum meeting rooms required: " + result);

		input = new ArrayList<Meeting>() {
			{
				add(new Meeting(1, 4));
				add(new Meeting(2, 5));
				add(new Meeting(7, 9));
			}
		};
		result = MinimumMeetingRooms.findMinimumMeetingRooms(input);
		System.out.println("Minimum meeting rooms required: " + result);

		input = new ArrayList<Meeting>() {
			{
				add(new Meeting(6, 7));
				add(new Meeting(2, 4));
				add(new Meeting(8, 12));
			}
		};
		result = MinimumMeetingRooms.findMinimumMeetingRooms(input);
		System.out.println("Minimum meeting rooms required: " + result);

		input = new ArrayList<Meeting>() {
			{
				add(new Meeting(1, 4));
				add(new Meeting(2, 3));
				add(new Meeting(3, 6));
			}
		};
		result = MinimumMeetingRooms.findMinimumMeetingRooms(input);
		System.out.println("Minimum meeting rooms required: " + result);

		input = new ArrayList<Meeting>() {
			{
				add(new Meeting(4, 5));
				add(new Meeting(2, 3));
				add(new Meeting(2, 4));
				add(new Meeting(3, 5));
			}
		};
		result = MinimumMeetingRooms.findMinimumMeetingRooms(input);
		System.out.println("Minimum meeting rooms required: " + result);
	}
}