package com.educative.recursive.refresher.theory.chapter5;

public class ArraySum {
	public static int arraySum( int[] array, int n)
	{   
	  //base case
	    if(n <= 0)
	        return 0;
	    else {
	    	int temp1=arraySum(array, n - 1) ;
	    	int temp2= array[n - 1];
	    	int result= temp1+temp2;
	    	return result;
	    }
	}
	
	public static void printArray(int array[], int startIndex, int len)
	{
		if(startIndex >= len)
	        return;
		
	 
	    // Recursively calling printArray to print next element in the array
	    printArray(array, startIndex + 1, len);
	    System.out.print(array[startIndex] + " ");
		
	}
	
	public static void main(String[] args) {
		int[]a = {1,2,3,4,5};
		System.out.println(arraySum(a, a.length));
		printArray(a, 0, a.length);
	}
}
