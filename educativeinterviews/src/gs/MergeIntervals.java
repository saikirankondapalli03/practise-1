package gs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MergeIntervals {

	public int[][] mergePriorityQueue(int[][] intervals) {

		if (intervals == null || intervals.length == 0)
			return intervals;

		if (intervals.length == 1)
			return intervals;

		Queue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

		for (int[] i : intervals)
			heap.offer(i);

		int[] prev = heap.poll();

		List<int[]> list = new ArrayList<>();

		list.add(prev);

		while (!heap.isEmpty()) {

			int[] current = heap.poll();

			int start = current[0], end = current[1];
			int prevStart = prev[0], prevEnd = prev[1];

			if (start <= prevEnd) {
				// merge happening here
				prev[1] = Math.max(end, prevEnd);

			} else {

				list.add(current);
				prev = current;
			}
		}

		int i = 0;
		int[][] merged = new int[list.size()][];

		for (int[] l : list)
			merged[i++] = l;

		return merged;
	}
}
