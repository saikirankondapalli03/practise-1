package com.educative.coderust.chapter7.clone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CloneUnDirectedGraph {

	// Function to clone the graph
	public Graph cloneGraph(Graph graph) {
		// maintain map of visited with Graph
		Map<GraphNode, GraphNode> map = new HashMap<GraphNode, GraphNode>();

		// for all nodes in the graph
		for (GraphNode node : graph.getNodes()) {
			// maintain table for nodes visited
			// if table contains the node visited, then don't clone it
			if (!map.containsKey(node))
				// clone the node with bfs
				cloneConnectedComponent(node, map);
		}

		// after cloning individual Nodes GraphNode, finally append it to the graph
		Graph cloned = new Graph();
		for (GraphNode current : map.values())
			cloned.addNode(current);

		return cloned;
	}

	// Function to clone the connected components
	private void cloneConnectedComponent(GraphNode node, Map<GraphNode, GraphNode> map) {
		//queue for bfs
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		queue.add(node);

		
		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();
			GraphNode currentCloned = null;
			// see the table, if the node is already cloned ?? 
			if (map.containsKey(current)) {
				//if it s cloned, then take the currentCloned 
				currentCloned = map.get(current);
			} else {
				//if it is not cloned, then create a new Node
				// CREATION OF NEW NODE
				currentCloned = new GraphNode(current.getData());
				// mark it as visited in the table
				map.put(current, currentCloned);
			}
			
			// take its immediate neighbours
			List<GraphNode> children = current.getChildren();
			// see if the neighbours are cloned or not. 
			for (GraphNode child : children) {
				// if child is already visited 
				if (map.containsKey(child)) {
					currentCloned.addChild(map.get(child));
				} else {
					// if the child is not visited, create a node new Node and mark it as visited in the table
					GraphNode childCloned = new GraphNode(child.getData());
					map.put(child, childCloned);
					currentCloned.addChild(childCloned);
					queue.add(child);
				}
			}
		}
	}

	/*
	 * 
	 * educative
	 * 
	 */
	
	//visual like a binary tree. here instead of binary tree we have multiple childes. hence children is
	// stored as arraylist. 
	private static GraphNode clone_rec(GraphNode root, HashMap<GraphNode, GraphNode> nodes_completed) {
		// if the root is null, then we can't clone anything return null
		if (root == null) {
			return null;
		}
		//since we have root, create a duplicate node by marking it as visited
		GraphNode pNew = new GraphNode(root.data);
		nodes_completed.put(root, pNew);
		
		//for all childrens 
		for (GraphNode p : root.children) {
			// see if children is visited
			GraphNode x = nodes_completed.get(p);
			//if it is not visited
			if (x == null) {
				// visit it and clone it by calling the clone recursively
				pNew.children.add(clone_rec(p, nodes_completed));
			} else {
				// if it is visited, add  current children
				pNew.children.add(x);
			}
		}
		return pNew;
	}

	/*
	 * 
	 * educative recursion
	 * 
	 */
	public static GraphNode clone(GraphNode root) {
		HashMap<GraphNode, GraphNode> nodes_completed = new HashMap<GraphNode, GraphNode>();
		return clone_rec(root, nodes_completed);
	}

	// Function to build the graph
	public Graph buildGraph() {

		// Create graph
		Graph g = new Graph();

		// Adding nodes to the graph
		GraphNode g1 = new GraphNode(1);
		g.addNode(g1);
		GraphNode g2 = new GraphNode(2);
		g.addNode(g2);
		GraphNode g3 = new GraphNode(3);
		g.addNode(g3);
		GraphNode g4 = new GraphNode(4);
		g.addNode(g4);
		GraphNode g5 = new GraphNode(5);
		g.addNode(g5);
		GraphNode g6 = new GraphNode(6);
		g.addNode(g6);

		// Adding edges
		g1.addChild(g2);
		g1.addChild(g3);
		g2.addChild(g1);
		g2.addChild(g4);
		g3.addChild(g1);
		g3.addChild(g4);
		g4.addChild(g2);
		g4.addChild(g3);
		g5.addChild(g6);
		g6.addChild(g5);

		return g;
	}

	// Function to print the connected components
	public void printConnectedComponent(GraphNode node, Set<GraphNode> visited) {
		if (visited.contains(node))
			return;

		Queue<GraphNode> q = new LinkedList<GraphNode>();
		q.add(node);

		while (!q.isEmpty()) {
			GraphNode currentNode = q.remove();
			if (visited.contains(currentNode))
				continue;
			visited.add(currentNode);
			System.out.println("Node " + currentNode.getData() + " - " + currentNode);
			for (GraphNode child : currentNode.getChildren()) {
				System.out.println("\tNode " + child.getData() + " - " + child);
				q.add(child);
			}
		}
	}

	// Driver code
	public static void main(String[] args) {
		CloneUnDirectedGraph gfg = new CloneUnDirectedGraph();
		Graph g = gfg.buildGraph();

		// Original graph
		System.out.println("\tINITIAL GRAPH");
		Set<GraphNode> visited = new HashSet<GraphNode>();
		for (GraphNode n : g.getNodes())
			gfg.printConnectedComponent(n, visited);

		// Cloned graph
		System.out.println("\n\n\tCLONED GRAPH\n");
		Graph cloned = gfg.cloneGraph(g);
		visited = new HashSet<GraphNode>();
		for (GraphNode node : cloned.getNodes())
			gfg.printConnectedComponent(node, visited);
	}
}
