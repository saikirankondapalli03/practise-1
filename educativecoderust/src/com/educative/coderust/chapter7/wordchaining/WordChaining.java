package com.educative.coderust.chapter7.wordchaining;

import java.util.ArrayList;
import java.util.Arrays;



	class LongestChain{
	  
	  // Test Program.
	  public static void main(String[] args) {
	    ArrayList<String> list = new ArrayList<String>(Arrays.asList("eve", "eat", "ripe", "tear"));
	    graph g = new graph(new ArrayList<vertex>());
	    g.createGraph(list);
	    boolean result = g.canChainWords(list.size());
	    String output = result == true ? "true" : "false";
	    System.out.println(output);
	  }
	}