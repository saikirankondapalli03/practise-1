package com.educative.chapter3.recursion;

class Fibonacci {

	  public int CalculateFibonacci(int n) {
	    if(n < 2)
	      return n;
	    return CalculateFibonacci(n-1) + CalculateFibonacci(n-2);
	  }

	  public static void main(String[] args) {
	    Fibonacci fib = new Fibonacci();
	    System.out.println("5th Fibonacci is ---> " + fib.CalculateFibonacci(5));
	    System.out.println("6th Fibonacci is ---> " + fib.CalculateFibonacci(6));
	    System.out.println("7th Fibonacci is ---> " + fib.CalculateFibonacci(7));
	  }
	}
