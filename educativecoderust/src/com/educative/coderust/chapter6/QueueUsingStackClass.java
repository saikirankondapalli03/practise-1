package com.educative.coderust.chapter6;

import java.util.Stack;

class QueueUsingStackClass{
	  class QueueUsingStack{

	    Stack<Integer> stack1 = new Stack<Integer>(); 
	    Stack<Integer> stack2 = new Stack<Integer>(); 

	    void enqueue(int data) {
	      stack1.push(data);
	    }

	    boolean isEmpty() {
	      return stack1.size() + stack2.size() == 0;
	    }

	    int dequeue() {
	      if(isEmpty()) {
	        return -1;
	      }

	      if(stack2.isEmpty()) {
	        while(!stack1.isEmpty()){
	            stack2.push(stack1.pop());
	        }
	      }
	      return stack2.pop();
	    }
	  }
	  public static void main(String[] args) {
	  
	    QueueUsingStackClass qsc = new QueueUsingStackClass();
	    QueueUsingStackClass.QueueUsingStack qs = qsc.new QueueUsingStack();

	    System.out.println("dequeue()" + " = " + qs.dequeue());
	    qs.enqueue(3);
	    qs.enqueue(6);
	    qs.enqueue(16);
	    System.out.println("dequeue()" + " = " + qs.dequeue());
	    qs.enqueue(8);
	    qs.enqueue(4);
	    System.out.println("dequeue()" + " = " + qs.dequeue());
	  }   
	}  