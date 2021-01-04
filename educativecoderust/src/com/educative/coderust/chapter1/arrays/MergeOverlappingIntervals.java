package com.educative.coderust.chapter1.arrays;

import java.util.ArrayList;

class Pair {
	public int first;
	public int second;

	public Pair(int x, int y) {
		this.first = x;
		this.second = y;
	}
}

class MergeOverlappingIntervals {
	static ArrayList<Pair> mergeIntervals(ArrayList<Pair> input) {

		if (input == null || input.size() == 0) {
			return null;
		}

		ArrayList<Pair> result = new ArrayList<Pair>();

		result.add(new Pair(input.get(0).first, input.get(0).second));

		for (int i = 1; i < input.size(); i++) {
			int x1 = input.get(i).first;
			int y1 = input.get(i).second;
			int x2 = result.get(result.size() - 1).first;
			int y2 = result.get(result.size() - 1).second;
			// (1,2) (3,9)
			if (y2 >= x1) {
				result.get(result.size() - 1).second = Math.max(y1, y2);
			} else {
				result.add(new Pair(x1, y1));
			}
		}

		return result;
	}// 1(x2),8(y2) // 10(x1),12(y1)

	public static void main(String[] args) {
		ArrayList<Pair> v = new ArrayList<Pair>();

		v.add(new Pair(1, 4));
		v.add(new Pair(2, 5));
		v.add(new Pair(7, 9));
		//v.add(new Pair(6, 8));
		//v.add(new Pair(10, 12));
		//v.add(new Pair(11, 15));

		ArrayList<Pair> result = mergeIntervals(v);

		for (int i = 0; i < result.size(); i++) {
			System.out.print(String.format("[%d, %d] ", result.get(i).first, result.get(i).second));
		}
	}
}