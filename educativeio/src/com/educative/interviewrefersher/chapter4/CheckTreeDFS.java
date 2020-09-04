package com.educative.interviewrefersher.chapter4;

class CheckTreeDFS {
    public static boolean isTree(Graph g) {

        //1. Check each node other than root has exactly one parent.
        if (!checkOneParent(g))
            return false;

        //2. Check for Cycle
        if (detectCycle(g))
            return false;

        //2. Check for connectivity
        if (!checkConnected(g, 0))
            return false;

        return true; 
    }

    public static boolean detectCycle(Graph g) {
        int num_of_vertices = g.vertices;

        //Boolean Array to hold the history of visited nodes (by default-false)
        boolean[] visited = new boolean[num_of_vertices];
        //Holds a flag if the node is currently in Stack or not (by default- false)
        boolean[] stackFlag = new boolean[num_of_vertices];

        for (int i = 0; i < num_of_vertices; i++) {
            //Check cyclic on each node
            if (cyclic(g, i, visited, stackFlag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean cyclic(Graph g, int v, boolean[] visited, boolean[] stackFlag) {
        //if node is currently in stack that means we have found a cycle
        if (stackFlag[v])
            return true;

        //if it is already visited (and not in Stack) then there is no cycle
        if (visited[v])
            return false;

        visited[v] = true;
        stackFlag[v] = true;

        // check adjacency list of the node
        DoublyLinkedList<Integer>.Node temp = null;
        if (g.adjacencyList[v] != null)
            temp = g.adjacencyList[v].headNode;

        while (temp != null) {
            //run cyclic function recursively on each outgowing path
            if (cyclic(g, temp.data, visited, stackFlag)) {
                return true;
            }
            temp = temp.nextNode;
        }
        stackFlag[v] = false;

        return false;
    }

    public static boolean checkOneParent(Graph g) {
        int num_of_vertices = g.vertices;
        boolean[] hasOneParent = new boolean[num_of_vertices];

        //traverse adjacency list and mark the nodes which have a parent
        for (int i = 0; i < num_of_vertices; i++) {
            DoublyLinkedList<Integer>.Node tmp = null;
            if (g.adjacencyList[i] != null) {
                tmp = g.adjacencyList[i].headNode;
                while (tmp != null) {
                    if (hasOneParent[tmp.data]) //if a node has more than one parent
                        return false;            //then return false
                    hasOneParent[tmp.data] = true;
                    tmp = tmp.nextNode;
                }
            }
        }
        for (int i = 0; i < num_of_vertices; i++) {
            //System.out.print(hasOneParent[i]);
            if (i == 0 && hasOneParent[i] == true) {
                // root should not have parent
                return false;
            } else if (i != 0 && hasOneParent[i] == false) {
                //will be false if the node had no parent.
                return false;
            }
        }

        return true;
    }
    private static boolean checkConnected(Graph g, int source) {

        int num_of_vertices = g.vertices;
        int vertices_reached = 0; //Store vertices reachable through source

        //Boolean Array to hold the history of visited nodes (by default-false)
        //Make a node visited whenever you push it into stack
        boolean[] visited = new boolean[num_of_vertices];

        //Create Stack(Implemented in previous section) for Depth First Traversal and Push source in it
        Stack<Integer> stack = new Stack<>(num_of_vertices);

        stack.push(source);
        visited[source] = true;

        //Traverse while stack is not empty
        while (!stack.isEmpty()) {

            //Pop a vertex/node from stack
            int current_node = stack.pop();

            //Get adjacent vertices to the current_node from the array,
            //and push unvisited vertices in stack and also increment vertices_reached
            DoublyLinkedList<Integer>.Node temp = null;
            if (g.adjacencyList[current_node] != null)
                temp = g.adjacencyList[current_node].headNode;

            while (temp != null) {

                if (!visited[temp.data]) {
                    stack.push(temp.data);
                    visited[temp.data] = true;
                    vertices_reached++;
                }

                temp = temp.nextNode;
            }

        }

        //+1 for source, and if number of vertices reachable from source are equal
        //to the total number of vertices in graph then return true else false.
        return (vertices_reached + 1) == g.vertices;
    }
    public static void main(String args[]) {
    
        Graph g1 = new Graph(4);
        g1.addEdge(0,1);
        g1.addEdge(1,2);
        g1.addEdge(1,3);
        g1.printGraph();
        System.out.println("isTree : " + isTree(g1));

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