package com.educative.interviewrefersher.chapter4;

public class CheckTreeBFS {
	public static boolean isTree(Graph g) {

        int root = 0;

        //Boolean Array to hold the history of visited nodes (by default-false)
        //Make a node visited whenever you enqueue it into queue
        boolean[] visited = new boolean[g.vertices];
        
        //Create Queue for Breadth First Traversal and enqueue root in it
        Queue<Integer> queue = new Queue<>(g.vertices);

        queue.enqueue(root);
        visited[root] = true;

        //Store the number of visited nodes to check at end if all are visited
        int numberOfVisited = 1; //root is already visited

        //Traverse while queue is not empty
        while (!queue.isEmpty()) {

            //Dequeue a vertex/node from queue and add it to result
            int current_node = queue.dequeue();

            //Get adjacent vertices to the current_node from the array,
            //and if they are not already visited then enqueue them in the Queue
            DoublyLinkedList<Integer>.Node temp = null;
            if(g.adjacencyList[current_node] != null)
                temp = g.adjacencyList[current_node].headNode;

            while (temp != null) {

                if (!visited[temp.data]) {
                    queue.enqueue(temp.data);
                    visited[temp.data] = true; //Visit the current Node
                    numberOfVisited++;
                }
                else 
                    return false;
                temp = temp.nextNode;
            }
        }//end of while

        //If all vertices are visited then return true
        if (numberOfVisited == g.vertices)
            return true; 

        return false;
    }
    
    public static void main(String args[]) {
    
        Graph g = new Graph(5);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(3,4);
        g.printGraph();
        System.out.println("isTree : " + isTree(g));

        Graph g2 = new Graph(4);
        g2.addEdge(0,1);
        g2.addEdge(0,2);
        g2.addEdge(0,3);
        g2.addEdge(3,2);
        g2.printGraph();
        System.out.println("isTree : " + isTree(g2));

        Graph g3 = new Graph(6);
        g3.addEdge(0,1);
        g3.addEdge(0,2);
        g3.addEdge(0,3);
        g3.addEdge(4,5);
        g3.printGraph();
        System.out.println("isTree : " + isTree(g3));
  }
}
