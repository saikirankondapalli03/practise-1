package com.educative.interviewrefersher.chapter2;

//Java program to rotate an array by 
//d elements 
class RotateArray { 
	/*Function to left rotate arr[] of siz n by d*/
	void leftRotate(int arr[], int d, int n) 
	{ 
		 /* To handle if d >= n */
        d = d % n; 
        int i, j, k, temp; 
        int g_c_d = gcd(d, n); 
        for (i = 0; i < g_c_d; i++) { 
            /* move i-th values of blocks */
            temp = arr[i]; 
            j = i; 
            while (true) { 
                k = j + d; 
                if (k >= n) 
                    k = k - n; 
                if (k == i) 
                    break; 
                arr[j] = arr[k]; 
                j = k; 
            } 
            arr[j] = temp; 
        } 
	} 

	/*UTILITY FUNCTIONS*/

	/* function to print an array */
	void printArray(int arr[], int size) 
	{ 
		int i; 
		for (i = 0; i < size; i++) 
			System.out.print(arr[i] + " "); 
	} 

	/*Fuction to get gcd of a and b*/
	int gcd(int a, int b) 
	{ 
		if (b == 0) 
			return a; 
		else
			return gcd(b, a % b); 
	} 

	// Driver program to test above functions 
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{ 
		RotateArray rotate = new RotateArray(); 
		int arr[] = {3, 6, 1, 8, 4, 2}; 
		int rightRotate=1;
		rotate.leftRotate(arr,arr.length-1, arr.length); 
		rotate.printArray(arr, arr.length); 
	}
	
	
	//Rotates given Array by 1 position
	  public static void rotateArray(int[] arr) {

	    //Store last element of Array.
	    //Start from the Second last and Right Shift the Array by one.
	    //Store the last element saved on the first index of the Array.
	    int lastElement = arr[arr.length - 1];

	    for (int i = arr.length - 1; i > 0; i--) {

	      arr[i] = arr[i - 1];
	    }

	    arr[0] = lastElement;
	  } //end of rotateArray

	
} 


//This code has been contributed by Mayank Jaiswal 
