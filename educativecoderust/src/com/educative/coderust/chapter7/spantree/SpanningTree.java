package com.educative.coderust.chapter7.spantree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class mst {
	public static void testGraph1() {
		graph g = new graph(new ArrayList<vertex>(), new ArrayList<edge>());
		int v = 5;

		// each edge contains the following: weight, src, dest
		ArrayList<Integer> e1 = new ArrayList<Integer>(Arrays.asList(1, 1, 2));
		ArrayList<Integer> e2 = new ArrayList<Integer>(Arrays.asList(1, 1, 3));
		ArrayList<Integer> e3 = new ArrayList<Integer>(Arrays.asList(2, 2, 3));
		ArrayList<Integer> e4 = new ArrayList<Integer>(Arrays.asList(3, 2, 4));
		ArrayList<Integer> e5 = new ArrayList<Integer>(Arrays.asList(3, 3, 5));
		ArrayList<Integer> e6 = new ArrayList<Integer>(Arrays.asList(2, 4, 5));

		List<ArrayList<Integer>> e = new ArrayList<ArrayList<Integer>>();
		e.add(e1);
		e.add(e2);
		e.add(e3);
		e.add(e4);
		e.add(e5);
		e.add(e6);

		g.generateGraph(v, e);
		System.out.println("Testing graph 1...");
		// g.printGraph();
		int w = g.findMinSpanningTree();
		g.printMst(w);
	}

	public static void testGraph2() {
		graph g = new graph(new ArrayList<vertex>(), new ArrayList<edge>());
		int v = 7;

		// each edge contains the following: weight, src, dest
		ArrayList<Integer> e1 = new ArrayList<Integer>(Arrays.asList(2, 1, 4));
		ArrayList<Integer> e2 = new ArrayList<Integer>(Arrays.asList(1, 1, 3));
		ArrayList<Integer> e3 = new ArrayList<Integer>(Arrays.asList(2, 1, 2));
		ArrayList<Integer> e4 = new ArrayList<Integer>(Arrays.asList(1, 3, 4));
		ArrayList<Integer> e5 = new ArrayList<Integer>(Arrays.asList(3, 2, 4));
		ArrayList<Integer> e6 = new ArrayList<Integer>(Arrays.asList(2, 3, 5));
		ArrayList<Integer> e7 = new ArrayList<Integer>(Arrays.asList(2, 4, 7));
		ArrayList<Integer> e8 = new ArrayList<Integer>(Arrays.asList(1, 5, 6));
		ArrayList<Integer> e9 = new ArrayList<Integer>(Arrays.asList(2, 5, 7));

		List<ArrayList<Integer>> e = new ArrayList<ArrayList<Integer>>();
		e.add(e1);
		e.add(e2);
		e.add(e3);
		e.add(e4);
		e.add(e5);
		e.add(e6);
		e.add(e7);
		e.add(e8);
		e.add(e9);

		g.generateGraph(v, e);
		System.out.println("Testing graph 2...");
		// g.printGraph();
		int w = g.findMinSpanningTree();
		g.printMst(w);
	}

	public static void main(String[] args) {
		//testGraph1();
		testGraph2();
	}
}
