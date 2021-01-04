package com.educative.multithreading.chapter8.practise.puzzles.h20;

import java.util.Arrays;
import java.util.Collections;

class H2OMachine {

	Object object;
	String[] molecule;
	int count;

	public H2OMachine() {
		molecule = new String[3];
		count = 0;
		object = new Object();
	}

	public void HydrogenAtom() {
		synchronized (object) {
			// if 2 hydrogen atoms already exist
			while (Collections.frequency(Arrays.asList(molecule), "H") == 2) {
				try {
					object.wait();
				} catch (Exception e) {
				}
			}

			molecule[count] = "H";
			count++;

			// if molecule is complete, then exit.
			if (count == 3) {
				for (String element : molecule) {
					System.out.print(element);
				}
				Arrays.fill(molecule, null);
				count = 0;
			}
			object.notifyAll();
		}
	}

	public void OxygenAtom() throws InterruptedException {
		synchronized (object) {

			// if 1 oxygen atom already exists
			while (Collections.frequency(Arrays.asList(molecule), "O") == 1) {
				try {
					object.wait();
				} catch (Exception e) {
				}
			}

			molecule[count] = "O";
			count++;

			// if molecule is complete, then exit.
			if (count == 3) {
				for (String element : molecule) {
					System.out.print(element);
				}
				Arrays.fill(molecule, null);
				count = 0;
			}
			object.notifyAll();
		}
	}
}

class H2OMachineThread extends Thread {

	H2OMachine molecule;
	String atom;

	public H2OMachineThread(H2OMachine molecule, String atom) {
		this.molecule = molecule;
		this.atom = atom;
	}

	public void run() {
		if ("H".equals(atom)) {
			try {
				molecule.HydrogenAtom();
			} catch (Exception e) {
			}
		} else if ("O".equals(atom)) {
			try {
				molecule.OxygenAtom();
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {

		H2OMachine molecule = new H2OMachine();

		Thread t1 = new H2OMachineThread(molecule, "H");
		Thread t2 = new H2OMachineThread(molecule, "O");
		Thread t3 = new H2OMachineThread(molecule, "H");
		Thread t4 = new H2OMachineThread(molecule, "O");

		t2.start();
		t1.start();
		t4.start();
		t3.start();
	}
}
