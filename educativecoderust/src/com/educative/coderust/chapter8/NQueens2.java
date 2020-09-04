package com.educative.coderust.chapter8;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class NQueens2 {
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


	// This solution uses stack to store the solution.
	// Stack will hold only the column values and one solution
	// will be stored in the stack at a time.
	static int solveNQueens(int n, List<List<Integer>> results) {

		List<Integer> solution = new ArrayList<Integer>(n);
		Stack<Integer> solStack = new Stack<Integer>();

		for (int i = 0; i < n; ++i) {
			solution.add(-1);
		}

		int row = 0;
		int col = 0;

		while (row < n) {
			while (col < n) {
				if (isValidMove(row, col, solution)) {
					solStack.push(col);
					solution.set(row, col);
					row++;
					col = 0;
					break;
				}
				col++;
			}

			if (col == n) {
				if (!solStack.empty()) {
					col = solStack.peek() + 1;
					solStack.pop();
					row--;
				} else {
					// no more solutions exist
					break;
				}
			}

			if (row == n) {
				// add the solution into results
				results.add(new ArrayList<Integer>(solution));

				// backtrack to find the next solution
				row--;
				col = solStack.peek() + 1;
				solStack.pop();
			}
		}
		return results.size();
	}

	public static void main(String[] args) {
		List<List<Integer>> results = new ArrayList<List<Integer>>();

		int n = 4;
		int res = solveNQueens(n, results);
		System.out.println("Total solutions count for " + n + " queens: " + res);
	}
}