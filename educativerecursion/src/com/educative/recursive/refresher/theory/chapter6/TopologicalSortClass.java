package com.educative.recursive.refresher.theory.chapter6;

import java.util.LinkedList;
import java.util.Stack;

class TopologicalSortClass {

    static class Graph {
        int numVertices;
        LinkedList<Integer>[] tempList;

        Graph(int numVertices) {
            this.numVertices = numVertices;
            tempList = new LinkedList[numVertices];
            for (int i = 0; i < numVertices ; i++) {
                tempList[i] = new LinkedList<>();
            }
        } 

        // Method to add an edge between 2 nodes in the Graph
        // fromNode 2 toNode 4 ==> 2 -> 4
        public void addEgde(int fromNode, int toNode) {
            tempList[fromNode].addLast(toNode);
        }
      
        public void printGraph(){
            for (int i = 0; i <numVertices ; i++) {
                if(tempList[i].size()>0) {
                    System.out.println("Vertex " + i + " is connected to: ");
                    for (int j = 0; j < tempList[i].size(); j++) {
                        System.out.print(tempList[i].get(j) + " ");
                    }
                    System.out.println();
                }
            }
        }
        
        
        public void topologicalSorting() {
            boolean[] visited = new boolean[numVertices];
            Stack<Integer> ts = new Stack<>();
            
            //visit from each node if not already visited
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i]) {
                    topologicalSortRecursive(i, visited, ts);
                }
            }
            System.out.println("Topological Sort: ");
            int size = ts.size();
            for (int i = 0; i < size; i++) {
                System.out.print(ts.pop() + " ");
            }
        }

        public void topologicalSortRecursive(int start, boolean[] visited, Stack<Integer> ts) {
            visited[start] = true;
            for (int i = 0; i < tempList[start].size(); i++) {
                int vertex = tempList[start].get(i);
                if (!visited[vertex])
                    topologicalSortRecursive(vertex, visited, ts);
            }
            ts.push(start);
        }
        
    }

    public static void main( String args[] ) {
        System.out.println( "Path after Topological Sorting: " );

    
        Graph g = new Graph(7);
        
        g.addEgde(5,3);
        g.addEgde(6,4);
        g.addEgde(5,4);
        g.addEgde(6,2);
        g.addEgde(4,1);
        g.addEgde(3,1);
        
        g.addEgde(3,0);
        g.addEgde(3,2);
        
        g.printGraph();
        // Topological function called here
        g.topologicalSorting();

    }

}