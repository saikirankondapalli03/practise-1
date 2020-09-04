package com.educative.coderust.chapter7.wordchaining;

import java.util.ArrayList;
import java.util.List;

class vertex {
	  private char value;
	  private boolean visited;
	  private List<vertex> adj_vertices;
	  private List<vertex> in_vertices;

	  public vertex(char value, boolean visited) {
	    this.value = value;
	    this.visited = visited;
	    this.adj_vertices = new ArrayList<vertex>();
	    this.in_vertices = new ArrayList<vertex>();
	  }

	  char getValue() {
	    return value;
	  }

	  void setValue(char value) {
	    this.value = value;
	  }

	  boolean isVisited() {
	    return visited;
	  }

	  void setVisited(boolean visited) {
	    this.visited = visited;
	  }

	  List<vertex> getAdj_vertices() {
	    return this.adj_vertices;
	  }

	  List<vertex> getIn_vertices() {
	    return this.in_vertices;
	  }
	}

	