package com.educative.coderust.chapter10.miscallenous;

import java.util.Arrays;

class SumOfThree{
	  static boolean findSumOfTwo(int[] A, int val, int startIndex) {

	    for (int i = startIndex, j = A.length - 1; i < j;) {
	      int sum = A[i] + A[j];
	      if (sum == val) {
	        return true;
	      }  

	      if (sum < val) {
	        ++i;
	      } else {
	        --j;
	      }
	    }

	    return false;
	  }

	  public static boolean findSumOfThree(int arr[], int requiredSum) {
	    Arrays.sort(arr);
	    for (int i = 0; i < arr.length-2; ++i) {
	      int remainingSum = requiredSum - arr[i];
	      if (findSumOfTwo(arr, remainingSum, i + 1)) {
	        return true;
	      }
	    }  
	    return false;
	  }
	  
	  public static void main(String []args){
	    int[] arr = {3, 7, 1, 2, 8, 4, 5};
	    System.out.println("Original Array: " + Arrays.toString(arr));                                         
	    System.out.println("Sum 20 exists " + findSumOfThree(arr, 20)); 
	    System.out.println("Sum 10 exists " + findSumOfThree(arr, 10));
	    System.out.println("Sum 21 exists " + findSumOfThree(arr, 21));
	  }
	}  