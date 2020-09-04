package com.educative.recursive.refresher.theory.chapter5;

public class ArrayPalindrome {

	  private static boolean palindrome(int[] array, int idx) {
	    if (idx == array.length/2)
	      return true;
	    else if (array[idx] != array[array.length-1-idx])
	      return false;
	    else
	      return palindrome(array, idx+1);
	  }
	  
	  public static void main(String[] args) {
	    System.out.println("Array: ");
	    
	    //int[] array = {6,7,8,7,6};
	    int[] array = {1,2,2,2,1};

	    System.out.print("{ ");
	    for (int i = 0; i < array.length; i++) {
	      System.out.print(array[i] + ", ");
	    }
	    System.out.println("} ");
	    
	    System.out.println("Is it a palindrome?");
	    
	    Boolean result = palindrome(array, 0);
	    System.out.println(result);  
	  }
}
