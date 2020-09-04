package com.educative.coderust.chapter10.miscallenous;

class SearchMatrix {
	public static IntPair searchInMatrix(int matrix[][], int value) {
		int M = matrix.length; // rows
		int N = matrix[0].length; // columns

		// Let's start searching from top right.
		// Alternatively, searching from bottom left
		// i.e. matrix[M-1][0] can also work.

		int i = 0, j = N - 1;

		while (i < M && j >= 0) {
			if (matrix[i][j] == value) {
				return new IntPair(i, j);
			} else if (value < matrix[i][j]) {
				// search left
				--j;
			} else {
				// search down.
				++i;
			}
		}

		return new IntPair(-1, -1);
	}

	public static void main(String[] args) {
		int[][] matrix = new int[][] { { 2, 4, 9, 13, 15 }, { 3, 5, 11, 18, 22 }, { 6, 8, 16, 21, 28 },
				{ 9, 11, 20, 25, 31 }, };

		IntPair val_loc = searchInMatrix(matrix, 8);
		System.out.println("Verifying at " + Integer.toString(val_loc.first) + "," + Integer.toString(val_loc.second)
				+ ": " + Integer.toString(matrix[val_loc.first][val_loc.second]));
	}
}

class IntPair extends Pair<Integer, Integer> {
	public IntPair(int first, int second) {
		super(first, second);
	}
}

class Pair<X, Y> {
	public X first;
	public Y second;

	public Pair(X first, Y second) {
		this.first = first;
		this.second = second;
	}
}
