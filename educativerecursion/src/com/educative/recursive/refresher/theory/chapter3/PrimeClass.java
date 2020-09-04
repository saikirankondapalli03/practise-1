package com.educative.recursive.refresher.theory.chapter3;

public class PrimeClass {
	public static boolean isPrime(int num, int i) {

		// First base case
		if (num < 2) {
			return false;
		}
		// Second base case
		else if (i == 1) {
			return true;
		}
		// Third base case
		else if (num % i == 0) {
			return false;
		}
		// Recursive case
		else {
			return isPrime(num, i - 1);
		}
	}

	static boolean isPrimeNum(int n, int i) 
    { 
  
        // Base cases 
        if (n <= 2) 
            return (n == 2) ? true : false; 
        if (n % i == 0) 
            return false; 
        if (i * i > n) 
            return true; 
       
        // Check for next divisor 
        return isPrimeNum(n, i + 1); 
    } 
	
	static void sieveOfEratosthenes(int n) 
    { 
        // Create a boolean array "prime[0..n]" and initialize 
        // all entries it as true. A value in prime[i] will 
        // finally be false if i is Not a prime, else true. 
        boolean prime[] = new boolean[n+1]; 
        for(int i=0;i<n;i++) 
            prime[i] = true; 
          
        for(int p = 2; p*p <=n; p++) 
        { 
            // If prime[p] is not changed, then it is a prime 
            if(prime[p] == true) 
            { 
                // Update all multiples of p 
                for(int i = p*p; i <= n; i += p) 
                    prime[i] = false; 
            } 
        } 
          
        // Print all prime numbers 
        for(int i = 2; i <= n; i++) 
        { 
            if(prime[i] == true) 
                System.out.print(i + " "); 
        } 
    } 
	
	// check for number prime or not
	static boolean isPrime(int n) {
		// check if n is a multiple of 2
		if (n % 2 == 0)
			return false;
		// if not, then just check the odds
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static void main(String args[]) {
		int input = 13;
		System.out.println(13/2);
		boolean result = isPrime(input, 2);
		sieveOfEratosthenes(13);
		// Print if the number is prime
		if (result == true) {
			System.out.println(input + " is a prime number");
		}
		// Prints if the number is NOT a prime number
		else {
			System.out.println(input + " is NOT a prime number");
		}
	}
}
