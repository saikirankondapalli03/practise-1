package com.educative.recursive.refresher.theory.chapter5;

public class ReplaceNegativeWithZero {
	  
	  private static void replaceNegativeValues(int[] a, int currentIndex) {
	    if (currentIndex == a.length) 
	      return;
	    else {
	      if (a[currentIndex] < 0) {
	        a[currentIndex] = 0;
	        replaceNegativeValues(a, currentIndex+1);
	      }
	      else {
	        a[currentIndex] = a[currentIndex];
	        replaceNegativeValues(a, currentIndex+1);
	      }
	    }
	  } 
	  
	  public static void main(String[] args) {
	    System.out.println("Before: ");
	    
	    int[] array = {2,-3,4,-1,-7,8,3};

	    System.out.print("{ ");
	    for (int i = 0; i < array.length; i++) {
	      System.out.print(array[i] + " ");
	    }
	    System.out.println("} ");
	    System.out.println("After: ");
	    
	    replaceNegativeValues(array, 0);

	    System.out.print("{ ");
	    for (int i = 0; i < array.length; i++) {
	      System.out.print(array[i] + " ");
	    }
	    System.out.print("} ");
	  }
}
