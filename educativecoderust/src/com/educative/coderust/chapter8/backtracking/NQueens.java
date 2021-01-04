package com.educative.coderust.chapter8.backtracking;

import java.util.ArrayList;
import java.util.List;

class NQueens {
	// this method determines if a queen can
	// be placed at proposedRow, proposedCol
	// with current solution i.e. this move
	// is valid only if no queen in current
	// solution should attacked square at
	// proposedRow and proposedCol
	static boolean isValidMove(int proposedRow, int proposedCol, List<Integer> solution) {
		System.out.println("proposed Row=> " + proposedRow);
		System.out.println("proposed Col=> " + proposedCol);
		// we need to check with all queens
		// in current solution
		for (int i = 0; i < proposedRow; ++i) {
			int oldRow = i;
			int oldCol = solution.get(i);

			int diagonalOffset = proposedRow - oldRow;
			if (oldCol == proposedCol) {
				return false;
			}
			if (oldCol == proposedCol - diagonalOffset) {
				return false;
			}
			if (oldCol == proposedCol + diagonalOffset) {
				return false;
			}
		}

		return true;
	}

	static void solveNQueensRec(int n, List<Integer> solution, int row, List<List<Integer>> results) {

		if (row == n) {
			results.add(new ArrayList<Integer>(solution));
			return;
		}

		for (int i = 0; i < n; ++i) {
			if (isValidMove(row, i, solution)) {
				solution.set(row, i);
				solveNQueensRec(n, solution, row + 1, results);
			}
		}
	}

	static int solveNQueens(int n, List<List<Integer>> results) {
		List<Integer> solution = new ArrayList<Integer>(n);
		for (int i = 0; i < n; ++i) {
			solution.add(-1);
		}
		solveNQueensRec(n, solution, 0, results);
		return results.size();
	}

	public static void main(String[] args) {
		List<List<Integer>> results = new ArrayList<List<Integer>>();

		int n = 4;
		int res = solveNQueens(n, results);
		System.out.println("Total solutions count for " + n + " queens: " + res);
	}
}