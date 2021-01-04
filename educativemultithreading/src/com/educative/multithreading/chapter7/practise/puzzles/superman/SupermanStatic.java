package com.educative.multithreading.chapter7.practise.puzzles.superman;

public class SupermanStatic {
	private static SupermanStatic superman;

	static {
		try {
			superman = new SupermanStatic();
		} catch (Exception e) {
			// Handle exception here
		}
	}

	private SupermanStatic() {
	}

	public static SupermanStatic getInstance() {
		return superman;
	}
}
