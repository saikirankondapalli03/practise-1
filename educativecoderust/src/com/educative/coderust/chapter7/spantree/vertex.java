package com.educative.coderust.chapter7.spantree;

class vertex {
	private int id;
	private boolean visited;

	public vertex(int id, boolean visited) {
		super();
		this.id = id;
		this.visited = visited;
	}

	int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	boolean isVisited() {
		return visited;
	}

	void setVisited(boolean visited) {
		this.visited = visited;
	}
}
