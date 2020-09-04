package com.educative.coderust.chapter7.spantree;

class edge {
	private int weight;
	private boolean visited;
	private vertex src;
	private vertex dest;

	public edge(int weight, boolean visited, vertex src, vertex dest) {
		this.weight = weight;
		this.visited = visited;
		this.src = src;
		this.dest = dest;
	}

	int getWeight() {
		return weight;
	}

	void setWeight(int weight) {
		this.weight = weight;
	}

	boolean isVisited() {
		return visited;
	}

	void setVisited(boolean visited) {
		this.visited = visited;
	}

	vertex getSrc() {
		return src;
	}

	void setSrc(vertex src) {
		this.src = src;
	}

	vertex getDest() {
		return dest;
	}

	void setDest(vertex dest) {
		this.dest = dest;
	}
}
