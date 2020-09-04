package com.educative.coderust.chapter8.maze;

//Try to understand this code and modify it as per your requirements.
import java.util.Arrays;
import java.util.Stack;

class Cell {
	int x;
	int y;

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "[" + x + "," + y + "] ";
	}
}

public class RatMazeAllPaths {
	private static void printSolution(Stack<Cell> stk) {
		System.out.println(Arrays.toString(stk.toArray()));
	}

	private static boolean canVisit(int maze[][], int x, int y, boolean visited[][]) {
		return (x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 1 && !visited[x][y]);
	}

	@SuppressWarnings("unchecked")
	private static void solveMaze(int maze[][], int x, int y, boolean visited[][], Stack<Cell> stk) {
		if (x == maze.length - 1 && y == maze[0].length - 1) {
			// We have reached the destination
			Stack<Cell> copiedStack = (Stack<Cell>) stk.clone();
			copiedStack.push(new Cell(x, y));
			printSolution(copiedStack);
			return;
		}
		visited[x][y] = true;

		// Can go down?
		if (canVisit(maze, x + 1, y, visited)) {
			Stack<Cell> copiedStack = (Stack<Cell>) stk.clone();
			copiedStack.push(new Cell(x, y));
			solveMaze(maze, x + 1, y, visited, copiedStack);
			visited[x + 1][y] = false;
		}

		// Can go up?
		if (canVisit(maze, x - 1, y, visited)) {
			Stack<Cell> copiedStack = (Stack<Cell>) stk.clone();
			copiedStack.push(new Cell(x, y));
			solveMaze(maze, x - 1, y, visited, copiedStack);
			visited[x - 1][y] = false;
		}

		// Can go right?
		if (canVisit(maze, x, y + 1, visited)) {
			Stack<Cell> copiedStack = (Stack<Cell>) stk.clone();
			copiedStack.push(new Cell(x, y));
			solveMaze(maze, x, y + 1, visited, copiedStack);
			visited[x][y + 1] = false;
		}

		// Can go left?
		if (canVisit(maze, x, y - 1, visited)) {
			Stack<Cell> copiedStack = (Stack<Cell>) stk.clone();
			copiedStack.push(new Cell(x, y));
			solveMaze(maze, x, y - 1, visited, copiedStack);
			visited[x][y - 1] = false;
		}
	}

	public static void main(String args[]) {
		int maze[][] = { { 1, 1, 1, 0 }, { 1, 1, 1, 1 }, { 0, 1, 0, 0 }, { 1, 1, 1, 1 } };
		boolean visited[][] = new boolean[maze.length][maze[0].length];
		Stack<Cell> stk = new Stack<Cell>();
		solveMaze(maze, 0, 0, visited, stk);
	}
}
