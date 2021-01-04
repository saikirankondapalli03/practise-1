package com.educative.interviewrefersher.chapter4;

/* Java program for solution of Hamiltonian Cycle problem 
using backtracking */
class HamiltonianCycle 
{ 
	final int V = 5; 
	int path[]; 

	/* A utility function to check if the vertex v can be 
	added at index 'pos'in the Hamiltonian Cycle 
	constructed so far (stored in 'path[]') */
	boolean isSafe(int v, int graph[][], int path[], int pos) 
	{ 
		/* Check if this vertex is an adjacent vertex of 
		the previously added vertex. */
		int x=path[pos - 1];
		System.out.println(x+"-"+v);
		if (graph[x][v] == 0) 
			return false; 

		/* Check if the vertex has already been included. 
		This step can be optimized by creating an array 
		of size V */
		for (int i = 0; i < pos; i++) 
			if (path[i] == v) 
				return false; 

		return true; 
	} 

	/* A recursive utility function to solve hamiltonian 
	cycle problem */
	boolean hamCycleUtil(int graph[][], int path[], int i) 
	{ 
		/* base case: If all vertices are included in 
		Hamiltonian Cycle */
		if (i == V) 
		{ 
			// And if there is an edge from the last included 
			// vertex to the first vertex 
			if (graph[path[i - 1]][path[0]] == 1) 
				return true; 
			else
				return false; 
		} 

		// Try different vertices as a next candidate in 
		// Hamiltonian Cycle. We don't try for 0 as we 
		// included 0 as starting point in hamCycle() 
		for (int v = 1; v < V; v++) 
		{ 
			/* Check if this vertex can be added to Hamiltonian 
			Cycle */
			if (isSafe(v, graph, path, i)) 
			{ 
				path[i] = v; 

				/* recur to construct rest of the path */
				if (hamCycleUtil(graph, path, i + 1) == true) 
					return true; 

				/* If adding vertex v doesn't lead to a solution, 
				then remove it */
				path[i] = -1; 
			} 
		} 

		/* If no vertex can be added to Hamiltonian Cycle 
		constructed so far, then return false */
		return false; 
	} 

	/* This function solves the Hamiltonian Cycle problem using 
	Backtracking. It mainly uses hamCycleUtil() to solve the 
	problem. It returns false if there is no Hamiltonian Cycle 
	possible, otherwise return true and prints the path. 
	Please note that there may be more than one solutions, 
	this function prints one of the feasible solutions. */
	int hamCycle(int graph[][]) 
	{ 
		path = new int[V]; 
		for (int i = 0; i < V; i++) 
			path[i] = -1; 

		/* Let us put vertex 0 as the first vertex in the path. 
		If there is a Hamiltonian Cycle, then the path can be 
		started from any point of the cycle as the graph is 
		undirected */
		path[0] = 0; 
		if (hamCycleUtil(graph, path, 1) == false) 
		{ 
			System.out.println("\nSolution does not exist"); 
			return 0; 
		} 

		printSolution(path); 
		return 1; 
	} 

	/* A utility function to print solution */
	void printSolution(int path[]) 
	{ 
		System.out.println("Solution Exists: Following" + 
						" is one Hamiltonian Cycle"); 
		for (int i = 0; i < V; i++) 
			System.out.print(" " + path[i] + " "); 

		// Let us print the first vertex again to show the 
		// complete cycle 
		System.out.println(" " + path[0] + " "); 
	} 

	// driver program to test above function 
	public static void main(String args[]) 
	{ 
		HamiltonianCycle hamiltonian = 
								new HamiltonianCycle(); 
		/*
	 Let us create the following graph 
		(0)--(1)--(2) 
		| 		   | 
		|      	   | 
		| 	       | 
		(3)-------(4)
		 */
		int graph1[][] = {
			{0, 1, 0, 1, 0}, 
			{1, 0, 1, 1, 1}, 
			{0, 1, 0, 0, 1}, 
			{1, 1, 0, 0, 1}, 
			{0, 1, 1, 1, 0}, 
		}; 

		// Print the solution 
		hamiltonian.hamCycle(graph1); 

		/* Let us create the following graph 
		(0)--(1)--(2) 
			| / \ | 
			| / \ | 
			| /	 \ | 
		(3)	 (4) */
		int graph2[][] = {{0, 1, 0, 1, 0}, 
			{1, 0, 1, 1, 1}, 
			{0, 1, 0, 0, 1}, 
			{1, 1, 0, 0, 0}, 
			{0, 1, 1, 0, 0}, 
		}; 

		// Print the solution 
		hamiltonian.hamCycle(graph2); 
	} 
} 
// This code is contributed by Abhishek Shankhadhar 

