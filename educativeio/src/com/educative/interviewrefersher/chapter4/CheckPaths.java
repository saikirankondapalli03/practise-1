package com.educative.interviewrefersher.chapter4;

class CheckPaths {
    
    //Perfrom DFS Traversal starting from source and if you reach destination
    //then it means that there exist a path between source and destination
    //so return true and if you traverse the graph but can't reach destination
    //then simply return false.
    public static boolean checkPath(Graph g, int source, int destination) {
        if (source == destination){
            return true;
        }

        //Boolean Array to hold the history of visited nodes (by default-false)
        //Make a node visited whenever you push it into stack
        boolean[] visited = new boolean[g.vertices];

        //Create Stack
        Stack<Integer> stack = new Stack<>(g.vertices);

        stack.push(source);
        visited[source] = true;

        //Traverse while stack is not empty
        while (!stack.isEmpty()) {

            //Pop a vertex/node from stack
            int current_node = stack.pop();

            //Get adjacent vertices to the current_node from the array,
            //and if only push unvisited adjacent vertices into stack
            //Before pushing into stack, check if it's the destination.
            DoublyLinkedList<Integer>.Node temp = null;
            if (g.adjacencyList[current_node] != null)
                temp = g.adjacencyList[current_node].headNode;

            while (temp != null) {

                if (!visited[temp.data]) {

                    if (temp.data == destination) {
                        return true;
                    }

                    stack.push(temp.data);
                    visited[temp.data] = true;

                }

                temp = temp.nextNode;
            }

        } //end of while
        return false;
    }
    public static void main(String args[]) {
    
        Graph g1 = new Graph(9);
        g1.addEdge(0,2);
        g1.addEdge(0,5);
        g1.addEdge(2,3);
        g1.addEdge(2,4);
        g1.addEdge(5,3);
        g1.addEdge(5,6);
        g1.addEdge(3,6);
        g1.addEdge(6,7);
        g1.addEdge(6,8);
        g1.addEdge(6,4);
        g1.addEdge(7,8);
        g1.printGraph();
        System.out.println("Path exists: " + checkPath(g1, 0, 7));
        System.out.println();
        Graph g2 = new Graph(4);
        g2.addEdge(0,1);
        g2.addEdge(1,2);
        g2.addEdge(1,3);
        g2.addEdge(2,3);

        g2.printGraph();
        System.out.println("Path exists: " + checkPath(g2, 3, 0));
  }
}