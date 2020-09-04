package com.educative.coderust.chapter3;

class DivideInteger {
	public static int integerDivide(int x, int y) {

		// We will return -1 if the
		// divisor is '0'.
		if (y == 0) {
			return -1;
		}

		if (x < y) {
			return 0;
		} else if (x == y) {
			return 1;
		} else if (y == 1) {
			return x;
		}

		int q = 1;
		int val = y; // val is 6

		while (val < x) {
			val <<= 1;
			// we can also use 'val = val + val;'
			q <<= 1;
			// we can also use 'q = q + q;'
		}

		if (val > x) {
			val >>= 1;
			q >>= 1;
			int result=q + integerDivide(x - val, y);
			return result;
		}

		return q;
	}

	public static void main(String[] args) {
		//System.out.println("7/2 = " + integerDivide(7, 2));
		//System.out.println("5/4 = " + integerDivide(5, 4));
		//System.out.println("1/3 = " + integerDivide(1, 3));
		System.out.println("67/6 = " + integerDivide(67,6));
		//System.out.println("40/4 = " + integerDivide(40, 4));
	}
}
