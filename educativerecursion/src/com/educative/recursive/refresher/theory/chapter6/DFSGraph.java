package com.educative.recursive.refresher.theory.chapter6;

import java.util.LinkedList;

public class DFSGraph {

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
        // fromNode 2 toNode 5 ==> 2 -> 5
        public void addEgde(int fromNode, int toNode) {
            tempList[fromNode].addFirst(toNode);
        }

        public void DFSRecursion(int startVertex) {
            boolean[] visitedArr = new boolean[numVertices];
            dfs(startVertex, visitedArr);
        }

        // DFS Recursion takes place here
        public void dfs(int start, boolean [] visitedArr) {
            visitedArr[start] = true;

            System.out.print(start + " ");

            for (int i = 0; i < tempList[start].size(); i++) {
                int toNode = tempList[start].get(i);
                if (!visitedArr[toNode])
                    dfs(toNode,visitedArr);
            }
        }
    }

    public static void main( String args[] ) {
        System.out.println( "Your DFS path is: " );

        int nVertices = 6;

        Graph g = new Graph(nVertices);
        
        g.addEgde(0, 1);
        g.addEgde(0, 2);
        g.addEgde(1, 3);
        g.addEgde(1, 4);
        g.addEgde(2, 5);

        // Root node given as argument to the function
        g.DFSRecursion(0);
    }
}
