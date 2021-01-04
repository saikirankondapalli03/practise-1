package com.educative.multithreading.chapter7.practise.puzzles.superman;

public class SupermanSingleEager {
	private static final SupermanSingleEager superman =   new SupermanSingleEager();

    private SupermanSingleEager() {
    }

    public static SupermanSingleEager getInstance() {
        return superman;
    }
    
    public static void main( String args[] ) {
    	SupermanSingleEager superman = SupermanSingleEager.getInstance();
        superman.fly();
    }
    public void fly() {
		System.out.println("I am Superman & I can fly !");
	}
}
