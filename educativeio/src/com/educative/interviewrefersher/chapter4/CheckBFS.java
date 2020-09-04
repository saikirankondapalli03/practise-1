package com.educative.interviewrefersher.chapter4;

class CheckBFS {
	public static String bfsTraversal(Graph g, int source) {

		String result = "";
		if (g.vertices < 1) {
			return result;
		}
		int num_of_vertices = g.vertices;

		// Boolean Array to hold the history of visited nodes (by default-false)
		// Make a node visited whenever you enqueue it into queue
		boolean[] visited = new boolean[num_of_vertices];

		// Create Queue for Breadth First Traversal and enqueue source in it
		Queue<Integer> queue = new Queue<>(num_of_vertices);

		queue.enqueue(source);
		visited[source] = true;

		// Traverse while queue is not empty
		while (!queue.isEmpty()) {

			// Dequeue a vertex/node from queue and add it to result
			int current_node = queue.dequeue();

			result += String.valueOf(current_node);

			// Get adjacent vertices to the current_node from the array,
			// and if they are not already visited then enqueue them in the Queue
			DoublyLinkedList<Integer>.Node temp = null;
			if (g.adjacencyList[current_node] != null)
				temp = g.adjacencyList[current_node].headNode;

			while (temp != null) {

				if (!visited[temp.data]) {
					queue.enqueue(temp.data);
					visited[temp.data] = true; // Visit the current Node
				}
				temp = temp.nextNode;
			}
		} // end of while
		return result;
	}

	public static void main(String args[]) {
		   Graph g = new Graph(6);
	        g.addEdge(0, 1);
	        g.addEdge(0, 2);
	        g.addEdge(1, 3);
	        g.addEdge(1, 4);
	        g.addEdge(2, 5);
	        //g.addEdge(1, 2);
	        g.printGraph();
	System.out.println(bfsTraversal(g, 0));
	}
}