package com.educative.recursive.refresher.theory.chapter3;

public class FibonacciClass {
	 private static int fibonacci(int n) {
	        // Base case 
	        if (n <= 1) {
	            return n;
	        }
	        // Recursive case
	        else {
	            return (fibonacci(n-1) + fibonacci(n-2));
	        }
	    }

	    public static void main( String args[] ) {
	        int input = 5;
	        System.out.println("Fibonacci sequence for the first " + input + " elements is:");

	        // Loop to print all the fibonacci sequence elements
	        int i = 0;
	        while (i < input) {
	            System.out.print(fibonacci(i) + " ");
	            i++;
	        }
	    }
}
