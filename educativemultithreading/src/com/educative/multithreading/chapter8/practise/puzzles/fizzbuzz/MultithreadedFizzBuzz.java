package com.educative.multithreading.chapter8.practise.puzzles.fizzbuzz;

class MultithreadedFizzBuzz {
	
	public static void main(String[] args) {
		MultithreadedFizzBuzz obj = new MultithreadedFizzBuzz(15);
 		Thread t1 = new FizzBuzzThread(obj,"Fizz");
	    Thread t2 = new FizzBuzzThread(obj,"Buzz");
	    Thread t3 = new FizzBuzzThread(obj,"FizzBuzz");
	    Thread t4 = new FizzBuzzThread(obj,"Number");
	    t1.start(); 
	    t2.start();
	    t4.start();
	    t3.start();
	}

	private int n;
    private int num = 1;

    public MultithreadedFizzBuzz(int n) {
        this.n = n;
    }
    public synchronized void fizz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 == 0 && num % 5 != 0) {
                System.out.println("Fizz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 == 0) {
                System.out.println("Buzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void fizzbuzz() throws InterruptedException {
        while (num <= n) {
            if (num % 15 == 0) {
                System.out.println("FizzBuzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void number() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 != 0) {
                System.out.println(num);
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
}

class FizzBuzzThread extends Thread {

    MultithreadedFizzBuzz obj;
    String method;
    
    public FizzBuzzThread(MultithreadedFizzBuzz obj, String method){
        this.obj = obj;
        this.method = method;
    }
    
    public void run() {
        if ("Fizz".equals(method)) {
            try {
                obj.fizz();
            }
            catch (Exception e) {
            }
        }
        else if ("Buzz".equals(method)) {
            try {
                obj.buzz();
            }
            catch (Exception e) {
            }
        }
        else if ("FizzBuzz".equals(method)) {
            try {
                obj.fizzbuzz();
            }
            catch (Exception e) {
            }
        }
        else if ("Number".equals(method)) {
            try {
                obj.number();
            }
            catch (Exception e) {
            }
        }

    }
}