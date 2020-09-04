package com.educative.interviewrefersher.chapter8;

import java.util.HashSet;

class CheckDisjoint {

	  public static boolean isDisjoint(int[] arr1, int[] arr2) {

	    //Create an HashSet and store all values of arr1   
	    HashSet < Integer > hSet = new HashSet < >();

	    for (int i = 0; i < arr1.length; i++) {
	      if (!hSet.contains(arr1[i])) hSet.add(arr1[i]);
	    }

	    //Traverse arr2 and check if arr1 contains any arr2 element
	    for (int i = 0; i < arr2.length; i++) {
	      if (hSet.contains(arr2[i])) return false;
	    }
	    return true;
	  }
	  
	  public static void main(String args[]) {
	   
	    int[] arr1 = {9, 4, 3, 1, -2, 6, 5};
	    int[] arr2 = {7, 10, 8};
	    int[] arr3 = {1, 12};
	    System.out.println(isDisjoint(arr1, arr2));
	    System.out.println(isDisjoint(arr1, arr3));
	    
	  }
	}