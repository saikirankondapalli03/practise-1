package com.educative.multithreading.chapter7.practise.puzzles.superman;

public class SupermanVolatile {
	private static volatile SupermanVolatile superman;

	private SupermanVolatile() {

	}

	public static SupermanVolatile getInstance() {

		if (superman == null) {
			synchronized (SupermanVolatile.class) {

				if (superman == null) {
					superman = new SupermanVolatile();
				}
			}
		}

		return superman;
	}

	public void fly() {
		System.out.println("I am Superman & I can fly !");
	}

}
