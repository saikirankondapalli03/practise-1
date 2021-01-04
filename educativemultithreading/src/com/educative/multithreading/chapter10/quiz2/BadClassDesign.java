package com.educative.multithreading.chapter10.quiz2;

import java.io.File;

class BadClassDesign {
	public static void main(String args[]) throws Exception {
		BadClassDesign bcd = (new BadClassDesign());
	}

	// Private field
	private File file;

	public BadClassDesign() throws InterruptedException {
		Thread t = new Thread(() -> {
			System.out.println(this.getClass().getSimpleName());

			// Private field of class is accessible in the anonymous class
			System.out.println(this.file);
		});
		t.start();
		t.join();
	}
}