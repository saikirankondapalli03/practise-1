package com.educative.recursive.refresher.theory.chapter3;

public class GCD {

	public static int gcd(int num1, int num2) {

        // Base case
        if (num1 == num2) {
            return num1;
        }
        // Recursive case
        if (num1 > num2) {
            return gcd(num1-num2, num2);
        }
        else {
            return gcd(num1, num2-num1);
        }
    }

    public static void main( String args[] ) {
        int x = 54;
        int y = 36;
        int result = gcd(x, y);
        System.out.println("The GCD of " + x + " and " + y + " is:");
        System.out.println(result);
        
        System.out.println(hcf(x,y));
    }
    
    

    public static int hcf(int a, int b)
    {
        if (b != 0)
            return hcf(b, a % b);
        else
            return a;
    }
}
