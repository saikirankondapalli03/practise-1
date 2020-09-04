package com.educative.coderust.chapter7.clone;

import java.util.ArrayList;
import java.util.List;

//GraphNode class represents each 
//Node of the Graph 
class GraphNode {

	int data;
	List<GraphNode> children;

	// Constructor to initialize the node with value
	public GraphNode(int data) {
		this.data = data;
		this.children = new ArrayList<GraphNode>();
	}

	// Function to add a child to the current node
	public void addChild(GraphNode node) {
		this.children.add(node);
	}

	// Function to return a list of children
	// for the current node
	public List<GraphNode> getChildren() {
		return children;
	}

	// Function to set the node's value
	public void setData(int data) {
		this.data = data;
	}

	// Function to return the node's value
	public int getData() {
		return data;
	}
}
