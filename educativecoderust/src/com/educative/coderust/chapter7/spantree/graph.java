package com.educative.coderust.chapter7.spantree;

import java.util.ArrayList;
import java.util.List;

class graph {
	private List<vertex> vertices; // vertices
	private List<edge> e; // edges

	public graph(List<vertex> g, List<edge> e) {
		super();
		this.vertices = g;
		this.e = e;
	}

	public List<vertex> getG() {
		return vertices;
	}

	public void setG(List<vertex> g) {
		this.vertices = g;
	}

	public List<edge> getE() {
		return e;
	}

	public void setE(List<edge> e) {
		this.e = e;
	}

	// This method returns the vertex with a given id if it
	// already exists in the graph, returns NULL otherwise
	vertex vertexExists(int id) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getId() == id) {
				return vertices.get(i);
			}
		}
		return null;
	}

	// This method generates the graph with v vertices
	// and e edges
	void generateGraph(int vertice, List<ArrayList<Integer>> edges) {
		// create vertices
		for (int i = 0; i < vertice; i++) {
			vertex v = new vertex(i + 1, false);
			vertices.add(v);
		}

		// create edges
		for (int i = 0; i < edges.size(); i++) {
			vertex src = vertexExists(edges.get(i).get(1));
			vertex dest = vertexExists(edges.get(i).get(2));
			edge e = new edge(edges.get(i).get(0), false, src, dest);
			getE().add(e);
		}
	}

	// This method finds the MST of a graph using
	// Prim's Algorithm
	// returns the weight of the MST
	//A spanning-tree of a connected, undirected graph is a subgraph that is a tree and connects all the vertices.
	//One graph can have many different spanning trees. A graph with nn vertices has a spanning tree with n-1 edges.
	int findMinSpanningTree() {
		int vertex_count = 0;
		int weight = 0;

		// Add first vertex to the MST
		vertex current = vertices.get(0);
		current.setVisited(true);
		vertex_count++;

		// Construct the remaining MST using the
		// smallest weight edge
		while (vertex_count < vertices.size()) {
			edge smallest = null;

			// find smallest edge
			for (int i = 0; i < e.size(); i++) {
				if (!e.get(i).isVisited()) {
					if (e.get(i).getSrc().isVisited() && !e.get(i).getDest().isVisited()) {
						if (smallest == null || e.get(i).getWeight() < smallest.getWeight()) {
							smallest = e.get(i);
						}
					}
				}
			}

			// mark smallest edge as visited
			smallest.setVisited(true);
			smallest.getDest().setVisited(true);
			weight += smallest.getWeight();
			vertex_count++;
		}
		return weight;
	}

	String printGraph() {
		String result = "";

		for (int i = 0; i < e.size(); i++) {
			result += e.get(i).getSrc().getId() + "->" + e.get(i).getDest().getId() + "[" + e.get(i).getWeight() + ", "
					+ e.get(i).isVisited() + "]  ";
		}
		return result;
	}

	void printMst(int w) {
		System.out.println("MST");
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).isVisited()) {
				System.out.println(e.get(i).getSrc().getId() + "->" + e.get(i).getDest().getId());
			}
		}
		System.out.println("weight: " + w);
		System.out.println();
	}
};
