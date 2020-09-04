package com.educative.interviewrefersher.chapter4;

import java.util.LinkedList;

public class GraphDFSRecursion {

    static class Graph{
        int vertices;
        LinkedList<Integer>[] adjList;

        Graph(int vertices){
            this.vertices = vertices;
            adjList = new LinkedList[vertices];
            for (int i = 0; i <vertices ; i++) {
                adjList[i] = new LinkedList<>();
            }
        }
        public void addEdge(int source, int destination){
            adjList[source].addFirst(destination);
        }

        public void printGraph(){
            for (int i = 0; i <vertices ; i++) {
                if(adjList[i].size()>0) {
                    System.out.println("Vertex " + i + " is connected to: ");
                    for (int j = 0; j < adjList[i].size(); j++) {
                        System.out.print(adjList[i].get(j) + " ");
                    }
                    System.out.println();
                }
            }
        }

        public void DFSRecursion(int startVertex){
            boolean [] visited = new boolean[vertices];
            dfs(startVertex, visited);
        }

        public void dfs(int start, boolean [] visited){
            visited[start] = true;
            System.out.print(start + " ");
            for (int i = 0; i <adjList[start].size() ; i++) {
                int destination = adjList[start].get(i);
                if(!visited[destination])
                    dfs(destination,visited);
            }
        }
    }

    public static void main(String[] args) {
        int vertices = 7;
        Graph g = new Graph(vertices);
        
        g.addEdge(0, 1); 
        g.addEdge(0, 2); 
        g.addEdge(1, 3); 
        g.addEdge(4, 1); 
        g.addEdge(6, 4); 
        g.addEdge(5, 6); 
        g.addEdge(5, 2); 
        g.addEdge(6, 0); 
        g.printGraph();
        
        
        g.DFSRecursion(0);
    }
}