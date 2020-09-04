package com.educative.recursive.refresher.theory.chapter3;

class ModuloClass {

    public static int mod(int dividend, int divisor) {

        // Making sure there is no division by 0
        if (divisor == 0) {
            System.out.println("Divisor cannot be 0");
            return -1;
        }

        // Base case
        if (dividend < divisor) {
            return dividend;
        }
        // Recursive case
        else {
        	int value=dividend-divisor;
            return mod(value, divisor);
        }
    }

    public static void main( String args[] ) {
    	
        int dividend = 14;
        int divisor = 4;
        int remainder = mod(dividend, divisor);
        System.out.println(dividend + " mod " + divisor + " = " + remainder);
    }

}