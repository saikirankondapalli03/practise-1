package com.educative.interviewrefersher.chapter3;

public class CheckSortRecursion {
	public static void insert(Stack<Integer> stack, int value) {
	      if(stack.isEmpty()|| value < stack.top())
	          stack.push(value);
	      else{
	          int temp = stack.pop();
	          insert(stack,value);
	          stack.push(temp);
	      }
	  }

	  public static Stack<Integer> sortStack(Stack<Integer> stack) {
	      if(!stack.isEmpty()) {
	          int value = stack.pop();
	          sortStack(stack);
	          insert(stack,value);
	      }
	      return stack;
	  }
	  
	   public static void main(String args[]) {

	        Stack<Integer> stack = new Stack<Integer>(7);
	        stack.push(2);
	        stack.push(97);
	        stack.push(4);
	        stack.push(42);
	      
	        sortStack(stack);
	        while(!stack.isEmpty()){
	              System.out.println(stack.pop());
	          }
	    }
}
