package com.educative.interviewrefersher.chapter2;

import java.util.Arrays;

class CheckMaxMin {

	  public static void maxMin(int[] arr) {
	    int maxIdx = arr.length - 1;
	    int minIdx = 0;
	    int maxElem = arr[maxIdx] + 1; // store any element that is greater than the maximum element in the array 
	    for( int i = 0; i < arr.length; i++) {
	      // at even indices we will store maximum elements
	    	 int mod_result;
	      if (i % 2 == 0)
	      {  
	    	int mod_value= (arr[maxIdx] % maxElem ); // 8 => 8 % 10 
	    	mod_result=mod_value* maxElem; // => 8 * 10 => 80 
	        maxIdx -= 1;
	      }
	      else 
	      { // at odd indices we will store minimum elements
	    	int mod_value= (arr[minIdx] % maxElem ); // 9 
		    mod_result=mod_value* maxElem;
		    minIdx += 1;
	      }
	     
	    System.out.println(Arrays.toString(arr));
		arr[i] = arr[i]+mod_result;
		System.out.println(Arrays.toString(arr)); 
	    }
	    // dividing with maxElem to get original values.
	    for( int i = 0; i < arr.length; i++) {
	      arr[i] = arr[i] / maxElem; // 80/10 => 8 
	    }
	  }

	  public static void main(String args[]) {

	    int[] arr = {0,1,2,3,4,5,6,7,8,9};
	    System.out.print("Array before min/max: ");
	    for (int i = 0; i < arr.length; i++) 
	      System.out.print(arr[i] + " ");
	    System.out.println();

	    maxMin(arr);

	    System.out.print("Array after min/max: ");
	    for (int i = 0; i < arr.length; i++) 
	      System.out.print(arr[i] + " ");
	    System.out.println();
	  }
	}