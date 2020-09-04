package com.educative.recursive.refresher.theory.chapter3;

public class DecToBinary {
	public static int decimalToBinary(int decimalNum) {
		if (decimalNum == 0) {
			return 0;
		} else {
			int temp1= decimalNum % 2;
			int temp2= 10 * decimalToBinary(decimalNum / 2);
			return ( temp1 + temp2 );
		}
	}

	public static void main(String args[]) {
		int input = 27;
		int result = decimalToBinary(input);
		System.out.println("The binary form of " + input + " is: " + result);
	}
}
