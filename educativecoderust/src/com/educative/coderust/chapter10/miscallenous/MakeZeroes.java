package com.educative.coderust.chapter10.miscallenous;

import java.util.HashSet;
import java.util.Set;

class MakeZeroes {
	static void makeZeroes(int[][] matrix) {
		if (matrix.length == 0) {
			return;
		}

		Set<Integer> zeroRows = new HashSet<Integer>();
		Set<Integer> zeroCols = new HashSet<Integer>();

		int rows = matrix.length;
		int cols = matrix[0].length;

		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				if (matrix[i][j] == 0) {
					if (!zeroRows.contains(i)) {
						zeroRows.add(i);
					}

					if (!zeroCols.contains(j)) {
						zeroCols.add(j);
					}
				}
			}
		}

		for (int r : zeroRows) {
			for (int c = 0; c < cols; ++c) {
				matrix[r][c] = 0;
			}
		}

		for (int c : zeroCols) {
			for (int r = 0; r < rows; ++r) {
				matrix[r][c] = 0;
			}
		}
	}

	static void printMatrix(int[][] m) {
		System.out.println();
		for (int i = 0; i < m.length; ++i) {
			for (int j = 0; j < m[i].length; ++j) {
				System.out.print(m[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	static boolean isRowOrColZero(int[][] matrix, int r, int c) {
		int rows = matrix.length;
		int cols = 0;
		if (rows > 0) {
			cols = matrix[0].length;
		}

		for (int i = 0; i < cols; ++i) {
			if (matrix[r][i] == 0) {
				return true;
			}
		}

		for (int i = 0; i < rows; ++i) {
			if (matrix[i][c] == 0) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		int[][] matrix = { { 5, 4, 3, 9 }, { 2, 0, 7, 6 }, { 1, 3, 4, 0 }, { 9, 8, 3, 4 } };
		System.out.print("Original Matrix");
		printMatrix(matrix);
		makeZeroes(matrix);
		System.out.print("\nMatrix with Zeros");
		printMatrix(matrix);
	}
}