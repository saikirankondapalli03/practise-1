package com.educative.interviewrefersher.chapter4;

class CheckDFS {
    public static String dfsTraversal(Graph g, int source) {

        String result = "";
        if (g.vertices < 1){
            return result;
        }
        int num_of_vertices = g.vertices;

        //Boolean Array to hold the history of visited nodes (by default-false)
        //Make a node visited whenever you push it into stack
        boolean[] visited = new boolean[num_of_vertices];

        //Create Stack(Implemented in previous lesson) for Depth First Traversal and Push source in it
        Stack<Integer> stack = new Stack<Integer>(num_of_vertices);

        stack.push(source);

        //Traverse while stack is not empty
        while (!stack.isEmpty()) {

            //Pop a vertex/node from stack and add it to the result
            int current_node = stack.pop();
            result += String.valueOf(current_node);
            
            //Get adjacent vertices to the current_node from the array,
            //and if they are not already visited then push them in the stack
            DoublyLinkedList<Integer>.Node temp = null;
            if(g.adjacencyList[current_node] !=null)
                temp = g.adjacencyList[current_node].headNode;

            while (temp != null) {

                if (!visited[temp.data]) {
                    stack.push(temp.data);
                     //Visit the node
                    visited[current_node] = true;
                }
                temp = temp.nextNode;
            }
        }//end of while
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
        System.out.println(dfsTraversal(g, 0));
  }
}