package com.educative.interviewrefersher.chapter8;

import java.util.HashMap;

class CheckSymmetric {

	  public static String findSymmetric(int[][] arr) {
	    //Create an empty Hash Map
	    //Traverse given Array
	    //Look for second element of each pair in the hash. i.e for pair (1,2) look for key 2 in map.
	    //If found then store it in the result array, otherwise insert the pair in hash
	    HashMap < Integer,Integer > hashMap = new HashMap < Integer,Integer >();

	    String result = "";

	    //Traverse through the given array
	    for (int i = 0; i < arr.length; i++) {
	      int first = arr[i][0];
	      int second = arr[i][1];

	      Integer value = hashMap.get(second);

	      if (value != null && value == first) {
	        //Symmetric Pair found
	        result += "{" + String.valueOf(second) + "," + String.valueOf(first) + "}";
	      }
	      else 
	        hashMap.put(first, second);
	    }
	    return result;
	  }

	  public static void main(String args[]) {

	    int[][] arr = {{1, 2}, {3, 4}, {5, 9}, {4, 3}, {9, 5}};
	    String symmetric = findSymmetric(arr);
	    System.out.println(symmetric);

	  }

	}